package xilodyne.photo_gallery.format_exit.xml;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExportImageObjectHTML5 extends DefaultHandler {
	
	private String qNameHeading = "";
	private String tempRowName = "";
	private boolean OKtoPrint = false;
	private boolean startRowActive = false;

	public ExportImageObjectHTML5() {
	}

	public ExportImageObjectHTML5(String sFilename) throws SAXException,
			ParserConfigurationException, IOException, FileNotFoundException {
		// archive = sArchive;
		// imageList = paramInfo.getImageList();

		// XMLFile = Globals.GALLERY_XML_DIR_LOCATION + "/" +
		// archive.getXMLFilename();

		// Logging.info(this.getClass().getName(), "Reading(1) XML file: " +
		// XMLFile);

		SAXParserFactory factory = javax.xml.parsers.SAXParserFactory
				.newInstance();

		SAXParser parser = factory.newSAXParser();
		InputSource source = new InputSource(sFilename);

		parser.parse(source, this);

	}

	/*
	 * public ReadGalleryXML(String archiveXMLFilename, ParameterPhoto
	 * paramInfo) throws SAXException, ParserConfigurationException,
	 * IOException,FileNotFoundException { imageList = paramInfo.getImageList();
	 * // imageList.clear();
	 * 
	 * XMLFile = Globals.GALLERY_XML_DIR_LOCATION + "/" + archiveXMLFilename;
	 * 
	 * Logging.info(this.getClass().getName(), "Reading(2) XML file: " +
	 * XMLFile);
	 * 
	 * SAXParserFactory factory =
	 * javax.xml.parsers.SAXParserFactory.newInstance();
	 * 
	 * SAXParser parser = factory.newSAXParser(); InputSource source = new
	 * InputSource(XMLFile);
	 * 
	 * parser.parse(source, this);
	 * 
	 * 
	 * }
	 */

	public void startPrefixMapping(String prefix, String uri) {
		System.out.println("prefix: " + prefix + ", uri: " + uri);
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
				this.OKtoPrint = true;
				
				this.tableHeader(heading);
				this.qNameHeading = heading;
			} else {
				this.OKtoPrint = false;
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
	
		if (this.startRowActive) {
			this.closeRowName();
			this.tempRowName = "";
		}	
		this.startRowActive = false;
	}

	public void literalStatement(int subjectType, String subject,
			String predicate, String object, String lang) {
		System.out.print("{PRED: " + predicate + ", SUB: " + subject
				+ ", object: \"");
		char ch[] = object.toCharArray();
		showCharacters(ch, 0, ch.length);
		System.out.println("\" (lang=" + lang + ")}");
	}

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
		if (this.startRowActive)
			System.out.print(sbHold.toString().replaceAll("&(?!amp;)", "&amp;"));
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
	@SuppressWarnings("unused")
	private void showCharactersX(char ch[], int start, int length) {
		for (int i = start; i < start + length; i++) {
			switch (ch[i]) {
			case '\\':
				System.out.print("\\\\");
				break;
			case '"':
				System.out.print("\\\"");
				break;
			case '\n':
				System.out.print("\\n");
				break;
			case '\r':
				System.out.print("\\r");
				break;
			case '\t':
				System.out.print("\\t");
				break;
			default:
				System.out.print(ch[i]);
				break;
			}
		}
	}

	
	private void tableHeader(String sHeading) {}

/*	  <h2 itemprop="name">Beach in Mexico</h2>
	  <img src="mexico-beach.jpg" itemprop="contentUrl" />
	  By <span itemprop="author">Jane Doe</span>
	  Photographed in
	    <span itemprop="contentLocation">Puerto Vallarta, Mexico</span>
	  Date uploaded:
	    <meta itemprop="datePublished" content="2008-01-25">Jan 25, 2008
	  <span itemprop="description">I took this picture while on vacation last year.</span>
		 */
		private void tableRowName(String sName) {
			this.startRowActive = false;
			if (this.OKtoPrint) {
				if (sName.equals("XPComment")) {
					this.tempRowName = sName;
					this.startRowActive = true;
					System.out.print("				<tr>\n");
					System.out.print("					<td class=\"exifhtml_name\">Description</td>\n");
		//			System.out.print("</tr>\n");
		//			System.out.print("<tr>\n");
					System.out.print("					<td class=\"exifhtml_value\"><span itemprop=\"description\">" );					
	/*			} else if (sName.equals("XPComment")) {
						this.tempRowName = sName;
						this.startRowActive = true;
						System.out.print("<tr><td><span itemprop=\"description\">" );					
		*/		} else if (sName.equals("XPAuthor")) {
					this.tempRowName = sName;
					this.startRowActive = true;
					System.out.print("				<tr>\n");
					System.out.print("					<td class=\"exifhtml_name\">Author</td>\n");
		//			System.out.print("</tr>\n");
		//			System.out.print("<tr>\n");
					System.out.print("					<td class=\"exifhtml_value\"><span itemprop=\"author\">" );					
				}
				
			}

	}
		
		private void closeRowName() {
			if (this.tempRowName.equals("XPComment")) {
				System.out.print("</span></td>\n");
				System.out.print("				</tr>\n");
				
			} else if (this.tempRowName.equals("XPAuthor")) {
				System.out.print("</span></td>\n");
				System.out.print("				</tr>\n");
			}

			System.out.println();
		
		}
/*<tr>
	<td align="center" colspan="2" bgcolor="silver">
		<font color="black" size="0" face="arial">EXIF Data</font>
	</td>
</tr><tr>
	<th align="right" bgcolor="silver">
		<font color="black" size="1" face="arial">Sensing Method</font>
	</th>
	<td>
		<font color="silver" size="0" face="arial">One-chip color area sensor</font>
	</td>
</tr>*/
	

}


