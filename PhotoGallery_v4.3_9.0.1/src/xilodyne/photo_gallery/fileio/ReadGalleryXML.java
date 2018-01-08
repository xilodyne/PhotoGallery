package xilodyne.photo_gallery.fileio;

import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.ParameterPhoto;

import java.io.File;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author aholiday
 *
 */
public class ReadGalleryXML extends DefaultHandler {
	
	//variables used in XML files
	//archive format
	public final static String DIR_THUMBNAILS="dir_thumbnails";
	public final static String DIR_WEB="dir_web";
	public final static String DIR_ORIGINALS="dir_originals";
	public final static String DIR_MARQUEE="dir_marquee";
	public final static String THUMBNAILSPERROW="thumbnailsPerRow";
	public final static String THUMBNAILCOLUMNWIDTH="thumbnailColumnWidth";
	
	
	
	//image format
	public final static String IMAGE_TITLE="title";
	public final static String IMAGE_DATE="date";
	public final static String IMAGE_DESC="description";	
	public final static String FILE_THUMBNAIL = "file_thumbnail";
	public final static String FILE_WEB = "file_web";
	public final static String FILE_ORIGINAL = "file_original";
	public final static String FILE_ORIGINAL_SIZE = "file_original_size";


	String XMLFile = "";
	ArchiveInfo archive = new ArchiveInfo();
	Vector<Image> imageList = new Vector<Image>();

	public ReadGalleryXML() {}

	public ReadGalleryXML(ArchiveInfo sArchive, ParameterPhoto paramInfo) 
	throws InvalidPropertiesFormatException, IOException
//			throws SAXException, ParserConfigurationException,
//	IOException, FileNotFoundException
			{
		archive = sArchive;
		imageList = paramInfo.getImageList();

		XMLFile = Globals.GALLERY_XML_DIR_LOCATION + "/" + archive.getXMLFilename();

		Logging.info(this.getClass().getName(), "Reading(1) XML file: " + XMLFile);

		SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();

		SAXParser parser;
		try {
			parser = factory.newSAXParser();
		
		InputSource source = new InputSource(XMLFile);
		
		
			parser.parse(source, this);
		} catch (ParserConfigurationException | SAXException  e) {
			Logging.info(this.getClass().getName(), e.getLocalizedMessage());
			e.printStackTrace();
		}
		

	}
	
	public ReadGalleryXML(String archiveXMLFilename, ParameterPhoto paramInfo) 
//			throws SAXException, ParserConfigurationException,
//	IOException,FileNotFoundException
		 {
		imageList = paramInfo.getImageList();
//		imageList.clear();

		XMLFile = Globals.GALLERY_XML_DIR_LOCATION + "/" + archiveXMLFilename;
		
		Logging.info(this.getClass().getName(), "Reading(2) XML file: " + XMLFile);

		SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();

		SAXParser parser;
		try {
			parser = factory.newSAXParser();
		InputSource source = new InputSource(XMLFile);
		
		
			parser.parse(source, this);
		} catch (InvalidPropertiesFormatException | ParserConfigurationException | SAXException   e) {
			Logging.info(this.getClass().getName(), e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Logging.info(this.getClass().getName(), e.getLocalizedMessage());
			e.printStackTrace();
			
		}
		

	}

	/**
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("tomcat_photo_gallery")) {
	//	if (qName.equals("pgal_project")) {
			archive.setTitle(attributes.getValue("title"));
			archive.setDirThumbnail(attributes.getValue(DIR_THUMBNAILS));
			archive.setDirWeb(attributes.getValue(DIR_WEB));
			archive.setDirOriginals(attributes.getValue(DIR_ORIGINALS));
			archive.setThumbnailsPerRow(Integer.parseInt(attributes.getValue(THUMBNAILSPERROW)));
			archive.setThumbnailColumnWidth(Integer.parseInt(attributes.getValue(THUMBNAILCOLUMNWIDTH)));
		} else if (qName.equals("photo")) {
			
			Image image = new Image();
			
			image.setTitle(attributes.getValue(IMAGE_TITLE));
			image.setDate(attributes.getValue(IMAGE_DATE));
	//		image.setDescription(attributes.getValue(IMAGE_DESC));
			image.setFileThumbnail(attributes.getValue(FILE_THUMBNAIL));
			image.setFileWeb(attributes.getValue(FILE_WEB));
			image.setFileOriginal(attributes.getValue(FILE_ORIGINAL));
			image.setFileOriginalSize(attributes.getValue(FILE_ORIGINAL_SIZE));

			imageList.add(image);
		}
	}

	public Vector<String> getFileList() {
		Vector<String> fileList = new Vector<String>();

		File directory = new File(Globals.GALLERY_XML_DIR_LOCATION);
//		System.out.println("reading directory: " + directory.getAbsolutePath());

		File[] xmlFiles = directory.listFiles();


			if (xmlFiles != null) {
				for (int i = 0; i < xmlFiles.length; i++) {
//					System.out.println("File: " + xmlFiles[i]);
					if (this.isValidXMLExtension( xmlFiles[i])) {
					fileList.add(xmlFiles[i].getName());
					}
				}
			} else {
				Logging.error("ERROR: no xml files");
			}


	
		return fileList;
	}
	
	 private boolean isValidXMLExtension(File file){
		 boolean bValid = false;
         int i=file.getName().lastIndexOf('.');
         if (i>0 &&  i<file.getName().length()-1) {
        	 String sExt = file.getName().substring(i+1).toLowerCase();
         if (sExt.compareTo( "xml" ) == 0  ) {
        	bValid = true; 
         }
         }
         
         return bValid;
     }


}
