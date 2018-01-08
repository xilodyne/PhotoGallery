package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.fileio.ReadEXIFHTML;
import xilodyne.photo_gallery.fileio.ReadEXIFTOOLDumpXML;
import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterPhoto;


public class PhotoHTML4 extends Photo implements PhotoInterface  {

	AssembleHTTP assembleHTTP = new AssembleHTTP();
	ReadEXIFHTML exif_html = new ReadEXIFHTML();
	ReadEXIFTOOLDumpXML exifData = new ReadEXIFTOOLDumpXML();



	public String dspImage (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

//		int index = assembleHTTP.getIndexOfImageFromList( paramInfo);
//		Image image  = new Image();
//		image = assembleHTTP.getImageAtIndex( paramInfo, index);
		Image image = paramInfo.getImageFromList();
		
		String[] sEXIFAuthorDesc = exifData.getEXIFTOOLdata(archive.getXMLFilename(), image.getFileOriginal());


		sbHTML.append("<center>\n");
		sbHTML.append("<table align=\"center\" width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		
		sbHTML.append("<tr valign =\"top\" align=\"center\">\n");
		sbHTML.append("  <td width =\"100\" height=\"100%\">\n");
		sbHTML.append("		<table  height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n");
		sbHTML.append("			<tr valign=\"center\"><td height=\"200\" align=\"center\">\n");
		sbHTML.append("				" + this.dspNavigationTop(archive) + "\n");
		sbHTML.append("				<br></br>&nbsp;\n");
		sbHTML.append("			</td></tr>\n");
		sbHTML.append("			<tr><td align=\"right\">\n");
		sbHTML.append("				"  + this.dspNavigationPrevious(archive, paramInfo) + "\n");
		sbHTML.append("			</td></tr>\n");
		sbHTML.append("			<tr><td height=\"200\" align=\"center\">\n");
		sbHTML.append("				" + this.dspNavigationDownload(archive, image) + "\n");
		sbHTML.append("				<br>&nbsp;</br>" + this.dspNavigationEXIF(archive, paramInfo) + "\n");
		sbHTML.append("			</td></tr>\n");
/*		sbHTML.append("			<tr><td height=\"200\" align=\"center\">\n");
		sbHTML.append("				<br>&nbsp;</br>\n");
		sbHTML.append("				<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"0\" face=\"arial\"><button id=\"buttonexif\" onclick=\"toggleHidden()\">Show Metadata</button>\n");
		sbHTML.append("				</font>\n");
		sbHTML.append("			</td></tr>\n");
*/		sbHTML.append("		</table>\n");
		sbHTML.append("  </td>\n");
		
		sbHTML.append("  <td align=\"center\">\n");
//		sbHTML.append("		<div itemscope itemtype=\"http://schema.org/ImageObject\">\n");
//		sbHTML.append("		<table><tr><td align=\"center\">\n");
		sbHTML.append("    		<img  width=\"800\" itemprop=\"contentUrl\" src=\"" + Globals.HTTP_Refer +"/");
		sbHTML.append( archive.getDirWeb() + "/" + paramInfo.getImageFile()+ "\" border=\"1\"" );
		sbHTML.append(" alt=\"" + Globals.USER_WEB_TITLE +" - " + archive.getXMLFilenameTitle() + "\" ");
		sbHTML.append(" title=\"" + Globals.USER_WEB_TITLE +" - " + archive.getXMLFilenameTitle() + ": " + image.getTitle() + "\" ");
		sbHTML.append(">\n");
//		sbHTML.append("			<tr id=\"showexif\" hidden><td>\n");
	//	sbHTML.append(this.dspEXIFData(archive, paramInfo));
//		sbHTML.append("			</td></tr>\n");
//		sbHTML.append("		</table>\n");
//		sbHTML.append("</div>\n");
		sbHTML.append("  </td>\n");
		
		sbHTML.append("  <td  width =\"100\" height=\"100%\" >\n");
		sbHTML.append("		<table  height=\"100%\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n");
		sbHTML.append("			<tr valign=\"center\"><td  height=\"200\" align=\"left\">\n");
	//	sbHTML.append("						<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Date: ");
	//	sbHTML.append( this.dspEXIF(archive, paramInfo) + "</font>\n");

		sbHTML.append("			</td></tr>\n");
		sbHTML.append("			<tr><td align=\"left\">\n");
		sbHTML.append("				" + this.dspNavigationNext(archive, paramInfo) + "\n");
		sbHTML.append("			</td></tr>\n");
		
		sbHTML.append("			<tr><td align=\"left\"  height=\"200\"  valign=\"center\">\n");
		sbHTML.append("				<table>\n");
		sbHTML.append("					<tr><td>\n");
		sbHTML.append("						<bold><font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+2\" face=\"arial\">" + image.getTitle() + "</font></bold>\n");
		sbHTML.append("					</td></tr>\n");
		sbHTML.append("					<tr><td>\n");
		sbHTML.append("						<bold><font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+0\" face=\"arial\">" + image.getDate() + "</font></bold>\n");
		sbHTML.append("					</td></tr>\n");
		sbHTML.append("					<tr><td>\n");
		sbHTML.append("						<bold><font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+0\" face=\"arial\">" + sEXIFAuthorDesc[Globals.EXIFTOOL_AUTHOR_POS] + "</font></bold>\n");
		sbHTML.append("					</td></tr>\n");
		sbHTML.append("					<tr><td>\n");
		sbHTML.append("						<bold><font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+0\" face=\"arial\">" + sEXIFAuthorDesc[Globals.EXIFTOOL_DESC_POS].replaceAll("&(?!amp;)", "&amp;") + "</font></bold>\n");
		sbHTML.append("					</td></tr>\n");
//		sbHTML.append("					<tr><td class=\"large\">" + image.getTitle() + "</td></tr>\n");
//		sbHTML.append("					<tr><td class=\"normal\">" + image.getDate() + "</td></tr>\n");
//		sbHTML.append("					<tr><td class=\"normal\">" + this.getEXIFAuthor(archive, paramInfo) + "</td></tr>\n");
//		sbHTML.append("					<tr><td class=\"normal\">" + this.getEXIFComments(archive, paramInfo) + "</td></tr>\n");
		sbHTML.append("				</table>\n");
		
		sbHTML.append("			</td></tr>\n");
		sbHTML.append("		</table>\n");
		sbHTML.append("  </td>\n");
		sbHTML.append("</tr>\n");

		
		sbHTML.append("</table>\n");
		

		return sbHTML.toString();
	}

	
	private String dspNavigationPrevious (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

//		int index = assembleHTTP.getIndexOfImageFromList( paramInfo);
//		Image imageBefore;
//		imageBefore = assembleHTTP.getImageAtIndex( paramInfo, index-1);
		int index = paramInfo.getIndexOfImageFromList();
		Image imageBefore;
		imageBefore = paramInfo.getImageAtIndex(index-1);
		
		if (index > 0 ) {
			sbHTML.append("		<a ");
			sbHTML.append(" href=\"");
			sbHTML.append( assembleHTTP.makeHREFNextPhoto( imageBefore.getFileWeb()));
			sbHTML.append("\">\n");
			sbHTML.append("				<img src=\"" + Globals.HTTP_Refer + "/support_files/PointersLeft.gif\" height=\"60\" alt=\"");
			sbHTML.append("Previous Image: " + imageBefore.getTitle() + "\" border=\"0\">");
			sbHTML.append("</a>");			
		} else {
			sbHTML.append( "&nbsp;&nbsp\n");
		}

		return sbHTML.toString();
	}

	private String dspNavigationNext (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

//		int index = assembleHTTP.getIndexOfImageFromList( paramInfo);
//		Image imageAfter = new Image();
//		imageAfter = assembleHTTP.getImageAtIndex( paramInfo, index+1 );
		int index = paramInfo.getIndexOfImageFromList();
		Image imageAfter = new Image();
		imageAfter = paramInfo.getImageAtIndex( index+1 );
			
			if (index < paramInfo.getImageListSize() -1) {
				sbHTML.append("		<a ");
				sbHTML.append(" href=\"");
				sbHTML.append( assembleHTTP.makeHREFNextPhoto( imageAfter.getFileWeb()));
				sbHTML.append("\">\n");
				sbHTML.append("				<img src=\"" + Globals.HTTP_Refer + "/support_files/PointersRight.gif\" height=\"60\"  alt=\"");
				sbHTML.append("Next Image: " + imageAfter.getTitle() + "\" border=\"0\">");

				sbHTML.append("</a>");			
			} else {
				sbHTML.append( "&nbsp;&nbsp\n");
			}

		return sbHTML.toString();
	}
	
	private String dspNavigationTop (ArchiveInfo archive) {
		StringBuffer sbHTML = new StringBuffer();
		sbHTML.append("		<a href=\"");
		sbHTML.append(this.assembleHTTP.makeHREFGalleryHome(archive.getXMLFilename()) ) ;
		sbHTML.append("\">\n");
		sbHTML.append("<img src=\"" + Globals.HTTP_Refer + "/support_files/PointersUp.gif\" height=\"20\" width=\"30\" alt=\""); 
		sbHTML.append(Globals.USER_WEB_TITLE + "\" border=\"0\">");
		sbHTML.append("</a>\n");
	
	//	sbHTML.append(this.assembleHTTP.makeHREFGalleryHome(archive.getXMLFilename()));	
	//	sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"0\" face=\"arial\">Return to <br>\n"); 
	//	sbHTML.append(this.assembleHTTP.makeGalleryHomeTitle(archive));
	//	sbHTML.append("</font></bold></a>");
		return sbHTML.toString();
	}
	
	
	private String dspNavigationDownload (ArchiveInfo archive, Image image) {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append(this.assembleHTTP.makeHREFOriginalsButton(image));
		sbHTML.append("\n");
		
		float fFileSize = Integer.parseInt(image.getFileOriginalSize()) ;
		fFileSize = fFileSize / (1024 * 1024);

		Logging.info(this.getClass().getName(), "MB File size: " + fFileSize);
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"0\" face=\"arial\"> Download Original (");
		sbHTML.append(String.format("%.2f", fFileSize) + " MB)");
		sbHTML.append("</font></bold></a>");
		return sbHTML.toString();
	}	

