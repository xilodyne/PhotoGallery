package xilodyne.photo_gallery.display;

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
public class ThumbnailsHTML4 extends Thumbnails implements ThumbnailsInterface {
	// based upon the xml file
	// show all thumbnails

	AssembleHTTP assembleHTTP = new AssembleHTTP();



	public String dspThumbnails(ArchiveInfo archive, ParameterPhoto paramInfo) {
		StringBuffer sbHTML = new StringBuffer();

		int iColumns = archive.getThumbnailsPerRow();
		int iPhotoCount = paramInfo.getImageListSize();

		int iCount = 0;

		sbHTML.append("<center>\n");
		sbHTML.append("  <table border=\"0\" width=\"80%\"  cellpadding=\"2\">\n");
		//for center, show up arrow to go back to gallery
		sbHTML.append("  <tr align=\"center\">\n");
		sbHTML.append("    <td align=\"center\" colspan=\"" + iColumns + "\">\n");
		sbHTML.append("      ");
		sbHTML.append(this.assembleHTTP.makeHREFArchiveHome() ) ;
		sbHTML.append("\n");
		sbHTML.append("      <img src=\"" + Globals.HTTP_Refer + "/support_files/PointersUp.gif\" height=\"20\" width=\"30\" alt=\""); 
		sbHTML.append(Globals.USER_WEB_TITLE + "\" border=\"0\">");
		sbHTML.append("</a>\n");
		sbHTML.append("    </td>\n");
		sbHTML.append("  </tr>\n");


		sbHTML.append("  <tr>\n");
		sbHTML.append("    <td align=\"center\" colspan=\"" + iColumns + "\">\n");
		sbHTML.append("      <bold><font size=\"+3\" color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"0\" face=\"arial\">"
				+ archive.getTitle() + "</font></bold>\n");
		sbHTML.append("    </td>\n");
		sbHTML.append("  </tr>\n");
		// get total number of photos
		// columns divided by photos = how many rows
		int iRows = (iPhotoCount / iColumns);

		if (Globals.USER_DEBUG) {
			Logging.info(this.getClass().getName(), "Thumbnails: rows: "+iRows+": columns:"+iColumns+" photoCount:" + iPhotoCount);
			
			sbHTML.append("<tr><td>iRows: " + iRows + "</td></tr>");
			sbHTML.append("<tr><td>iColumns: " + iColumns + "</td></tr>");
			sbHTML.append("<tr><td>iPhotoCount: " + iPhotoCount + "</td></tr>\n");
		}

		for (int row = 0; row < iRows; row++) {
			sbHTML.append("  <tr align=\"center\">\n");

			for (int col = 0; col < iColumns; col++) {
				try {

					Image tempImage = this.getNextImage(paramInfo);
					sbHTML.append(this.formatThumbs(archive, tempImage));
				} catch (NullPointerException e) {
				}
				iCount++;
				sbHTML.append("    </td>\n");
			}
			sbHTML.append("  </tr>\n");
		}

		int iRemainingPhotos = iPhotoCount - iCount;

		if (iRemainingPhotos > 0) {
			if (Globals.USER_DEBUG) {
				sbHTML.append("<tr><td> Remaining Photos: " + iRemainingPhotos + "</td></tr>\n");
			}
			sbHTML.append("  <tr align=\"center\">\n");
			for (int iRemaining = 0; iRemaining < iRemainingPhotos; iRemaining++) {
				Image tempImage = this.getNextImage(paramInfo);
				sbHTML.append(this.formatThumbs(archive, tempImage));
			}

			// fill in the remaining table
			for (int iFill = iRemainingPhotos; iFill < iColumns; iFill++) {
				sbHTML.append("    <td> &nbsp;</td>\n");
			}
			sbHTML.append("  </tr>\n");
		}
		
		//for center, show up arrow to go back to gallery
		//show down arrow to see high res image
		sbHTML.append("  <tr align=\"center\">\n");
		sbHTML.append("    <td align=\"center\" colspan=\"" + iColumns + "\">\n");
		sbHTML.append("      ");
		sbHTML.append(this.assembleHTTP.makeHREFArchiveHome() ) ;
		sbHTML.append("\n");
		sbHTML.append("      <img src=\"" + Globals.HTTP_Refer + "/support_files/PointersUp.gif\" height=\"20\" width=\"30\" alt=\""); 
		sbHTML.append(Globals.USER_WEB_TITLE + "\" border=\"0\">");
		sbHTML.append("</a>\n");
		sbHTML.append("    </td>\n");
		sbHTML.append("  </tr>\n");

		sbHTML.append("  </table>\n");
		
		/*
		 * 		sbHTML.append("    <table border=\"0\" cellpadding=\"1\">\n");	
		sbHTML.append("    <tr align=\"center\">\n");
		sbHTML.append("      <td align=\"center\" height=\"10%\" >\n");
		sbHTML.append("        ");
		sbHTML.append( this.assembleHTTP.makeHREFArchiveHome());
		sbHTML.append("\n        <font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"0\" face=\"arial\">");
		sbHTML.append( Globals.USER_WEB_TITLE );
		sbHTML.append("</font>");

		 */

		return sbHTML.toString();
	}

	
	

	
	
	private String formatThumbs(ArchiveInfo archive, Image image) {
		StringBuffer sbHold = new StringBuffer();

		if (Globals.USER_DEBUG) {
			sbHold.append("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			sbHold.append("<tr><td colspan=\"2\"><h2>Thumbnails: Archive & Info</h2></td></tr>");

			sbHold.append("<tr><th align=\"right\">Archive: dir web:</th>");
			sbHold.append("<td>" + archive.getDirWeb() + "</td>");
			sbHold.append("</tr>");

			sbHold.append("</table>");
		}

		sbHold.append("    <td>");
		sbHold.append("		<a ");
		sbHold.append(" href=\"");
		sbHold.append(this.assembleHTTP.makeHREFNextPhoto(image.getFileWeb()));
		sbHold.append("\">\n");
		sbHold.append(this.assembleHTTP.makeIMGThumbnail(archive, image));
		sbHold.append(this.assembleHTTP.makeIMGThumbnailTitle(image));

		sbHold.append("</a>\n");
		return sbHold.toString();
	}

	
	

}
