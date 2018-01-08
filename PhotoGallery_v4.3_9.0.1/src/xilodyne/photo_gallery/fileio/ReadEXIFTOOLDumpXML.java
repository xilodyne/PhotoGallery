package xilodyne.photo_gallery.fileio;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

//code based upon xml reader in FormatMetaData project

public class ReadEXIFTOOLDumpXML extends DefaultHandler {

	private String qNameHeading = "";
	//private String tempRowName = "";
	//private boolean OKtoPrint = false;
	private boolean startRowActive = false;

	private int iResultPosition = 999;
	private String[] sResult = new String[2]; // 0=author, 1=description

	// get the author and description from the XML dump created
	// /by the EXIFTOOL
	public String[] getEXIFTOOLdata(String sTempGallery, String sPhoto) {
		// throws SAXException, ParserConfigurationException, IOException,
		// FileNotFoundException {

		String sGallery = sTempGallery;
		int iLocation = sGallery.indexOf(".xml");
		sGallery = sGallery.substring(0, iLocation);

		StringBuffer sbFileName = new StringBuffer();
		// sbFileName.append(Globals.CATALINE_HOME);
		sbFileName.append(Globals.GALLERY_METADATA_FILE_LOCATION);
		sbFileName.append("/");
		sbFileName.append(sGallery);
		sbFileName.append("/");
		sbFileName.append(sPhoto + ".exiftool.xml");
		
		Logging.info(this.getClass().getName(), "Reading EXIFTOOL dump XML file: " + sbFileName.toString());


		sResult[Globals.EXIFTOOL_AUTHOR_POS] = "";
		sResult[Globals.EXIFTOOL_DESC_POS] = "";

		SAXParserFactory factory = javax.xml.parsers.SAXParserFactory
				.newInstance();

		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			InputSource source = new InputSource(sbFileName.toString());


				parser.parse(source, this);
			
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			Logging.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sResult;
	}

	public void startPrefixMapping(String prefix, String uri) {
		// System.out.println("prefix: " + prefix + ", uri: " + uri);
	}

	/**
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		// if new heading, update variable
		String heading = this.getHeadingFromQName(qName);
		if (this.qNameHeading.compareToIgnoreCase(heading) != 0) {
			if (this.checkOKtoPrint(heading)) {
				//this.OKtoPrint = true;

				this.tableHeader(heading);
				this.qNameHeading = heading;
	//		} else {
				//this.OKtoPrint = false;
			}
		}
		this.tableRowName(this.getNameFromQName(qName));
	}

	private String getHeadingFromQName(String name) {
		int index = name.indexOf(':');
		return name.substring(0, index);

	}

	private String getNameFromQName(String name) {
		int index = name.indexOf(':');
		return name.substring(index + 1, name.length());

	}

	// if name equal to certain categories, do not print out
	private boolean checkOKtoPrint(String sName) {
		if (sName.toLowerCase().equals("ifd0"))
			return true;
		return false;
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//String heading = this.getHeadingFromQName(qName);

		// if (this.startRowActive) {
		// this.closeRowName();
		// this.tempRowName = "";
		// }

		this.startRowActive = false;

	}

	/*private void literalStatement(int subjectType, String subject,
			String predicate, String object, String lang) {
		System.out.print("{PRED: " + predicate + ", SUB: " + subject
				+ ", object: \"");
		char ch[] = object.toCharArray();
		showCharacters(ch, 0, ch.length);
		// System.out.println("\" (lang=" + lang + ")}");
	} */

	/**
	 * Show a chunk of character data.
	 *
	 * @param ch
	 *            The array of characters.
	 * @param start
	 *            The starting position.
	 * @param length
	 *            The number of characters to use.
	 * @see org.xml.sax.ContentHandler#characters
	 */
	public void characters(char ch[], int start, int length) {
		// System.out.print("XML characters: ");
		showCharacters(ch, start, length);
		// System.out.print("\n");
	}

	/**
	 * Display characters with some escaping.
	 *
	 * @param ch
	 *            The array of characters to escape.
	 * @param start
	 *            The starting position.
	 * @param length
	 *            The number of characters to escape.
	 */
	private void showCharacters(char ch[], int start, int length) {
		StringBuffer sbHold = new StringBuffer();
		// don't add special characters
		for (int i = start; i < start + length; i++) {
			switch (ch[i]) {
			case '\\':
				// System.out.print("\\\\");
				break;
			case '"':
				sbHold.append("\\\"");
				break;
			case '\n':
				// System.out.print("\\n");
				break;
			case '\r':
				// System.out.print("\\r");
				break;
			case '\t':
				// System.out.print("\\t");
				break;
			default:
				sbHold.append(ch[i]);
				break;
			}
		}
		if (this.startRowActive) {
	//		System.out.println("Result: " + sbHold.toString());
			sResult[this.iResultPosition] = sResult[this.iResultPosition] + sbHold.toString();  // append to handle & situation
	//		sResult[this.iResultPosition] = sbHold.toString();
		}
	}


	private void tableHeader(String sHeading) {
	}

	/*
	 * <h2 itemprop="name">Beach in Mexico</h2> <img src="mexico-beach.jpg"
	 * itemprop="contentUrl" /> By <span itemprop="author">Jane Doe</span>
	 * Photographed in <span itemprop="contentLocation">Puerto Vallarta,
	 * Mexico</span> Date uploaded: <meta itemprop="datePublished"
	 * content="2008-01-25">Jan 25, 2008 <span itemprop="description">I took
	 * this picture while on vacation last year.</span>
	 */
	private void tableRowName(String sName) {
		this.startRowActive = false;
		if (sName.equals(Globals.EXIFTOOL_DESC)) {
			this.startRowActive = true;
			this.iResultPosition = Globals.EXIFTOOL_DESC_POS;
		} else if (sName.equals(Globals.EXIFTOOL_AUTHOR)) {
			this.startRowActive = true;
			this.iResultPosition = Globals.EXIFTOOL_AUTHOR_POS;
		}

	}

}
