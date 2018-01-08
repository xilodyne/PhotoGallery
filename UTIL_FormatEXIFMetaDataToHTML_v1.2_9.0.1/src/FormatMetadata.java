import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xilodyne.photo_gallery.format_exit.xml.ExportEXIFHTML4;
import xilodyne.photo_gallery.format_exit.xml.ExportEXIFHTML5;
import xilodyne.photo_gallery.format_exit.xml.ExportEXIFPropObject;
import xilodyne.photo_gallery.format_exit.xml.ExportImageObjectHTML5;



/**
 * @author Austin Davis Holiday (aholiday@xilodyne.com)
 * @version 1.2_9.0.1
 *
 */
public class FormatMetadata {
	
/*
 * Do the following:
 * read exiftool xml 
 * output as html table for display
 * output as html div for schema.org formating, mainly imageObject
 */
/*
caption 
Text  The caption for this object. 

exifData 
PropertyValue  or 
Text  exif data for this object. 

representativeOfPage 
Boolean  Indicates whether this image is representative of the content of the page. 

thumbnail 
*/
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException {

		// if ((args.length <= 1 ) || (args.length > 2)){
		if (args.length <= 1) {
			displayUsage();

		} else {

			String sFunction = args[0];
			String sFileName = args[1];
			String sPropName = "";
			try {
				sPropName = args[2];
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
			
	//		System.out.println("Filename: " + sFileName );
			
			if (sFunction.compareToIgnoreCase("HTMLDisplay5") == 0) {
		//		getHTMLDisplay(sFileName);
				//ExportEXIFHTML5 readXML5 = new ExportEXIFHTML5(sFileName);
				new ExportEXIFHTML5(sFileName);


			} else if (sFunction.compareTo("HTMLSchema5") == 0) {
				//ExportImageObjectHTML5 imageObjectExport5 = new ExportImageObjectHTML5(sFileName);
				new ExportImageObjectHTML5(sFileName);
				System.out.println("				<tr><td>");
				
				//ExportEXIFPropObject exifExport = new ExportEXIFPropObject(sFileName);
				new ExportEXIFPropObject(sFileName);
				System.out.println("				</td></tr>");
			} else if (sFunction.compareTo("HTMLDisplay4") == 0) {
				//ExportEXIFHTML4 readXML5 = new ExportEXIFHTML4(sFileName, sPropName);
				new ExportEXIFHTML4(sFileName, sPropName);

			} else {
				displayUsage();
			}
		}

	}
	
	
	/*public static String getHTMLDisplay(String sFilename) throws FileNotFoundException, SAXException, ParserConfigurationException, IOException {
		CreateHTMLDisplay display = new CreateHTMLDisplay();
			display.getXMLData(sFilename);
			return "return getHTMLDisplay";
		
	} */
	public static void displayUsage() {
		System.out.println();
		System.out.println("Read exiftool xml file to extract EXIF for HTML Display or schema for HTML DIV tags.");
		System.out.println("Output is the form of HTML tags to be included in webpage.");
		System.out.println("");
		System.out.println(
				"Usage:\tjava FormatMetadata HTMLDisplay5 filexxx.jpg.exiftool.xml >> filexxx.jpg.exiftool.xml.meta.html5.txt ");
		System.out.println();
		System.out.println(
				"\tjava FormatMetadata HTMLDisplay4 filexxx.jpg.exiftool.xml gallery_user.property > filexxx.jpg.exiftool.xml.meta.html4.txt");
		System.out.println();
		System.out.println(
				"\tjava FormatMetadata HTMLSchema5 filexxx.jpg.exiftool.xml > filexxx.jpg.exiftool.xml.meta.html5.txt");
		System.out.println("\t(used to get author and description information as property object)");
	
	}


}
