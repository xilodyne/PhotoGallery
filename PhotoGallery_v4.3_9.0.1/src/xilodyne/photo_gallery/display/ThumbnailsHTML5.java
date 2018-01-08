package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterPhoto;

public class ThumbnailsHTML5 extends Thumbnails implements ThumbnailsInterface {
	// based upon the xml file
	// show all thumbnails

	AssembleHTTP assembleHTTP = new AssembleHTTP();

	public String dspThumbnails(ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		int iPhotoCount = paramInfo.getImageListSize();

		// for center, show up arrow to go back to gallery

		// sbHTML.append("<figure>\n");
		sbHTML.append("<div id='image_prev'>\n");
		sbHTML.append("	" + this.assembleHTTP.makeHREFArchiveHome());
		sbHTML.append("\n");

		sbHTML.append("		<img class=\"img_pointer_size\" src=\""
				+ Globals.HTTP_Refer + "/support_files/PointersUp.gif\" alt=\"");
		sbHTML.append(Globals.USER_WEB_TITLE + "\">\n");
		sbHTML.append("	</a>\n");
		sbHTML.append("</div>\n\n");

		sbHTML.append("<div id='image_next'>\n");
		sbHTML.append("	" + this.assembleHTTP.makeHREFArchiveHome());
		sbHTML.append("\n");
		sbHTML.append("		<img class=\"img_pointer_size\" src=\""
				+ Globals.HTTP_Refer + "/support_files/PointersUp.gif\" alt=\"");
		sbHTML.append(Globals.USER_WEB_TITLE + "\">\n");
		sbHTML.append("	</a>\n");
		sbHTML.append("</div>\n\n");

		sbHTML.append("<div class='thumbnail-title'>\n");
		sbHTML.append("	<p class='vlarge top'>");

		// sbHTML.append("<figcaption>" + archive.getTitle() +
		// "</figcaption>\n");
		sbHTML.append(archive.getTitle());
		sbHTML.append("	</p>\n");
		sbHTML.append("</div> <!-- thumbnail-title -->\n\n");

		// sbHTML.append("</figure>");

		sbHTML.append("<div class='thumbnail-box'>\n\n");

		for (int count = 0; count < iPhotoCount; count++) {
			try {

				Image tempImage = this.getNextImage(paramInfo);
				sbHTML.append(this.formatThumbs(archive, tempImage));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			
		}
		sbHTML.append("</div> <!-- thumbnail-box -->\n");

		return sbHTML.toString();
	}

	private String formatThumbs(ArchiveInfo archive, Image image) {
		StringBuffer sbHold = new StringBuffer();
		sbHold.append("	<figure class=\"fthumb\">\n");
		sbHold.append("		<a ");
		sbHold.append(" href=\"");
		sbHold.append(this.assembleHTTP.makeHREFNextPhoto(image.getFileWeb()));
		sbHold.append("\">\n");
		sbHold.append("			" + this.assembleHTTP.makeIMGThumbnail5(archive, image) +"\n");
		sbHold.append("			<figcaption>\n");

		sbHold.append("				<p class='large'>" + image.getTitle() + "</p>\n");
		sbHold.append("				<p class='normal'>" + image.getDateYYYYMMDD() + "</p>\n");
		sbHold.append("			</figcaption>\n");
		sbHold.append("		</a>\n");
		sbHold.append("	</figure>\n");

		return sbHold.toString();
	}

}
