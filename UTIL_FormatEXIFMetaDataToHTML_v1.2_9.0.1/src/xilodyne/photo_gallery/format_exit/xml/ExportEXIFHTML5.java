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

/**
 * @author aholiday
 *
 */
public class ExportEXIFHTML5 extends DefaultHandler {

	private String qNameHeading = "";
	private boolean OKtoPrint = false;
	private boolean startRowActive = false;

	public ExportEXIFHTML5() {
	}

	public ExportEXIFHTML5(String sFilename) throws SAXException,
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
		if (sName.toLowerCase().equals("rdf"))
			return false;
		if (sName.toLowerCase().equals("system"))
			return false;
		if (sName.toLowerCase().equals("exiftool"))
			return false;
		if (sName.toLowerCase().equals("XMP-xmpMM".toLowerCase()))
			return false;
		if (sName.toLowerCase().equals("XMP-x".toLowerCase()))
			return false;
		if (sName.toLowerCase().equals("composite"))
			return false;
		return true;
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

/*	private void XtableHeader(String sHeading) {
		System.out.println("<tr>\n");
		System.out.println("	<td align=\"center\" colspan=\"2\" bgcolor=\"silver\">");
		System.out.println("		<font color=\"black\" size=\"0\" face=\"arial\">");
		System.out.print(sHeading);
		System.out.println("		</font>\n");
		System.out.println("	</td>\n");
		System.out.println("</tr>\n");
	}*/
	
	private void tableHeader(String sHeading) {
		System.out.print("				<tr>\n");
		System.out.print("					<th colspan=\"2\" class=\"exifhtml\">");
		System.out.print(sHeading);
		System.out.print("</th>\n");
		System.out.print("				</tr>\n");
	}

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
		private void tableRowName(String sName) {
			if (this.OKtoPrint) {
				System.out.print("				<tr>\n");
				System.out.print("					<td class=\"exifhtml_name\">");
				System.out.print(sName);
				System.out.print("</td>\n");
				System.out.print("					<td class=\"exifhtml_value\">");
				this.startRowActive = true;
			}
	}
		
/*		private void closeRowName() {
			System.out.println("</font>\n");
			System.out.println("	</td>\n");
			System.out.println("</tr>\n");
		
		} */
		private void closeRowName() {
			System.out.print("</td>\n");
			System.out.print("				</tr>\n");
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
/*
  class City{
 
    private int id;
    private String name;
 
    public City()    {
 
    }
 
    public City( int id, String name )    {
            this.id = id;
            this.name = name;
    }
 
    public int getId()    {
            return id;
    }
 
    public void setId( int id )    {
            this.id = id;
    }
 
    public String getName()    {
            return name;
    }
 
    public void setName( String name )   {
            this.name = name;
    }
 
}

public class Main {
 
	public static void main(String[] args){
		City c1 = new City(100, &quot;Newark&quot;);
		City c2 = new City(200, &quot;New York&quot;);
 
		ArrayList&lt;City&gt; l = new ArrayList&lt;City&gt;();
		l.add(c1);
		l.add(c2);
 
		stAXToXml(l);
	}
 
	public static void stAXToXml(List&lt;City&gt; list) {
		String xmlStr = null;
		try {
			if (null != list &amp;&amp; !list.isEmpty()) {
				StringWriter writerStr = new StringWriter();
				// PrintWriter writerXml = new PrintWriter(new
				// OutputStreamWriter( new FileOutputStream( &quot;city-StAX.xml&quot; ),
				// &quot;utf-8&quot; ));
 
				// define XMLEventWriter and XMLStreamWriter factory instance
				XMLOutputFactory xof = XMLOutputFactory.newInstance();
				// only one of the following is required.
				XMLStreamWriter xmlsw = xof.createXMLStreamWriter(writerStr);
				// XMLStreamWriter xmlsw = xof.createXMLStreamWriter( writerXml
				// );
 
				// write declaration
				xmlsw.writeStartDocument(&quot;UTF-8&quot;, &quot;1.0&quot;);
				xmlsw.writeStartElement(&quot;cities&quot;);
 
				// write comments
				xmlsw.writeComment(&quot;city info&quot;);
				for (City po : list) {
					xmlsw.writeStartElement(&quot;city&quot;);
 
					// add &lt;id&gt; node
					xmlsw.writeStartElement(&quot;id&quot;);
					xmlsw.writeCharacters(String.valueOf(po.getId()));
					// end &lt;id&gt; node
					xmlsw.writeEndElement();
 
					// add &lt;name&gt; node
					xmlsw.writeStartElement(&quot;name&quot;);
					xmlsw.writeCharacters(po.getName());
					// end &lt;name&gt; node
					xmlsw.writeEndElement();
 
					xmlsw.writeEndElement();
				}
				// end &lt;cities&gt; node
				xmlsw.writeEndElement();
				// end XML document
				xmlsw.writeEndDocument();
				xmlsw.flush();
				xmlsw.close();
 
				xmlStr = writerStr.getBuffer().toString();
				writerStr.close();
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(&quot;StAX:&quot; + xmlStr);// print result
	}
}
*/
