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

public class ExportEXIFPropObject extends DefaultHandler {
	
	private String qNameHeading = "";
	private boolean OKtoPrint = false;
	private boolean startRowActive = false;

	public ExportEXIFPropObject() {
	}

	public ExportEXIFPropObject(String sFilename) throws SAXException,
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
		if (sName.toLowerCase().equals("exififd"))
			return true;
		return false;
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (this.startRowActive) {
			this.closeRowName();
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
		if (this.OKtoPrint)
			System.out.print(sbHold.toString());
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

/*	private void XtableHeader(String sHeading) {
		System.out.println("<tr>\n");
		System.out.println("	<td align=\"center\" colspan=\"2\" bgcolor=\"silver\">");
		System.out.println("		<font color=\"black\" size=\"0\" face=\"arial\">");
		System.out.print(sHeading);
		System.out.println("		</font>\n");
		System.out.println("	</td>\n");
		System.out.println("</tr>\n");
	}*/
	
	private void tableHeader(String sHeading) {}
/*	private void tableHeader(String sHeading) {
		System.out.print("<tr>\n");
		System.out.print("	<th colspan=\"2\" class=\"exifhtml\">");
		System.out.print(sHeading);
		System.out.print("</th>\n");
		System.out.print("</tr>\n");
	}
*/
/*	private void XtableRowName(String sName) {
		if (this.OKtoPrint) {
			System.out.println("<tr>\n");
			System.out.println("	<th align=\"right\" bgcolor=\"silver\">\n");
			System.out.print("			<font color=\"black\" size=\"1\" face=\"arial\">\n");	
			System.out.print(sName);
			System.out.println("</font>\n");
			System.out.println("	</th>\n");
			System.out.println("	<td>\n");
			System.out.print("		<font color=\"silver\" size=\"0\" face=\"arial\">\n");
			this.startRowActive = true;
		}*/
	
	/*
	  <div itemprop="exifData" itemscope itemtype="http://schema.org/PropertyValue">
	      <meta itemprop="name" content="FNumber">
	      <meta itemprop="value" content="f/4.0">
	  </div>

		 */
		private void tableRowName(String sName) {
			if (this.OKtoPrint) {
				System.out.print("					<div itemprop=\"exifData\" itemscope itemtype=\"http://schema.org/PropertyValue\">\n");
				System.out.print("						<meta itemprop=\"name\" content=\"" + sName + "\">\n");
				System.out.print("						<meta itemprop=\"value\" content=\"");
				this.startRowActive = true;
			}
	}
		
/*		private void closeRowName() {
			System.out.println("</font>\n");
			System.out.println("	</td>\n");
			System.out.println("</tr>\n");
		
		} */
		
		/*
		  <div itemprop="exifData" itemscope itemtype="http://schema.org/PropertyValue">
		      <meta itemprop="name" content="FNumber">
		      <meta itemprop="value" content="f/4.0">
		  </div>

			 */

		private void closeRowName() {
			System.out.print("\">\n");
			System.out.print("					</div>\n");
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


