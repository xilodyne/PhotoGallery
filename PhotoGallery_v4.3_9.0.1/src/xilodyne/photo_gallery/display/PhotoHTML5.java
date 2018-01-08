package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.fileio.ReadEXIFHTML;
import xilodyne.photo_gallery.fileio.ReadEXIFTOOLDumpXML;
import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterPhoto;

/**
 * @author aholiday
 *
 */
public class PhotoHTML5 extends Photo implements PhotoInterface {

	AssembleHTTP assembleHTTP = new AssembleHTTP();
	ReadEXIFHTML exif_html = new ReadEXIFHTML();
	ReadEXIFTOOLDumpXML exifData = new ReadEXIFTOOLDumpXML();

	public String dspImage(ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

//		int index = assembleHTTP.getIndexOfImageFromList(paramInfo);
//		Image image = new Image();
//		image = assembleHTTP.getImageAtIndex(paramInfo, index);
		
		Image image = paramInfo.getImageFromList();


		String[] sEXIFAuthorDesc = exifData.getEXIFTOOLdata(
				archive.getXMLFilename(), image.getFileOriginal());

		sbHTML.append("<div class=\"mainImg-boundingbox\">\n");

		sbHTML.append("	<div class=\"mainImg-wrapper\">\n");
		sbHTML.append("		<div class=\"mainImg-center\">\n");
		sbHTML.append("			<figure class=\"fmainImg\">\n");
		sbHTML.append("				<img id=\"mainImage\" class=\"fimainImg\" itemprop=\"contentUrl\" ");
		sbHTML.append("src=\"" + Globals.HTTP_Refer + "/");
		sbHTML.append(archive.getDirWeb() + "/" + paramInfo.getImageFile()
				+ "\"");
		sbHTML.append(" alt=\"" + Globals.USER_WEB_TITLE + " - "
				+ archive.getXMLFilenameTitle() + "\" ");
		sbHTML.append(" title=\"" + Globals.USER_WEB_TITLE + " - "
				+ archive.getXMLFilenameTitle() + ": " + image.getTitle()
				+ "\" ");
		sbHTML.append(">\n");

		sbHTML.append("			</figure>\n");
		sbHTML.append("		</div> <!-- mainImg-center -->\n");
		sbHTML.append("		<script type=\"text/javascript\">\n");
		sbHTML.append(" 		preimg='");
		sbHTML.append(this.jsNavigationPrevious(archive, paramInfo));
		sbHTML.append("';\n");		
		sbHTML.append(" 		nxtimg='");
		sbHTML.append(this.jsNavigationNext(archive, paramInfo));
		sbHTML.append("';\n");
		sbHTML.append(" 		topimg='");
		sbHTML.append(this.jsNavigationTop(archive));
		sbHTML.append("';\n");

		sbHTML.append("			window.onload = function(e) {\n");
		sbHTML.append("				setImageSize();\n");
		sbHTML.append("			}\n");
		sbHTML.append("		</script>\n");

		sbHTML.append("	</div> <!-- mainImg-wrapper -->\n");

		sbHTML.append("	<div id='image_prev'>\n");
		sbHTML.append("		" + this.dspNavigationPrevious(archive, paramInfo));
		sbHTML.append("	</div>\n");

		sbHTML.append("	<div id='image_next'>\n");
		sbHTML.append("		" + this.dspNavigationNext(archive, paramInfo));
		sbHTML.append("	</div>\n");
		sbHTML.append("	<div class=\"footer-center\">\n");

		sbHTML.append("		<div id=\"footer\">\n");
		sbHTML.append("			" + this.dspNavigationTop(archive));
		sbHTML.append("			" + this.dspNavigationDownload(archive, image) + "\n");
		sbHTML.append("			<button class=\"button\" id=\"buttoncaption\" onclick=\"toggleCaption()\">Show Caption</button>\n");
		sbHTML.append("			<button class=\"button\" id=\"buttonmeta\" onclick=\"toggleMeta()\">Show Metadata</button>\n");

		sbHTML.append("			<div itemscope itemtype=\"http://schema.org/ImageObject\">\n");
		sbHTML.append("				<meta itemprop=\"contentUrl\" content=\""
				+ Globals.HTTP_Refer + "/");
		sbHTML.append(archive.getDirWeb() + "/" + paramInfo.getImageFile()
				+ "\"/>\n");
		sbHTML.append("				<meta itemprop=\"encodingFormat\" content=\"jpg\"/>\n");

		sbHTML.append("				<div class=\"secondrow_right hideText\" id=\"toggleCaptionText\">\n");
		sbHTML.append("					<p class=\"large\">Title: <span itemprop=\"name\">"
				+ image.getTitle() + "</span></p>\n");
		sbHTML.append("					<p class=\"normal\">Date: <meta itemprop=\"dateCreated\" content=\""
				+ image.getDateYYYYMMDD() + "\">" + image.getDate() + "</p>\n");
		sbHTML.append("					<p class=\"normal\">Author: <span itemprop=\"author\">"
				+ sEXIFAuthorDesc[Globals.EXIFTOOL_AUTHOR_POS]
				+ "</span></p>\n");
		sbHTML.append("					<p class=\"normal\">Description: <span itemprop=\"description\">"
				+ sEXIFAuthorDesc[Globals.EXIFTOOL_DESC_POS].replaceAll(
						"&(?!amp;)", "&amp;") + "</span></p>\n");
		sbHTML.append("				</div> <!-- second row right -->\n");

		sbHTML.append("				<script type=\"text/javascript\">\n");
		sbHTML.append("					var caption_status = getCookie(\"caption\"); // page shows hide by default \n");
		sbHTML.append("						if (caption_status == \"hide\") {\n");
		sbHTML.append("							document.getElementById('toggleCaptionText').classList.toggle('hideText');\n");
		sbHTML.append("							document.getElementById(\"buttoncaption\").innerHTML = \"Hide Caption\";\n");
		sbHTML.append("					}\n");

		sbHTML.append("				</script>\n");
		sbHTML.append("				<div class=\"thirdrow_left hideText\" id=\"toggleMetaText\">\n");
		sbHTML.append("					" + this.dspEXIFData(archive, paramInfo) + "\n");


		sbHTML.append("				</div> <!-- thirdrow_left -->\n");
		sbHTML.append("			</div> <!-- image object -->\n");

		if (Globals.USER_DEBUG) {
			debugWindowsValues();
		}
		sbHTML.append("		</div> <!-- footer -->\n");
		sbHTML.append("	</div> <!-- footer-center -->\n");
		sbHTML.append("</div> <!-- mainImg-boundingbox -->\n");
		return sbHTML.toString();
	}

