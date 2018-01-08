package xilodyne.photo_gallery.util;

import xilodyne.photo_gallery.util.marquee.GetRandomMarqueePhoto;
import xilodyne.photo_gallery.util.metadata.PhotoMetadataPropertyXML;

import java.util.StringTokenizer;

/**
 * @author Austin Davis Holiday (aholiday@xilodyne.com)
 * @version 1.2_9.0.1
 *
 * metadata extraction use metadata-extractor library.
 */
public class ExtractTitle {

	// lets do the following:
	// title format: <date>-<title>-<count>.jpg
	// <date> = yyyymmdd, yyyymm, yyyy
	// <title> = "_" word Separators; UpperCase,lowerCase
	// <count> always a number

	// for marquee, get random file name from total dir count

	/*
	 * $JAVA -jar $EXTRACT_TITLE meta $HIGHRES_PHOTO > $METADATA/$FOLDER/$NAME.$EXT.meta.xml TITLE=`
	 * $JAVA -jar $EXTRACT_TITLE title $NAME` DESC=`$JAVA -jar $EXTRACT_TITLE date $NAME`
	 * 
	 * run configuration in eclipse, arguments:
	 *  meta 20060723-Matterhorn-6.jpg
	 *  marquee C:\Backup\JavaCode\Java8-Luna\Projects\Web\PhotoGalleryProjects\PhotoGallery-v2.1\WebContent\galleries\thumbnails\Matterhorn-2006.07.23 C:\Backup\JavaCode\Java8-Luna\Projects\Web\PhotoGalleryProjects\PhotoGallery-v2.1\WebContent\galleries\marquee Matterhorn-2006.07.23.xml
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// if ((args.length <= 1 ) || (args.length > 2)){
		if (args.length <= 1) {
			displayUsage();

		} else {

			String sFunction = args[0];
			String sFileName = args[1];
			
			if (sFunction.compareToIgnoreCase("title") == 0) {
				System.out.println(getTitle(sFileName));
			} else if (sFunction.compareTo("date") == 0) {
				System.out.println(getDate(sFileName));
			} else if (sFunction.compareTo("meta") == 0) {
				System.out.println(getMeta(sFileName));
			} else if (sFunction.compareTo("marquee") == 0) {
				System.out.println(copyMarquee(sFileName));
			} else if (sFunction.compareTo("toLowercase") == 0) {
				System.out.println(sFileName.toLowerCase());
			} else {
				displayUsage();
			}
		}

	}

	public static String getTitle(String sTempFileName) {
		String sTitle = "";

		StringTokenizer st = new StringTokenizer(sTempFileName, "-");
		if (st.countTokens() > 1) { // we know a date and title
			st.nextToken();
			StringBuffer sbTitle = new StringBuffer(st.nextToken());

			if (sbTitle.length() > 1) { // we have something
				//
				StringBuffer sbAddSpaces = new StringBuffer();
				sbAddSpaces.append(sbTitle.charAt(0));

				// add a space before each capital letter
				for (int i = 1; i < sbTitle.length(); i++) {
					if (sbTitle.charAt(i) >= 'A' && sbTitle.charAt(i) <= 'Z') {
						sbAddSpaces.append(" ");
					}
					sbAddSpaces.append(sbTitle.charAt(i));
				}

				// replace "_" with space
				sTitle = sbAddSpaces.toString();
				sTitle = sTitle.replace('_', ' ');
			}
			// get rid of duplicate spaces
			StringTokenizer stSpace = new StringTokenizer(sTitle);
			sTitle = "";
			while (stSpace.hasMoreTokens()) {
				sTitle = sTitle + " " + stSpace.nextToken();
			}
			sTitle = sTitle.trim();
		}
		if (st.hasMoreTokens()) {
			// get rid of the extension
			String sTemp = st.nextToken();
			StringTokenizer stExt = new StringTokenizer(sTemp, ".");
			if (stExt.countTokens() > 1) {
				sTemp = stExt.nextToken();
			}
			sTitle = sTitle + " - #" + sTemp;
		}

		return sTitle;

	}

	public static String getDate(String sTempFileName) {
		String sDate = "";

		StringTokenizer st = new StringTokenizer(sTempFileName, "-");
		if (st.countTokens() > 1) { // we know at least the date
			StringBuffer sbDate = new StringBuffer(st.nextToken());

			// if 8, then yyyymmdd format
			if (sbDate.length() == 8) {
				sDate = sbDate.substring(4, 6) + "/" + sbDate.substring(6, 8)
						+ "/" + sbDate.substring(0, 4);
			} else if (sbDate.length() == 6) { // then yyyymm
				sDate = sbDate.substring(4, 6) + "/" + sbDate.substring(0, 4);
			} else if (sbDate.length() == 4) {
				sDate = sbDate.substring(0, 4);
			}
		}
		return sDate;

	}

	// open the file, get the exip format
	// return data as xml format

	public static String getMeta(String sTempFileName) {
		PhotoMetadataPropertyXML metadata = new PhotoMetadataPropertyXML();
		return metadata.getXMLMetadata(sTempFileName);
	}

	public static String copyMarquee(String sourceDirPath) {
		GetRandomMarqueePhoto marquee = new GetRandomMarqueePhoto(sourceDirPath);
		return marquee.copyMarqueePhoto();
	}

	public static void displayUsage() {
		System.out.println();
		System.out.println("Usage:\tjava xilodyne.photo_gallery.util.ExtractTitle title filename");
		System.out.println("\tjava xilodyne.photo_gallery.util.ExtractTitle date filename");
		System.out.println("\tjava xilodyne.photo_gallery.util.ExtractTitle meta filename");
		System.out.println("\tjava xilodyne.photo_gallery.util.ExtractTitle marquee sourceDirPath");
		System.out.println("\tjava xilodyne.photo_gallery.util.ExtractTitle toLowercase filename");
	}
}
