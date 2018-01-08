package xilodyne.photo_gallery.threads;

import xilodyne.photo_gallery.access.AccessCodes;
import xilodyne.photo_gallery.fileio.ConfigFile;
import xilodyne.photo_gallery.fileio.ReadEXIFTOOLDumpXML;
import xilodyne.photo_gallery.fileio.ReadGalleryXML;
import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterPhoto;

import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;



//https://support.google.com/webmasters/answer/178636
//http://www.sitemaps.org/protocol.html
//https://googlewebmastercentral.blogspot.jp/2006/10/multiple-sitemaps-in-same-directory.html

//create an index of sitemaps
//for each xml gallery file, create a site map and add entry to sitemap index

public class SiteMapThread extends Thread {

	AccessCodes accessCodes = new AccessCodes();
	AssembleHTTP assembleHTTP = new AssembleHTTP();
	boolean openImageVector = false;
	private int jobID = 0;
	File sitemap_IndexFile, sitemap_XMLGalleryFile;
	FileOutputStream sitemap_IndexStream, sitemap_XMLGalleryStream;
	PrintStream outputSitemapIndex, outputSitemapXMLGallery;
	ArchiveInfo archive = new ArchiveInfo();
	ReadEXIFTOOLDumpXML exifData = new ReadEXIFTOOLDumpXML();


	
	Date date = new Date();
	String sDate;
	ConfigFile configFile;
	String sError = "";
	Boolean bError = false;

	
	private boolean ABORT;



	//duplicate to Thumbnails to avoid sync issues
	Enumeration<?> loop;
	
	public SiteMapThread(int iJobID) {

		this.jobID = iJobID;
		
		if ( !Globals.checkDataLoaded() ) {
			
			try {
				configFile = new ConfigFile();
				} catch (IOException e) {
					Logging.error(e);
					sError = e.getMessage();
					bError = true;
				}
			}
	}


	// synchronized
	// get list of all public galleries
	// for each gallery,
	public void run() {
		Logging.info(this.getClass().getName(), "STARTING");

		
		String[] xmlFiles;
//		sDate = DateFormat.getDateInstance().format(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sDate = dateFormat.format(date);

//		ParameterPhoto paramInfo = new ParameterPhoto();
		xmlFiles = accessCodes.returnPermitedArchives(Globals.access_PUBLIC);
		
		this.initSitemapIndexFile();
//		this.writeHomeURL();


	//	while (keepRunning) {
			
			for (int i = 0; i < xmlFiles.length; i++) {
				//for each xml file
				//update entry in sitemap index
				//create sitemap xmlfile
				
				String sXMLFile = xmlFiles[i];
				this.openImageVector = false;
				
				ParameterPhoto paramInfo = new ParameterPhoto();
				paramInfo.setXMLFile(sXMLFile);
				this.initVar(archive, paramInfo);

				System.out.println("Archive: " + archive.getTitle() + ":" + paramInfo.getXMLFile());
				
				Logging.info(this.getClass().getName(), "XMLFile: " + sXMLFile);
			//	this.writeGalleryURL( sXMLFile );
				
	/*			try {
			//	new ReadGalleryXML(sXMLFile, paramInfo);
			//	new ReadGalleryXML(archive, paramInfo);
				
				} catch (ParserConfigurationException e) {
					Logging.error(e.getLocalizedMessage());
				} catch (SAXException e) {
					Logging.error(e.getLocalizedMessage());
				} catch (IOException e) {
					Logging.error(e.getLocalizedMessage());
				}
	*/			
				int iCount = paramInfo.getImageListSize();
				Logging.info(this.getClass().getName(), "size: " + 	iCount);
			//	try {
			//		this.sleep(10000);
			//	} catch (InterruptedException e) {}
				
				if (ABORT) break;
				
				String sitemapXMLGalleryFilename = Globals.CATALINE_HOME
						+ Globals.GALLERY_DEFAULT_DEPLOY_DIR + "/support_files/sitemaps/sitemap_"
						+ sXMLFile;
			
				this.writeIndexEntry(sXMLFile);
				initSitemapXMLGalleryFile(sitemapXMLGalleryFilename);
				
				try {
				for (int x = 0 ; x < iCount; x++ )  {
						Image tempImage = this.getNextImage(paramInfo);
						Logging.info(this.getClass().getName(), "found: " + tempImage.getFileWeb());
						this.writeHomeLOC(tempImage);

						this.writeURLPhoto(tempImage, archive);
						if (ABORT) break;
				} 
				} catch (NullPointerException e) {
					Logging.error( e );
				}
				
				this.closeSitemapXMLGalleryFile();
		
		}
			
			
			this.closeSitemapIndexFile();
			Logging.info(this.getClass().getName(), "Sitemap.xml complete.");
			Queue queue = new Queue();
			int iStatus = QueueObject.COMPLETE;
			if (ABORT) iStatus = QueueObject.ABORTED;
			queue.jobFinished(this.getJobID(), iStatus );
	}