	private String dspNavigationEXIF (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append(this.assembleHTTP.makeHREF_EXIFButton(paramInfo.getImageFile()));
		sbHTML.append("\n");

		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"0\" face=\"arial\"> View EXIF data");
		sbHTML.append("</font></bold></a>");
		return sbHTML.toString();
	}
	
	private String dspNavigationReturnPhoto (String XMLFilename, String imageFileWeb) {
		StringBuffer sbHTML = new StringBuffer();

	//	int index = assembleHTTP.getIndex( paramInfo);
	//	Image image = new Image();
	//	image = assembleHTTP.getImageAtIndex( paramInfo, index);
		
		sbHTML.append("		<a ");
		sbHTML.append(" href=\"");
		sbHTML.append(assembleHTTP.makeHREFNextPhoto(imageFileWeb));
		sbHTML.append("\">\n");

		//sbHTML.append( assembleHTTP.makeHTTPImageHome(archive.getXMLFilename(), paramInfo.getImageFile()));
		sbHTML.append("<img src=\"" + Globals.HTTP_Refer + "/support_files/PointersUp.gif\" height=\"20\" width=\"30\" alt=\""); 
		sbHTML.append(Globals.USER_WEB_TITLE + "\" border=\"0\">");

		sbHTML.append("</a>");			

		return sbHTML.toString();
	}
	

	
	
	public String dspEXIFData (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();


//		int index = assembleHTTP.getIndexOfImageFromList( paramInfo);
//		Image image = new Image();
//		image = assembleHTTP.getImageAtIndex( paramInfo, index);
		Image image = paramInfo.getImageFromList();
		
		sbHTML.append("<table border=\"1\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		
		sbHTML.append("<tr>\n");
		sbHTML.append("	<td align=\"center\" colspan=\"2\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append(this.dspNavigationReturnPhoto(archive.getXMLFilename(), paramInfo.getImageFile()));
		sbHTML.append("	</td>\n");
		sbHTML.append("</tr>");
		
		sbHTML.append("<tr>\n");
		sbHTML.append("	<td align=\"center\" colspan=\"2\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\">\n");

		sbHTML.append("		<font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"2\" face=\"arial\">Photo Metadata</font>\n");

		sbHTML.append("	</td>\n");
		sbHTML.append("</tr>");
		sbHTML.append("<tr>\n");
		sbHTML.append("	<td align=\"center\" colspan=\"2\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("&nbsp;</td>\n");
		sbHTML.append("</tr>");

		sbHTML.append("<tr>\n");
		sbHTML.append("	<th align=\"right\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\">\n");
		sbHTML.append("		<font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"1\" face=\"arial\">Title</font>\n"); 
		sbHTML.append("	</th>\n");
		sbHTML.append("	<td>\n");
		sbHTML.append("		<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"0\" face=\"arial\">");
		sbHTML.append( image.getTitle() + "</font>\n");
		sbHTML.append("	</td>\n");
		sbHTML.append("</tr>\n");
		sbHTML.append("<tr>\n");
		sbHTML.append("	<th align=\"right\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\">\n");
		sbHTML.append("		<font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"1\" face=\"arial\">Date</font>\n"); 
		sbHTML.append("	</th>\n");
		sbHTML.append("	<td>\n");
		sbHTML.append("		<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"0\" face=\"arial\">");
		sbHTML.append( image.getDate() + "</font>\n");
		sbHTML.append("	</td>\n");
		sbHTML.append("</tr>\n");
			sbHTML.append(this.exif_html.loadEXIFHTML(Globals.checkUserAgentHTML5(), archive.getXMLFilename(), image.getFileOriginal()));
		
		sbHTML.append("</table>\n");

	return sbHTML.toString();


	}

	
	
	public String dspOriginals (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append( "<center>\n");
		sbHTML.append( "<img  src=\"" + Globals.HTTP_Refer +"/");
		sbHTML.append( archive.getDirOriginals() + "/" + paramInfo.getImageFile()+ "\" border=\"1\">\n");

		return sbHTML.toString();
	}

	public String dspDownload (ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append( "<center>\n");
		sbHTML.append( "<img  src=\"" + Globals.HTTP_Refer +"/");
		sbHTML.append( archive.getDirOriginals() + "/" + paramInfo.getImageFile()+ "\" border=\"1\">\n");

		return sbHTML.toString();
	}
	
}