	private String dspNavigationPrevious(ArchiveInfo archive,
			ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		int index = paramInfo.getIndexOfImageFromList();
		Image imageBefore;
		imageBefore = paramInfo.getImageAtIndex(index - 1);

		if (index > 0) {
			sbHTML.append("		<a ");
			sbHTML.append(" href=\"");
			sbHTML.append(assembleHTTP.makeHREFNextPhoto(imageBefore.getFileWeb()));
			sbHTML.append("\">\n");
			sbHTML.append("			<img class=\"img_pointer_size\" src=\""
					+ Globals.HTTP_Refer
					+ "/support_files/PointersLeft.gif\" alt=\"");
			sbHTML.append("Previous Image: " + imageBefore.getTitle() + "\">\n");
			sbHTML.append("		</a>\n");
		} else {
			sbHTML.append("&nbsp;&nbsp\n");
		}

		return sbHTML.toString();
	}
	
	private String jsNavigationPrevious(ArchiveInfo archive,
			ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		int index = paramInfo.getIndexOfImageFromList();
		Image imageBefore;
		imageBefore = paramInfo.getImageAtIndex(index - 1);

		if (index > 0) {
			sbHTML.append(assembleHTTP.makeHREFNextPhoto(imageBefore.getFileWeb()));
		} 

		return sbHTML.toString();
	}

	private String dspNavigationNext(ArchiveInfo archive,
			ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		int index = paramInfo.getIndexOfImageFromList();
		Image imageAfter = new Image();
		imageAfter = paramInfo.getImageAtIndex(index + 1);

		if (index < paramInfo.getImageListSize() - 1) {

			sbHTML.append("		<a ");
			sbHTML.append(" href=\"");
			sbHTML.append(assembleHTTP.makeHREFNextPhoto(imageAfter.getFileWeb()));
			sbHTML.append("\">\n");
			sbHTML.append("			<img class=\"img_pointer_size\" src=\""
					+ Globals.HTTP_Refer
					+ "/support_files/PointersRight.gif\" alt=\"");
			sbHTML.append("Next Image: " + imageAfter.getTitle() + "\">\n");
			sbHTML.append("		</a>\n");
		} else {
			sbHTML.append("&nbsp;&nbsp\n");
		}

		return sbHTML.toString();
	}
	private String jsNavigationNext(ArchiveInfo archive,
			ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		int index = paramInfo.getIndexOfImageFromList();
		Image imageAfter = new Image();
		imageAfter = paramInfo.getImageAtIndex(index + 1);

		if (index < paramInfo.getImageListSize() - 1) {

			sbHTML.append(assembleHTTP.makeHREFNextPhoto(imageAfter.getFileWeb()));
		}

		return sbHTML.toString();
	}

