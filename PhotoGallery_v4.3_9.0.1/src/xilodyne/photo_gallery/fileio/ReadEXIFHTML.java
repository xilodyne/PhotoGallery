package xilodyne.photo_gallery.fileio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;


public class ReadEXIFHTML {
	private FileInputStream fstream = null;

	String sGallery;
	String sPhoto;

	/*public String XloadEXIF()  {
	// Open the file	
		StringBuffer sbHold = new StringBuffer();
		try {
	Logging.info(this.getClass().getName(), "Reading: C:/Backup/JavaCode/Java8-Luna/Projects/Web/PhotoGalleryProjects/PhotoGallery-v2.1/WebContent/conf/gallery.xml_files/metadata/Ana_And_Austin/20060723-AustinZermatt.jp.exif.html.txt");

	FileInputStream fstream = new FileInputStream("C:/Backup/JavaCode/Java8-Luna/Projects/Web/PhotoGalleryProjects/PhotoGallery-v2.1/WebContent/conf/gallery.xml_files/metadata/Ana_And_Austin/20060723-AustinZermatt.jp.exif.html.txt");
	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

	Logging.info(this.getClass().getName(), "file opened");
	String strLine = "";

	//Read File Line By Line
	while ((strLine = br.readLine()) != null)   {
	  // Print the content on the console
		sbHold.append(strLine);
		Logging.info(this.getClass().getName(), "found: " + strLine );
//	  System.out.println (strLine);
	}
*/
	public String loadEXIFHTML(boolean HTML5, String sTempGallery, String sTempPhoto) {

		StringBuffer sbHold = new StringBuffer();

		sGallery = sTempGallery;
		int iLocation = sGallery.indexOf(".xml");
		sGallery = sGallery.substring(0, iLocation);

		sPhoto = sTempPhoto;

		// this.getCatalinaHome();
		StringBuffer sbFileName = new StringBuffer();
		// sbFileName.append(Globals.CATALINE_HOME);
		sbFileName.append(Globals.GALLERY_METADATA_FILE_LOCATION);
		sbFileName.append("/");
		sbFileName.append(sGallery);
		sbFileName.append("/");
		
		if (HTML5) {
		sbFileName.append(sPhoto + ".exiftool.xml.meta.html5.txt");
		} else {
			sbFileName.append(sPhoto + ".exiftool.xml.meta.html4.txt");
		}

		Logging.info(this.getClass().getName(),
				"Reading: " + sbFileName.toString());

		// Output.toConsole("Reading --> " + sbFileName.toString() );
		try {
			this.fstream = new FileInputStream(sbFileName.toString());
		//	BufferedReader br = new BufferedReader(new InputStreamReader(
		//			fstream));
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream, "UTF-8"));

	//		System.out.println("test char: ブラウザを�?�使 ");
	//		Logging.info(this.getClass().getName(), "ブラウザを�?�使");
			Logging.info(this.getClass().getName(), "file opened");
			String strLine = "";

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.length() > 0) {
					
				sbHold.append(strLine + "\n");
	//			Logging.info(this.getClass().getName(), "found: " + strLine);
				}
			}

		//	Logging.info(this.getClass().getName(), "done reading");

			// Close the input stream
			br.close();
			fstream.close();
		} catch (IOException e) {
			Logging.error(e);
		}
		return sbHold.toString();
	}

}