	// Ask the thread to stop running
	public void Abort() {
		this.ABORT = true;
	}
	

	public Image getNextImage(ParameterPhoto paramInfo) {
		Image tempImage = null;

		if (!this.openImageVector) {
			this.openImageVector = true;
			loop = paramInfo.getImageList().elements();
		}

		if (loop.hasMoreElements()) {
			tempImage = ((Image) loop.nextElement());
		}

		return tempImage;
	}

/*	<?xml version="1.0" encoding="UTF-8"?>
	<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
	        xmlns:image="http://www.google.com/schemas/sitemap-image/1.1">
	  <url>
	    <loc>http://example.com/sample.html</loc>  */

	private void initSitemapXMLGalleryFile(String sXMLFilename)  {
		  
		try {
		sitemap_XMLGalleryFile = new File(sXMLFilename);
		this.sitemap_XMLGalleryStream = new FileOutputStream(this.sitemap_XMLGalleryFile);
		Logging.info(this.getClass().getName(), "Sitemap Gallery: " + sXMLFilename);
		Logging.info(this.getClass().getName(), "File can write: " + sitemap_XMLGalleryFile.canWrite());
		} catch (IOException e) {
			Logging.error(e);
		}
		outputSitemapXMLGallery = new PrintStream(this.sitemap_XMLGalleryStream);

		outputSitemapXMLGallery.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		outputSitemapXMLGallery.println("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"");
		outputSitemapXMLGallery.println(" xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\">");

//		outputSitemapXMLGallery.println("		<loc>");
//		String sResult = this.assembleHTTP.makeHTTPImageHome(sXMLFile, sImage);
//		sResult = this.replace(sResult, "&", "&amp;");
//		outputSitemapIndex.print( "http://" + sResult );

	}
	
	private void initSitemapIndexFile()  {
		  
		String sFileName = Globals.CATALINE_HOME
				+ Globals.GALLERY_DEFAULT_DEPLOY_DIR + "/sitemap_index.xml";
	
		try {
		sitemap_IndexFile = new File(sFileName);
		this.sitemap_IndexStream = new FileOutputStream(sitemap_IndexFile);
		Logging.info(this.getClass().getName(), "Sitemap Index: " + sFileName);
		Logging.info(this.getClass().getName(), "File can write: " + sitemap_IndexFile.canWrite());
		} catch (IOException e) {
			Logging.error(e);
		}
		outputSitemapIndex = new PrintStream(this.sitemap_IndexStream);

		outputSitemapIndex.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");            
		outputSitemapIndex.println("<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
	}

	/*
	   <sitemap>
	      <loc>http://www.example.com/sitemap1.xml.gz</loc>
	      <lastmod>2004-10-01T18:23:17+00:00</lastmod>
	   </sitemap>  */

	private void writeIndexEntry(String sXMLFile) {
		outputSitemapIndex.println("	<sitemap>");
		outputSitemapIndex.print("		<loc>http://photo.xandus.net" + Globals.HTTP_Refer + "/support_files/sitemaps/sitemap_" + sXMLFile);
		outputSitemapIndex.println("</loc>");
		outputSitemapIndex.println("		<lastmod>" + sDate + "</lastmod>");
		outputSitemapIndex.println("	</sitemap>");
		
	}
	/*
	    <image:image>
	      <image:loc>http://example.com/image.jpg</image:loc>
	    </image:image>
	    <image:image>
	      <image:loc>http://example.com/photo.jpg</image:loc>
	    </image:image>
	//using:  http://www.google.com/schemas/sitemap-image/1.1/sitemap-image.xsd
	 
	 */
	private void writeURLPhoto(Image tempImage, ArchiveInfo archive) {

		String[] sEXIFAuthorDesc = exifData.getEXIFTOOLdata(archive.getXMLFilename(), tempImage.getFileWeb());

		outputSitemapXMLGallery.println("    <image:image>");
		outputSitemapXMLGallery.print("        <image:loc>http://photo.xandus.net");
		outputSitemapXMLGallery.print( Globals.HTTP_Refer + "/");
		outputSitemapXMLGallery.print(archive.getDirWeb() + "/" + tempImage.getFileWeb());
		outputSitemapXMLGallery.println("</image:loc>");

		outputSitemapXMLGallery.print("        <image:caption>" + sEXIFAuthorDesc[Globals.EXIFTOOL_DESC_POS].replaceAll("&(?!amp;)", "&amp;"));
		outputSitemapXMLGallery.println("</image:caption>");
		
//		outputSitemapXMLGallery.print("        <image:geo_location>" + tempImage.getDescription());
//		outputSitemapXMLGallery.println("</image:geo_location>");
		
		outputSitemapXMLGallery.print("        <image:title>" + tempImage.getTitle());
		outputSitemapXMLGallery.println("</image:title>");
		outputSitemapXMLGallery.println("    </image:image>");
		outputSitemapXMLGallery.println("  </url>\n"); 

		
		
	}

	public void writeHomeLOC(Image tempImage) {
		outputSitemapXMLGallery.println("  <url>");
		outputSitemapXMLGallery.print("    <loc>http://photo.xandus.net");
//		String sResult = this.assembleHTTP.makeHTTPImageHome(tempImage.getFileWeb()).replaceAll("&(?!amp;)", "&amp;");
//		sResult = this.replace(sResult, "&", "&amp;");
		outputSitemapXMLGallery.print( this.assembleHTTP.makeHTTPImageHome(tempImage.getFileWeb()) );
		outputSitemapXMLGallery.print(">");

//		outputSitemapXMLGallery.print("    <loc>");
////		String sResult = this.assembleHTTP.makeHTTPArchivesHome();
//		sResult = this.replace(sResult, "&", "&amp;");
//		outputSitemapXMLGallery.print( "http://" + sResult );

		outputSitemapXMLGallery.println("</loc>");
	//	outputSitemapIndex.println("    <lastmod>"  + sDate + "</lastmod>");	
		
	}
	private void closeSitemapIndexFile() {
		outputSitemapIndex.println("</sitemapindex>");
		outputSitemapIndex.flush();
		outputSitemapIndex.close();	
	
	}

	private void closeSitemapXMLGalleryFile() {
	//	outputSitemapXMLGallery.println("</loc>");
	//	outputSitemapXMLGallery.println("  </url>\n"); 
		outputSitemapXMLGallery.println("</urlset>\n");
		outputSitemapXMLGallery.flush();
		outputSitemapXMLGallery.close();
	}

	public int getJobID() {
		return this.jobID;
	}
	

	private void initVar(ArchiveInfo archive, ParameterPhoto paramInfo) {
		Logging.info(this.getClass().getName(), "param getxmlfile " + paramInfo.getXMLFile());

		archive.setXMLFilename(paramInfo.getXMLFile());

		try {
			new ReadGalleryXML(archive, paramInfo);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e ) {
			e.printStackTrace();
		}
			Logging.info(this.getClass().getName(), "archive get dir web" + archive.getDirWeb());

/*		} catch (ParserConfigurationException e) {
			Logging.error(e.getLocalizedMessage());
		} catch (SAXException e) {
			Logging.error(e.getLocalizedMessage());
		} catch (IOException e) {
			Logging.error(e);
			Logging.error("No images exist for this archive.");
		} */
	}

}