	private String dspNavigationTop(ArchiveInfo archive) {
		StringBuffer sbHTML = new StringBuffer();
		sbHTML.append("		<a href=\"");


		sbHTML.append(this.assembleHTTP.makeHREFGalleryHome(archive
				.getXMLFilename()));
		sbHTML.append("\">\n");
		sbHTML.append("				<img class=\"img_pointer_size\" src=\""
				+ Globals.HTTP_Refer + "/support_files/PointersUp.gif\" alt=\"");
		sbHTML.append(Globals.USER_WEB_TITLE + "\">");
		sbHTML.append("</a>\n");

		return sbHTML.toString();
	}
	
	private String jsNavigationTop(ArchiveInfo archive) {
		StringBuffer sbHTML = new StringBuffer();
		sbHTML.append(this.assembleHTTP.makeHREFGalleryHome(archive
				.getXMLFilename()));

		return sbHTML.toString();
	}

	private String dspNavigationDownload(ArchiveInfo archive, Image image) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append(this.assembleHTTP.makeHREFOriginalsButton(image));
		// sbHTML.append("\n");

		float fFileSize = Integer.parseInt(image.getFileOriginalSize());
		fFileSize = fFileSize / (1024 * 1024);

		Logging.info(this.getClass().getName(), "MB File size: " + fFileSize);
		sbHTML.append("Download Original (");
		sbHTML.append(String.format("%.2f", fFileSize) + " MB)");
		sbHTML.append("</a>");
		return sbHTML.toString();
	}

	public String dspEXIFData(ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

//		int index = assembleHTTP.getIndexOfImageFromList(paramInfo);
//		Image image = new Image();
//		image = assembleHTTP.getImageAtIndex(paramInfo, index);

		Image image = paramInfo.getImageFromList();

		sbHTML.append("<table border=\"1\"  bgcolor=\""
				+ Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<tr>\n");
		sbHTML.append("	<td class=\"exifhtml_name\">Title</td>\n");
		sbHTML.append("	<td class=\"exifhtml_value\">" + image.getTitle()
				+ "</td>\n");
		sbHTML.append("</tr>\n");
		sbHTML.append("<tr>\n");
		sbHTML.append("	<td class=\"exifhtml_name\">Date</td>\n");
		sbHTML.append("	<td class=\"exifhtml_value\">" + image.getDate()
				+ "</td>\n");
		sbHTML.append("</tr>\n");
		sbHTML.append(this.exif_html.loadEXIFHTML(
				Globals.checkUserAgentHTML5(), archive.getXMLFilename(),
				image.getFileOriginal()));

		sbHTML.append("</table>\n");

		return sbHTML.toString();
	}

	public String dspOriginals(ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<center>\n");
		sbHTML.append("<img  src=\"" + Globals.HTTP_Refer + "/");
		sbHTML.append(archive.getDirOriginals() + "/"
				+ paramInfo.getImageFile() + "\" border=\"1\">\n");

		return sbHTML.toString();
	}

	public String dspDownload(ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<center>\n");
		sbHTML.append("<img  src=\"" + Globals.HTTP_Refer + "/");
		sbHTML.append(archive.getDirOriginals() + "/"
				+ paramInfo.getImageFile() + "\" border=\"1\">\n");

		return sbHTML.toString();
	}

	private String debugWindowsValues() {

		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("	<div class=\"sizes\">\n");
		sbHTML.append("		<style>\n");
		sbHTML.append("			table { border-collapse: collapse;\n");
		sbHTML.append("				border: thin solid white;\n");
		sbHTML.append("				font-family: arial;\n");
		sbHTML.append("				font-weight: normal;\n");
		sbHTML.append("				font-size: larger;\n");
		sbHTML.append("				color: grey;\n");
		sbHTML.append("			}\n");
		sbHTML.append("			td { padding: 4px; }\n");
		sbHTML.append("			th { padding: 4px; }\n");
		sbHTML.append("		</style>\n");

		sbHTML.append("		<table border=\"1\">\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>outerWidth:</th><td id=\"ow\"></td>\n");
		sbHTML.append("				<th>outerHeight:</th><td id=\"oh\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>innerWidth:</th><td id=\"iw\"></td>\n");
		sbHTML.append("				<th>innerHeight:</th><td id=\"ih\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>screen.width:</th><td id=\"sw\"></td>\n");
		sbHTML.append("				<th>screen.height:</th><td id=\"sh\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>box.width:</th><td id=\"boxw\"></td>\n");
		sbHTML.append("				<th>box.height:</th><td id=\"boxh\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>img.width:</th><td id=\"imgw\"></td>\n");
		sbHTML.append("				<th>img.height:</th><td id=\"imgh\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>img.horizontal:</th><td id=\"imghor\"></td>\n");
		sbHTML.append("				<th>window.horizontal:</th><td id=\"winhor\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>F.g.h(orig):</th><td id=\"fgho\"</td>\n");
		sbHTML.append("				<th>F.g.h(new):</th><td id=\"fghi\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("			<tr>\n");
		sbHTML.append("				<th>F.g.w(orig):</th><td id=\"fgwo\"</td>\n");
		sbHTML.append("				<th>F.g.w(new):</th><td id=\"fgwi\"></td>\n");
		sbHTML.append("			</tr>\n");
		sbHTML.append("		</table>\n");

		sbHTML.append("	</div> <!-- sizes -->\n");

		/*
		 * sbHTML.append("var myImage = document.getElementById(\"mainImage\");\n"
		 * ); sbHTML.append("var myImageWidth = myImage.naturalWidth;\n");
		 * sbHTML.append("var myImageHeight = myImage.naturalHeight;\n");
		 * 
		 * sbHTML.append(
		 * "var myImageIsHorizontal = myImageWidth >= myImageHeight;\n");
		 * sbHTML.append(
		 * "var myWindowIsHorizontal = window.outerWidth >= window.outerHeight;\n"
		 * );
		 * 
		 * sbHTML.append(
		 * "			document.getElementById(\"ow\").innerHTML = window.outerWidth;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"oh\").innerHTML = window.outerHeight;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"iw\").innerHTML = window.innerHeight;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"ih\").innerHTML = window.innerHeight;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"sw\").innerHTML = window.screen.width;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"sh\").innerHTML = window.screen.height;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"imgw\").innerHTML = myImageWidth;\n");
		 * sbHTML.append(
		 * "			document.getElementById(\"imgh\").innerHTML = myImageHeight;\n");
		 * sbHTML.append(
		 * "			document.getElementById(\"imghor\").innerHTML = myImageIsHorizontal;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"winhor\").innerHTML = myWindowIsHorizontal;\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"fgho\").innerHTML = $('FIGURE.fmainImg').css('height');\n"
		 * ); sbHTML.append(
		 * "			document.getElementById(\"fgwo\").innerHTML = $('FIGURE.fmainImg').css('width');\n"
		 * );
		 * 
		 * sbHTML.append("//if horiztonal photo and horizontal window\n");
		 * sbHTML.append("//	height determines size\n");
		 * sbHTML.append("//if vertical photo and horizontal window\n");
		 * sbHTML.append("//	height determines size\n");
		 * 
		 * sbHTML.append(
		 * "if ((myImageIsHorizontal && myWindowIsHorizontal) || (!myImageIsHorizontal && myWindowIsHorizontal)) {\n"
		 * ); sbHTML.append(
		 * "$('FIGURE.fmainImg').css('height', ((window.innerHeight - 10)+'px'));\n"
		 * ); sbHTML.append(
		 * "$('FIGURE.fmainImg').css('max-height', ((window.innerHeight)+'px'));\n"
		 * ); sbHTML.append("$('FIGURE.fmainImg').css('width', '');\n");
		 * sbHTML.append("$('FIGURE.fmainImg').css('max-width', '');\n");
		 * sbHTML.append(
		 * "document.getElementById('mainImage').setAttribute('height', (window.innerHeight - 10));\n"
		 * ); sbHTML.append(
		 * "document.getElementById('fghi').innerHTML = $('FIGURE.fmainImg').css('height');\n"
		 * ); sbHTML.append("}\n");
		 * 
		 * sbHTML.append("//if horizontal photo and vertical window\n");
		 * sbHTML.append("//	width determines size\n");
		 * sbHTML.append("//if vertical photo and vertical window\n");
		 * sbHTML.append("//	width determines size\n");
		 * 
		 * sbHTML.append(
		 * "if ((!myImageIsHorizontal && !myWindowIsHorizontal) || (myImageIsHorizontal && !myWindowIsHorizontal)) {\n"
		 * ); sbHTML.append("$('FIGURE.fmainImg').css('height', '');\n");
		 * sbHTML.append("$('FIGURE.fmainImg').css('max-height', '');\n");
		 * sbHTML.append(
		 * "$('FIGURE.fmainImg').css('width', ((window.innerWidth - 10)+'px'));\n"
		 * ); sbHTML.append(
		 * "$('FIGURE.fmainImg').css('max-width', ((window.innerWidth)+'px'));\n"
		 * ); sbHTML.append(
		 * "document.getElementById('mainImage').setAttribute('width', (window.innerWidth - 10));\n"
		 * ); sbHTML.append(
		 * "document.getElementById('fgwi').innerHTML = $('FIGURE.fmainImg').css('width');\n"
		 * ); sbHTML.append("}\n"); sbHTML.append("}\n");
		 * 
		 * sbHTML.append("		</script>\n"); sbHTML.append("	</div>\n");
		 */
		return sbHTML.toString();

	}

}
