package xilodyne.photo_gallery.http;

import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;

/**
 * @author aholiday
 *
 */
public class AssembleHTTP {
	
/*
	public String dspPhotoLeftNavigation(ArchiveInfo archive, Image image) {
		StringBuffer sbHold = new StringBuffer();

		sbHold.append("<td width=\"" + archive.getThumbnailColumnWidth() + "\" align=\"center\" valign=\"top\">");

		sbHold.append(" <a href=\"" + Globals.HTTP_Refer + "/");
		sbHold.append(Globals.SERVLET_PHOTO + "/" + Globals.PARAM_THUMB + "=" + archive.getXMLFilename() + "\">");
		sbHold.append("</a>");
		sbHold.append("</td>");
		return sbHold.toString();
	}
*/
	
	public String makeMarqueeThumbnailHTML4(String sXMLFile) {
		StringBuffer sbImg = new StringBuffer();

		sbImg.append("<img ");
		sbImg.append("height=\"100\" src=\"" + Globals.HTTP_Refer + "/" + Globals.HTTP_ROOT_MARQUEE_DIR + "/");
		sbImg.append("marquee_" + sXMLFile + ".jpg" + "\" border=\"1\" >");
		return sbImg.toString();
	}

	public String makeMarqueeThumbnailHTML5(String sXMLFile) {
		StringBuffer sbImg = new StringBuffer();

		sbImg.append("<img class=\"img_gallery\" src=\"" + Globals.HTTP_Refer + "/" + Globals.HTTP_ROOT_MARQUEE_DIR + "/");
		sbImg.append("marquee_" + sXMLFile + ".jpg\">\n");
		return sbImg.toString();
	}
	
	public String makeIMGThumbnail(ArchiveInfo archive, Image image) {
		StringBuffer sbImg = new StringBuffer();

		sbImg.append("<img ");
		sbImg.append("height=\"100\" src=\"" + Globals.HTTP_Refer + "/" + archive.getDirThumbnail() + "/");
		sbImg.append(image.getFileThumbnail() + "\" border=\"1\" >");
		return sbImg.toString();
	}

	public String makeIMGThumbnail5(ArchiveInfo archive, Image image) {
		StringBuffer sbImg = new StringBuffer();

		sbImg.append("<img class=\"img_thumb\" src=\"" + Globals.HTTP_Refer + "/" + archive.getDirThumbnail() + "/");
		sbImg.append(image.getFileThumbnail() + "\">");
		return sbImg.toString();
	}

	public String makeIMGThumbnailTitle(Image image) {
		StringBuffer sbImgTitle = new StringBuffer();

		sbImgTitle.append("<br><font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"+1\" face=\"arial\">"
				+ image.getTitle() + "</font><br><br>");
		return sbImgTitle.toString();
	}

/*	public String makeGalleryHomeTitle(ArchiveInfo archive) {
		StringBuffer sbTitle = new StringBuffer();
		sbTitle.append("Gallery:  ");
		sbTitle.append(archive.getTitle());

		return sbTitle.toString();
	}
*/
	public String makeHREFGalleryHome(String sXMLFile) {
		StringBuffer sbGalleryHome = new StringBuffer();

	//	sbGalleryHome.append("<a href=\"");
		sbGalleryHome.append(Globals.HTTP_Refer);
		sbGalleryHome.append("/");
		sbGalleryHome.append(Globals.SERVLET_PHOTO);
		sbGalleryHome.append("?");
		sbGalleryHome.append(Globals.PARAM_THUMB);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sXMLFile);
	//	sbGalleryHome.append("\">\n");

		return sbGalleryHome.toString();
	}

	public String makeHTTPNewSessionToLogin(String sQueryString) {
		StringBuffer sbHold = new StringBuffer();

		sbHold.append(Globals.HTTP_Refer);
		sbHold.append("/");
		sbHold.append(Globals.SERVLET_LOGIN);

		if (sQueryString == null) {
			sbHold.append("?");
		} else {
			sbHold.append("?");
			sbHold.append(sQueryString);
			sbHold.append("&");
		}
		sbHold.append(Globals.PARAM_NEW_SESSION);
		sbHold.append("=");
		sbHold.append("true");

		return sbHold.toString();
	}	
	
	public String makeHTTPNewSessionToGallery(String sQueryString) {
		StringBuffer sbHold = new StringBuffer();

		sbHold.append(Globals.HTTP_Refer);
		sbHold.append("/");
		sbHold.append(Globals.SERVLET_PHOTO);

		if (sQueryString == null) {
			sbHold.append("?");
		} else {
			sbHold.append("?");
			sbHold.append(sQueryString);
			sbHold.append("&");
		}
		sbHold.append(Globals.PARAM_NEW_SESSION);
		sbHold.append("=");
		sbHold.append("true");

		return sbHold.toString();
	}

	public String makeHTTPGalleryHome(String sXMLFile) {
		StringBuffer sbGalleryHome = new StringBuffer();

		sbGalleryHome.append(Globals.HTTP_Refer);
		sbGalleryHome.append("/");
		sbGalleryHome.append(Globals.SERVLET_PHOTO);
		sbGalleryHome.append("?");
		sbGalleryHome.append(Globals.PARAM_THUMB);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sXMLFile);

		if (Globals.USER_DEBUG) {
			sbGalleryHome.append("&");
			sbGalleryHome.append(Globals.PARAM_DEBUG);
			sbGalleryHome.append("=");
			sbGalleryHome.append("true");
		}

		return sbGalleryHome.toString();
	}




	public String makeHTTPArchivesHome() {
		StringBuffer sbGalleryHome = new StringBuffer();

		sbGalleryHome.append(Globals.HTTP_Refer);
		sbGalleryHome.append("/");
		sbGalleryHome.append(Globals.SERVLET_PHOTO);
		sbGalleryHome.append("?");
		sbGalleryHome.append(Globals.PARAM_SHOW_ALL);
		sbGalleryHome.append("=");
		sbGalleryHome.append("all");

		if (Globals.USER_DEBUG) {
			sbGalleryHome.append("&");
			sbGalleryHome.append(Globals.PARAM_DEBUG);
			sbGalleryHome.append("=");
			sbGalleryHome.append("true");
		}

		return sbGalleryHome.toString();
	}
/*
	public String f(String sXMLFile, String sImage) {
		StringBuffer sbGalleryHome = new StringBuffer();

		sbGalleryHome.append(Globals.HTTP_Refer);
		sbGalleryHome.append("/");
		sbGalleryHome.append(Globals.SERVLET_PHOTO);
		sbGalleryHome.append("?");
		sbGalleryHome.append(Globals.PARAM_XML);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sXMLFile);
		sbGalleryHome.append("&");
		sbGalleryHome.append(Globals.PARAM_IMAGE);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sImage);




		if (Globals.USER_DEBUG) {
			sbGalleryHome.append("&");
			sbGalleryHome.append(Globals.PARAM_DEBUG);
			sbGalleryHome.append("=");
			sbGalleryHome.append("true");
		}

		return sbGalleryHome.toString();
	}
*/
	

	public String makeHTTPSLogin(String sXMLFile) {
		StringBuffer sbLogin = new StringBuffer();
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/");
		sbLogin.append(Globals.SERVLET_LOGIN);
		sbLogin.append("?");
		sbLogin.append(Globals.PARAM_LOGIN);
		sbLogin.append("=");
		sbLogin.append(sXMLFile);

		if (Globals.USER_DEBUG) {
			sbLogin.append("&");
			sbLogin.append(Globals.PARAM_DEBUG);
			sbLogin.append("=");
			sbLogin.append("true");
		}

		return sbLogin.toString();
	}

	public String makeHTTPSLogin() {
		StringBuffer sbLogin = new StringBuffer();

		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/");
		sbLogin.append(Globals.SERVLET_LOGIN);
		sbLogin.append("?");
		sbLogin.append(Globals.PARAM_SHOW_ALL);
		sbLogin.append("=");
		sbLogin.append("valid");

		if (Globals.USER_DEBUG) {
			sbLogin.append("&");
			sbLogin.append(Globals.PARAM_DEBUG);
			sbLogin.append("=");
			sbLogin.append("true");
		}

		return sbLogin.toString();
	}

	public String makeHREFArchiveHome() {
		StringBuffer sbArchive = new StringBuffer();
		sbArchive.append("<a href=\"");
		sbArchive.append(Globals.HTTP_Refer);
		sbArchive.append("/");
		sbArchive.append(Globals.SERVLET_PHOTO);
		sbArchive.append("?");
		sbArchive.append(Globals.PARAM_SHOW_ALL);
		sbArchive.append("=");
		sbArchive.append("all");
		sbArchive.append("\">");

		return sbArchive.toString();
	}



	

	public String makeHREFNextPhoto(String sImage) {
		StringBuffer sbHREF = new StringBuffer();

		if (Globals.USER_DEBUG) {
			sbHREF.append("ref HREF: a href=\"");
			sbHREF.append(Globals.HTTP_Refer);
			sbHREF.append("/");
			sbHREF.append(Globals.SERVLET_PHOTO);
			sbHREF.append("?");
			sbHREF.append(Globals.PARAM_IMAGE);
			sbHREF.append("=");
			sbHREF.append(sImage);
			sbHREF.append("\">");
		}

		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");

		sbHREF.append(Globals.PARAM_IMAGE);
		sbHREF.append("=");
		sbHREF.append(sImage);
		return sbHREF.toString();
	}
	
	public String makeHREFNextPhotoUsingXML(String sXMLFile, String sImage) {
		StringBuffer sbHREF = new StringBuffer();

		if (Globals.USER_DEBUG) {
			sbHREF.append("ref HREF: a href=\"");
			sbHREF.append(Globals.HTTP_Refer);
			sbHREF.append("/");
			sbHREF.append(Globals.SERVLET_PHOTO);
			sbHREF.append("?");
			sbHREF.append(Globals.PARAM_XML);
			sbHREF.append("=");
			// sbHREF.append( archive.getXMLFilename() );
			sbHREF.append(sXMLFile);
			sbHREF.append("&");
			sbHREF.append(Globals.PARAM_IMAGE);
			sbHREF.append("=");
			// sbHREF.append( image.getFileWeb());
			sbHREF.append(sImage);
			sbHREF.append("\">");
		}

//		sbHREF.append("<a ");
//		sbHREF.append(" href=\"");
		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");

		sbHREF.append(Globals.PARAM_XML);
		sbHREF.append("=");
		sbHREF.append(sXMLFile);
		sbHREF.append("&");
		sbHREF.append(Globals.PARAM_IMAGE);
		sbHREF.append("=");
		sbHREF.append(sImage);
//		sbHREF.append("\">");

		return sbHREF.toString();
	}
	
	

	
	
	
	public String makeHREFOriginalsButton(Image image) {
		StringBuffer sbHREF = new StringBuffer();

		if (Globals.USER_DEBUG) {
			sbHREF.append("ref HREF: a href=\"");
			sbHREF.append(Globals.HTTP_Refer);
			sbHREF.append("/");
			sbHREF.append(Globals.SERVLET_PHOTO);
			sbHREF.append("?");
			sbHREF.append(Globals.PARAM_ORIGINALS_DOWNLOAD);
			sbHREF.append("=");
			sbHREF.append(image.getFileOriginal());
			sbHREF.append("&");
			sbHREF.append(Globals.PARAM_ORIG_FILESIZE);
			sbHREF.append("=");
			sbHREF.append(image.getFileOriginalSize());
			sbHREF.append("\">");
		} 

		sbHREF.append("<a class=\"button\" ");
		sbHREF.append("href=\"");
		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");
		sbHREF.append(Globals.PARAM_ORIGINALS_DOWNLOAD);
		sbHREF.append("=");
		sbHREF.append(image.getFileOriginal());
		sbHREF.append("&");
		sbHREF.append(Globals.PARAM_ORIG_FILESIZE);
		sbHREF.append("=");
		sbHREF.append(image.getFileOriginalSize());
		sbHREF.append("\">");

		return sbHREF.toString();
	}
	
	public String makeHREFOriginalsButtonUsingXML(ArchiveInfo archive, Image image) {
		StringBuffer sbHREF = new StringBuffer();

		if (Globals.USER_DEBUG) {
			sbHREF.append("ref HREF: a href=\"");
			sbHREF.append(Globals.HTTP_Refer);
			sbHREF.append("/");
			sbHREF.append(Globals.SERVLET_PHOTO);
			sbHREF.append("?");
			sbHREF.append(Globals.PARAM_ORIGINALS_DOWNLOAD);
			sbHREF.append("=");
			sbHREF.append(image.getFileOriginal());
			sbHREF.append("&");
			sbHREF.append(Globals.PARAM_XML);
			sbHREF.append("=");
			sbHREF.append(archive.getXMLFilename());		
			sbHREF.append("&");
			sbHREF.append(Globals.PARAM_ORIG_FILESIZE);
			sbHREF.append("=");
			sbHREF.append(image.getFileOriginalSize());
			sbHREF.append("\">");
		} 

		sbHREF.append("<a class=\"button\" ");
		sbHREF.append("href=\"");
		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");
		sbHREF.append(Globals.PARAM_ORIGINALS_DOWNLOAD);
		sbHREF.append("=");
		sbHREF.append(image.getFileOriginal());
		sbHREF.append("&");
		sbHREF.append(Globals.PARAM_XML);
		sbHREF.append("=");
		sbHREF.append(archive.getXMLFilename());		
		sbHREF.append("&");
		sbHREF.append(Globals.PARAM_ORIG_FILESIZE);
		sbHREF.append("=");
		sbHREF.append(image.getFileOriginalSize());
		sbHREF.append("\">");

		return sbHREF.toString();
	}

	public String makeHREF_EXIFButton( String sImage) {
		
		StringBuffer sbHREF = new StringBuffer();


		sbHREF.append("<a ");
		sbHREF.append(" href=\"");
		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");
		sbHREF.append(Globals.PARAM_EXIF);
		sbHREF.append("=");
		sbHREF.append(sImage);
		sbHREF.append("\">");
//		sbHREF.append("\" target=\"_blank\">");
		
		return sbHREF.toString();
	}
	
	public String makeHREF_EXIFButtonUsingXML(String sXMLFile, String sImage) {
		
		StringBuffer sbHREF = new StringBuffer();


		sbHREF.append("<a ");
		sbHREF.append(" href=\"");
		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");
		sbHREF.append(Globals.PARAM_EXIF);
		sbHREF.append("=");
		sbHREF.append(sImage);
		sbHREF.append("&");
		sbHREF.append(Globals.PARAM_XML);
		sbHREF.append("=");
		sbHREF.append(sXMLFile);
		sbHREF.append("\">");
//		sbHREF.append("\" target=\"_blank\">");
		
		return sbHREF.toString();
	}

	public String makeHREFOriginalsUsingXML(ArchiveInfo archive, Image image) {
		StringBuffer sbHREF = new StringBuffer();

		if (Globals.USER_DEBUG) {
			sbHREF.append("ref HREF: a href=\"");
			sbHREF.append(Globals.HTTP_Refer);
			sbHREF.append("/");
			sbHREF.append(Globals.SERVLET_PHOTO);
			sbHREF.append("?");
			sbHREF.append(Globals.PARAM_ORIGINALS);
			sbHREF.append("=");
			sbHREF.append(image.getFileOriginal());
			sbHREF.append("&");
			sbHREF.append(Globals.PARAM_XML);
			sbHREF.append("=");
			sbHREF.append(archive.getXMLFilename());
			sbHREF.append("\">");
		}

		sbHREF.append("<a href=\"");
		sbHREF.append(Globals.HTTP_Refer);
		sbHREF.append("/");
		sbHREF.append(Globals.SERVLET_PHOTO);
		sbHREF.append("?");
		sbHREF.append(Globals.PARAM_ORIGINALS);
		sbHREF.append("=");
		sbHREF.append(image.getFileOriginal());
		sbHREF.append("&");
		sbHREF.append(Globals.PARAM_XML);
		sbHREF.append("=");
		sbHREF.append(archive.getXMLFilename());
		sbHREF.append("\">");

		return sbHREF.toString();
	}

	public String makeHTTPImageHome(String sImage) {
		StringBuffer sbGalleryHome = new StringBuffer();

		sbGalleryHome.append(Globals.HTTP_Refer);
		sbGalleryHome.append("/");
		sbGalleryHome.append(Globals.SERVLET_PHOTO);
		sbGalleryHome.append("?");
		sbGalleryHome.append(Globals.PARAM_IMAGE);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sImage);


		if (Globals.USER_DEBUG) {
			sbGalleryHome.append("&");
			sbGalleryHome.append(Globals.PARAM_DEBUG);
			sbGalleryHome.append("=");
			sbGalleryHome.append("true");
		}

		return sbGalleryHome.toString();
	}

	public String makeHTTPImageHomeUsingXML(String sXMLFile, String sImage) {
		StringBuffer sbGalleryHome = new StringBuffer();

		sbGalleryHome.append(Globals.HTTP_Refer);
		sbGalleryHome.append("/");
		sbGalleryHome.append(Globals.SERVLET_PHOTO);
		sbGalleryHome.append("?");
		sbGalleryHome.append(Globals.PARAM_XML);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sXMLFile);
		sbGalleryHome.append("&");
		sbGalleryHome.append(Globals.PARAM_IMAGE);
		sbGalleryHome.append("=");
		sbGalleryHome.append(sImage);


		if (Globals.USER_DEBUG) {
			sbGalleryHome.append("&");
			sbGalleryHome.append(Globals.PARAM_DEBUG);
			sbGalleryHome.append("=");
			sbGalleryHome.append("true");
		}

		return sbGalleryHome.toString();
	}



	public String makeHTTPNewSessionToAdminLogin(String sQueryString) {
		StringBuffer sbHold = new StringBuffer();

		sbHold.append(Globals.HTTP_Refer);
		sbHold.append("/");
		sbHold.append(Globals.SERVLET_LOGIN);

		if (sQueryString == null) {
			sbHold.append("?");
		} else {
			sbHold.append("?");
			sbHold.append(sQueryString);
			sbHold.append("&");
		}
		sbHold.append(ParameterAdmin.PARAM_ADMIN_LOGIN);
		sbHold.append("=");
		sbHold.append("true");

		return sbHold.toString();
	}
	public String makeHTTPAdminHomeFromLogin() {
		StringBuffer sbAdminHome = new StringBuffer();

		sbAdminHome.append(Globals.HTTP_Refer);
		sbAdminHome.append("/");
		sbAdminHome.append(Globals.SERVLET_ADMIN);
		sbAdminHome.append("?");
		sbAdminHome.append(ParameterAdmin.PARAM_ADMIN_MENU);
		sbAdminHome.append("=");
		sbAdminHome.append("true");

		return sbAdminHome.toString();
	}

	private String makeAdminHREFDSPConfSettings() {
		StringBuffer sbAdminConf = new StringBuffer();

		sbAdminConf.append("<a href=\"");
		sbAdminConf.append(Globals.HTTP_Refer);
		sbAdminConf.append("/");
		sbAdminConf.append(Globals.SERVLET_ADMIN);
		sbAdminConf.append("?");
		sbAdminConf.append(ParameterAdmin.PARAM_CONFIG_GOTO_DSPUSERSETTINGS);
		sbAdminConf.append("=");
		sbAdminConf.append(true);

		sbAdminConf.append("\">");

		return sbAdminConf.toString();
	}

	private String makeAdminHREFDSPRunJobs() {
		StringBuffer sbRunJobs = new StringBuffer();

		sbRunJobs.append("<a href=\"");
		sbRunJobs.append(Globals.HTTP_Refer);
		sbRunJobs.append("/");
		sbRunJobs.append(Globals.SERVLET_ADMIN);
		sbRunJobs.append("?");
		sbRunJobs.append(ParameterAdmin.PARAM_JOBS_GOTO_DSPRUNJOBS);
		sbRunJobs.append("=");
		sbRunJobs.append(true);

		sbRunJobs.append("\">");

		return sbRunJobs.toString();
	}

	private String makeAdminHREFDSPMonJobs() {
		StringBuffer sbRunJobs = new StringBuffer();

		sbRunJobs.append("<a href=\"");
		sbRunJobs.append(Globals.HTTP_Refer);
		sbRunJobs.append("/");
		sbRunJobs.append(Globals.SERVLET_ADMIN);
		sbRunJobs.append("?");
		sbRunJobs.append(ParameterAdmin.PARAM_JOBS_GOTO_DSPMONJOBS);
		sbRunJobs.append("=");
		sbRunJobs.append(true);

		sbRunJobs.append("\">");

		return sbRunJobs.toString();
	}
	
	private String makeAdminHREFDSPAccessCodes() {
		StringBuffer sbAdminConf = new StringBuffer();

		sbAdminConf.append("<a href=\"");
		sbAdminConf.append(Globals.HTTP_Refer);
		sbAdminConf.append("/");
		sbAdminConf.append(Globals.SERVLET_ADMIN);
		sbAdminConf.append("?");
		sbAdminConf.append(ParameterAdmin.PARAM_ACCESS_GOTO_DSPACCESSSETTINGS);
		sbAdminConf.append("=");
		sbAdminConf.append(true);

		sbAdminConf.append("\">");

		return sbAdminConf.toString();
	}

	public String makeAccessHREFXMLFileUpdate(String sXMLFile) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<a href=\"");
		sbHTML.append(Globals.HTTP_Refer);
		sbHTML.append("/");
		sbHTML.append(Globals.SERVLET_ADMIN);
		sbHTML.append("?");
		sbHTML.append(ParameterAdmin.PARAM_ACCESS_XMLFILE_NEW);
		sbHTML.append("=");
		sbHTML.append(true);
		sbHTML.append("&");
		sbHTML.append(ParameterAdmin.PARAM_ACCESS_XMLFILE_VALUE);
		sbHTML.append("=");
		sbHTML.append(sXMLFile);

		sbHTML.append("\">");

		return sbHTML.toString();
	}

	public String makeAccessHREFUsersUpdate(String sUsers) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<a href=\"");
		sbHTML.append(Globals.HTTP_Refer);
		sbHTML.append("/");
		sbHTML.append(Globals.SERVLET_ADMIN);
		sbHTML.append("?");
		sbHTML.append(ParameterAdmin.PARAM_ACCESS_GOTO_USERS);
		sbHTML.append("=");
		sbHTML.append(true);
		sbHTML.append("&");
		sbHTML.append(ParameterAdmin.PARAM_ACCESS_USERS_VALUE);
		sbHTML.append("=");
		sbHTML.append(sUsers);

		sbHTML.append("\">");

		return sbHTML.toString();
	}

	public String makeAccessHREFAddUsers() {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<a href=\"");
		sbHTML.append(Globals.HTTP_Refer);
		sbHTML.append("/");
		sbHTML.append(Globals.SERVLET_ADMIN);
		sbHTML.append("?");
		sbHTML.append(ParameterAdmin.PARAM_ACCESS_GOTO_ADDUSER);
		sbHTML.append("=");
		sbHTML.append(true);

		sbHTML.append("\">");

		return sbHTML.toString();
	}
	

	
	
	
	public String dspLoginArchive_HTML4(String sXMLFile) {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("<table align=\"center\" border=\"0\">\n");
		sbLogin.append("	<tr><td align=\"right\">\n");
		sbLogin.append("		<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbLogin.append("Please enter an access code for this gallery<br><br>\n");
		sbLogin.append("	</td></tr>\n");
		sbLogin.append("	<tr><th align=\"right\">");
		sbLogin.append("		<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">\n");
		sbLogin.append("		<input type=\"password\"");
		sbLogin.append(" name=\"" + Globals.PARAM_ACCESS_CODE + "\"");
		sbLogin.append(" size=\"20\"");
		sbLogin.append(" maxlength=\"40\"");
		sbLogin.append("/>\n");
		sbLogin.append("	</th></tr>\n");
		
		sbLogin.append("	<tr><th align=\"right\">\n");
		sbLogin.append("		<input type=\"hidden\"  name=\"" + Globals.SERVLET_LOGIN + "\" value=\"" + sXMLFile + "\">\n");
		sbLogin.append("		<input type=\"submit\"  name=\"ForThumbnails\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("		</font>\n");
		sbLogin.append("	</th></tr>\n");
		sbLogin.append("</table>\n");

		return sbLogin.toString();
	}	
	public String dspLogin_Image_HTML4(String sXMLFile, String sImage) {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("<table align=\"center\" border=\"0\">\n");
		sbLogin.append("	<tr><td align=\"right\">\n");
		sbLogin.append("		<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbLogin.append("Please enter an access code for this image<br><br>\n");

		sbLogin.append("	</td></tr>\n");
		sbLogin.append("	<tr><th align=\"right\">\n");
		sbLogin.append("		<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">");
		sbLogin.append("<input type=\"password\"");
		sbLogin.append(" name=\"" + Globals.PARAM_ACCESS_CODE + "\"");
		sbLogin.append(" size=\"20\"");
		sbLogin.append(" maxlength=\"40\"");
		sbLogin.append("/>\n");
		sbLogin.append("	</th></tr>\n");
		
		sbLogin.append("	<tr><th align=\"right\">\n");
		sbLogin.append("		<input type=\"hidden\"  name=\"" + Globals.PARAM_XML + "\" value=\"" + sXMLFile + "\">\n");
		sbLogin.append("		<input type=\"hidden\"  name=\"" + Globals.PARAM_IMAGE + "\" value=\"" + sImage + "\">\n");
		sbLogin.append("		<input type=\"submit\"  name=\"ForThumbnails\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("		</font>\n");
		sbLogin.append("	</th></tr>\n");
		sbLogin.append("</table>\n");

		return sbLogin.toString();
	}

	public String dspLogin_All_HTML4() {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("<table align=\"center\" border=\"0\">\n");
		sbLogin.append("	<tr><td align=\"right\">\n");
		sbLogin.append("		<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbLogin.append("Please enter an access code<br><br> ");

		sbLogin.append("	</td></tr>\n");
		sbLogin.append("	<tr><th align=\"right\">\n");
		sbLogin.append("		<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">");
		sbLogin.append("<input type=\"password\"");
		sbLogin.append(" name=\"" + Globals.PARAM_ACCESS_CODE + "\"");
		sbLogin.append(" size=\"20\"");
		sbLogin.append(" maxlength=\"40\"");
		sbLogin.append("/>\n");
		sbLogin.append("	</th></tr>\n");
		
		sbLogin.append("	<tr><th align=\"right\">\n");
		sbLogin.append("		<input type=\"hidden\"  name=\"" + Globals.PARAM_SHOW_ALL + "\" value=\"" + "all" + "\">\n");
		sbLogin.append("		<input type=\"submit\"  name=\"ForShowAll\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("		</font>\n");
		sbLogin.append("	</th></tr>\n");
		sbLogin.append("</table>\n");

		return sbLogin.toString();
	}


	public String dspLogin_All_HTML5() {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("	<div class=\"galleries-title\">\n");
		sbLogin.append("		<p class=\"large\">Please enter an access code</p>\n");
		return sbLogin.toString();
	}

	public String dspLogin_Image_HTML5() {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("	<div class=\"galleries-title\">\n");
		sbLogin.append("		<p class=\"large\">Please enter an access code for this image</p>\n");
		return sbLogin.toString();
	}
	
	public String dspLoginArchive_HTML5() {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("	<div class=\"galleries-title\">\n");
		sbLogin.append("		<p class=\"large\">Please enter an access code for this gallery</p>\n");

		return sbLogin.toString();
	}	
	
	public String dspLogin_FormGalleries_HTML5() {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("		<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">\n");
		sbLogin.append("			<input type=\"password\" name=\"" + Globals.PARAM_ACCESS_CODE + "\">\n");
		sbLogin.append("			<input type=\"hidden\" name=\"" + Globals.PARAM_SHOW_ALL + "\" value=\"" + "all" + "\">\n");
		sbLogin.append("			<input type=\"submit\" name=\"ForShowAll\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("	</div> <!-- galleries-title -->\n");

		return sbLogin.toString();
	}	
	
	public String dspLogin_FormArchive_HTML5(String sXMLFile) {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("		<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">\n");
		sbLogin.append("			<input type=\"password\" \"name=\"" + Globals.PARAM_ACCESS_CODE + "\">\n");
		sbLogin.append("			<input type=\"hidden\"  name=\"" + Globals.SERVLET_LOGIN + "\" value=\"" + sXMLFile + "\">\n");
		sbLogin.append("			<input type=\"submit\"  name=\"ForThumbnails\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("	</div> <!-- galleries-title -->\n");
		return sbLogin.toString();
	}
	public String dspLogin_FormGallery_HTML5(String sXMLFile) {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("		<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">\n");
		sbLogin.append("			<input type=\"password\" name=\"" + Globals.PARAM_ACCESS_CODE + "\">\n");
		sbLogin.append("			<input type=\"hidden\" name=\"" + Globals.SERVLET_LOGIN + "\" value=\"" + sXMLFile + "\">\n");
		sbLogin.append("			<input type=\"submit\" name=\"ForThumbnails\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("	</div> <!-- galleries-title -->\n");
		return sbLogin.toString();
	}
	
	public String dspLogin_FormImage_HTML5(String sXMLFile, String sImage) {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("		<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}
		
		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">\n");
		sbLogin.append("			<input type=\"password\" name=\"" + Globals.PARAM_ACCESS_CODE + "\">\n");
		sbLogin.append("			<input type=\"hidden\" name=\"" + Globals.PARAM_XML + "\" value=\"" + sXMLFile + "\">\n");
		sbLogin.append("			<input type=\"hidden\" name=\"" + Globals.PARAM_IMAGE + "\" value=\"" + sImage + "\">\n");
		sbLogin.append("			<input type=\"submit\" name=\"ForThumbnails\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("	</div> <!-- galleries-title -->\n");

		return sbLogin.toString();
	}
	

	public String dspWrongAccessCode_HTML4() {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("<table border=\"0\" width=\"50%\">");
		sbLogin.append("<tr><td align=\"right\">");
		sbLogin.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbLogin.append("Access Code is Invalid.<br><br> ");
		sbLogin.append("</td>");

		sbLogin.append("</tr></table>");

		return sbLogin.toString();
	}
	
	public String dspWrongAccessCode_HTML5() {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("	<div class=\"galleries-title\">\n");
		sbLogin.append("		<p class=\"large\">");
		sbLogin.append("Access Code is Invalid.");
		sbLogin.append("</p>\n");
		sbLogin.append("	</div> <!-- galleries-title -->\n");

		return sbLogin.toString();
	}
	
	public String dspAvailableArchives_HTML4(String sCode, String[] xmlFiles) {

		StringBuffer sbHold = new StringBuffer();

		sbHold.append("	<tr>\n");
		sbHold.append("		<td colspan=\"2\" align=\"center\">\n");
		sbHold.append("			<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\"  size=\"+4\" face=\"arial\">");

		if (sCode.equals(Globals.access_PUBLIC)) {
			sbHold.append("<br>&nbsp;<br>&nbsp;");
			sbHold.append("Public Galleries");
			sbHold.append("<br>&nbsp;</font>\n");
		} else {
			sbHold.append("<br>&nbsp;<br>&nbsp;");
			sbHold.append("Available Galleries");
			sbHold.append("<br>&nbsp;</font>\n");
		}
		sbHold.append("		</td>\n");
		sbHold.append("	</tr>\n");

		for (int i = 0; i < xmlFiles.length; i++) {
			String sTemp = (xmlFiles[i]);
			int iLocation = sTemp.indexOf(".xml");
			sTemp = sTemp.substring(0, iLocation);

			sbHold.append("	<tr>\n");
			sbHold.append("		<td valign=\"center\" align=\"right\">\n");


			sbHold.append("			<a href=\"" + this.makeHREFGalleryHome(xmlFiles[i]));
			sbHold.append("\">\n");
			sbHold.append(this.makeMarqueeThumbnailHTML4(xmlFiles[i]));
			sbHold.append("</a>\n");
			sbHold.append("		</td>\n");
			sbHold.append("		<th valign=\"center\" align=\"left\">\n");
			sbHold.append("			<a href=\"" + this.makeHREFGalleryHome(xmlFiles[i]));
			sbHold.append("\">\n");
			sbHold.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"+2\" face=\"arial\">");
			sbHold.append(sTemp + "</font></a>\n");
			sbHold.append("		</th>\n");
			sbHold.append("	</tr>\n");
		}

		return sbHold.toString();
	}
	
	public String dspAvailableArchives_HTML5(String sCode, String[] xmlFiles) {

		StringBuffer sbHold = new StringBuffer();

		sbHold.append("	<div class=\"galleries-title\">\n");

		if (sCode.equals(Globals.access_PUBLIC)) {
			sbHold.append("		<p class=\"vlarge\">Public Galleries</p>\n");
		} else {
			sbHold.append("		<p class=\"vlarge\">Available Galleries</p>\n");
		}
		

		sbHold.append("		<div class=\"galleries\">\n");


		for (int i = 0; i < xmlFiles.length; i++) {
			String sTemp = (xmlFiles[i]);
			int iLocation = sTemp.indexOf(".xml");
			sTemp = sTemp.substring(0, iLocation);
			sbHold.append("			<figure class=\"gallery\">\n");
		
			sbHold.append("				<a href=\"" + this.makeHREFGalleryHome(xmlFiles[i]) +"\">\n");
			sbHold.append("					" + this.makeMarqueeThumbnailHTML5(xmlFiles[i]));
			sbHold.append("					<figcaption>" + sTemp + "</figcaption>\n");
			sbHold.append("				</a>\n");
			sbHold.append("			</figure>\n");
		}
		sbHold.append("		</div> <!-- galleries -->\n");
		sbHold.append("	</div> <!-- galleries-title -->\n");

		return sbHold.toString();
	}

	public String dspHeaderStart_HTML4() {
		StringBuffer sbHead = new StringBuffer();
		//force HTML 4 specs
		sbHead.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n");
		
		sbHead.append("<html>\n");
		sbHead.append("<head>\n");
		sbHead.append("<meta charset=\"UTF-8\">\n");
		sbHead.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html charset=ISO-8859-1\">\n");
		sbHead.append("<META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\">\n");
		sbHead.append("<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">\n");
		
		return sbHead.toString();
		
	}
	public String dspHeaderStart_HTML5() {
		StringBuffer sbHead = new StringBuffer();
		//force HTML 5 specs
		sbHead.append("<!DOCTYPE html>");
		
		sbHead.append("<html>\n");
		sbHead.append("<head>\n");
		sbHead.append("<META CHARSET=\"UTF-8\">\n");
		sbHead.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html UTF-8\">\n");
		sbHead.append("<META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\">\n");
		sbHead.append("<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">\n");
		sbHead.append("<META NAME=\"viewport\" CONTENT=\"width=device-width\">\n");
		
	/*sbHead.append("<script type=\"text/javascript\">\n");
		sbHead.append("var img_screen_width = 350;\n");
		sbHead.append("if (screen.width < 481 ) img_screen_width = 350;\n");
		sbHead.append("	else if (screen.width < 1000)  img_screen_width=800;\n");
		sbHead.append("</script>\n");
*/
		sbHead.append("<script type=\"text/javascript\" src=\"/gallery/support_files/gallery_401b.js\"></script>\n");
//		sbHead.append("<script type=\"text/javascript\" src=\"/gallery/support_files/jquery-2.1.4.js\"></script>\n");
		sbHead.append("<script type=\"text/javascript\" src=\"" + Globals.JQUERY + "\"></script>\n");

		sbHead.append("<style type=\"text/css\">\n");
		sbHead.append("@import url(" + Globals.HTTP_Refer + "/support_files/reset.css);\n");
		sbHead.append("@import url(" + Globals.HTTP_Refer + "/support_files/gallery_401b.css);\n");
		sbHead.append("</style>\n");
		return sbHead.toString();
	}
	
	
	public String dspHeaderEnd(String sGalleryTitle, String sPhotoTitle) {
		StringBuffer sbHead = new StringBuffer();
		boolean bPhotoTitle = true;
		
		if (sPhotoTitle == null) bPhotoTitle = false;
		if (sPhotoTitle.length() == 0 ) bPhotoTitle = false;
		sbHead.append("<META NAME=\"keywords\" HTTP-EQUIV=\"keywords\" CONTENT=\"" + Globals.USER_WEB_TITLE +"," + sGalleryTitle );
		
		if (bPhotoTitle) {
			sbHead.append("," + sPhotoTitle);
		}
		sbHead.append("\"/>\n");
		
		sbHead.append("<META NAME=\"description\" HTTP-EQUIV=\"description\" CONTENT=\"" + sGalleryTitle );
		if (bPhotoTitle) {
			sbHead.append("," + sPhotoTitle);
		}
		sbHead.append("\"/>\n");

		sbHead.append("<title>" + Globals.USER_WEB_TITLE + " - " + sGalleryTitle );
		
		if (bPhotoTitle) {
			sbHead.append(": " + sPhotoTitle);
		}
		//<meta name="description" http-equiv="description" content="$category.cat_description$">
		//<meta name="keywords" http-equiv="keywords" content="Itali ,Italie, $category.keywords$">

		sbHead.append("</title>\n");

		sbHead.append("</head>\n");
		sbHead.append("<body>\n");
//		sbHead.append("<body bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
//		sbHead.append("<basefont=\"arial\">");

		return sbHead.toString();
	}	
	
	public String dspHeaderEnd() {
		StringBuffer sbHead = new StringBuffer();
		
		sbHead.append("<title>" + Globals.USER_WEB_TITLE + "</title>\n");
		sbHead.append("<META NAME=\"keywords\" HTTP-EQUIV=\"keywords\" CONTENT=\"" + Globals.USER_WEB_TITLE + "\"/>\n");		
		sbHead.append("</head>\n");
		sbHead.append("<body>\n");
//		sbHead.append("<body bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
//		sbHead.append("<basefont=\"arial\">");

		return sbHead.toString();
	}

	public String dspFooter() {
		StringBuffer sbFoot = new StringBuffer();

		//sbFoot.append( this.dspGoogleAnalytics() );
		sbFoot.append("</body>\n");
		sbFoot.append("</html>");

		return sbFoot.toString();
	}

/*	public String dspLoginAdmin() {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("<table border=\"0\" width=\"50%\">");
		sbLogin.append("<tr><td align=\"right\">");
		sbLogin.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbLogin.append("Please enter an <b>Admin</b> access code<br><br> ");

		sbLogin.append("<tr><th align=\"right\">");
		sbLogin.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}

		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">");
		sbLogin.append("<input type=\"password\"");
		sbLogin.append(" name=\"" + ParameterAdmin.PARAM_ADMIN_ACCESS_CODE + "\"");
		sbLogin.append(" size=\"20\"");
		sbLogin.append(" maxlength=\"40\"");
		sbLogin.append("/>");
		sbLogin.append("</th>");
		sbLogin.append("</tr>");

		sbLogin.append("<tr><th align=\"right\">");
		sbLogin.append("<input type=\"submit\"  name=\"Admin\" value=\"Submit\">");
		sbLogin.append("</form>");
		sbLogin.append("</font>");
		sbLogin.append("</th>");
		sbLogin.append("</tr></table>");

		return sbLogin.toString();
	}
*/	
	public String dspLogin_Admin_HTML5() {
		StringBuffer sbLogin = new StringBuffer();

		sbLogin.append("	<div class=\"galleries-title\">\n");
		sbLogin.append("		<p class=\"large\">Please enter an <b>Admin</b> access code</p>\n");

		return sbLogin.toString();
	}	

	public String dspLogin_FormAdmin_HTML5() {
		StringBuffer sbLogin = new StringBuffer();
		sbLogin.append("		<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbLogin.append(Globals.HTTPS_Refer);
		} else {
			sbLogin.append(Globals.HTTP_Refer);
		}
		
		sbLogin.append("/" + Globals.SERVLET_LOGIN + "\" method=\"post\">\n");
		sbLogin.append("			<input type=\"password\" name=\"" + ParameterAdmin.PARAM_ADMIN_ACCESS_CODE + "\">\n");
		sbLogin.append("			<input type=\"submit\"  name=\"Admin\" value=\"Submit\">\n");
		sbLogin.append("		</form>\n");
		sbLogin.append("	</div> <!-- galleries-title -->\n");

		return sbLogin.toString();
	}
	public String dspAdminMenu_HTML5() {
		StringBuffer sbAdminMenu = new StringBuffer();

		sbAdminMenu.append("	<div class=\"galleries-title\">\n");
		sbAdminMenu.append("		<p class=\"large\">\n");
		sbAdminMenu.append("			" + this.makeAdminHREFDSPRunJobs());
		sbAdminMenu.append("Run Jobs");
		sbAdminMenu.append("</a>\n");
		sbAdminMenu.append("		</p>\n");
		
		sbAdminMenu.append("		<p class=\"large\">\n");
		sbAdminMenu.append("			" + this.makeAdminHREFDSPMonJobs());
		sbAdminMenu.append("Monitor Jobs");
		sbAdminMenu.append("</a>\n");
		sbAdminMenu.append("		</p>\n");

		sbAdminMenu.append("		<p class=\"large\">\n");
		sbAdminMenu.append("			" + this.makeAdminHREFDSPConfSettings());
		sbAdminMenu.append("Change Configuration");
		sbAdminMenu.append("</a>\n");
		sbAdminMenu.append("		</p>\n");

		sbAdminMenu.append("		<p class=\"large\">\n");
		sbAdminMenu.append("			" + this.makeAdminHREFDSPAccessCodes());
		sbAdminMenu.append("Change Access Codes");
		sbAdminMenu.append("</a>\n");
		sbAdminMenu.append("		</p>\n");
		sbAdminMenu.append("	</div> <!-- galleries-title -->\n");
		return sbAdminMenu.toString();
	}
	
/*	public String dspAdminMenu() {
		StringBuffer sbAdminMenu = new StringBuffer();

		sbAdminMenu.append("<center><table border=\"0\">");

		sbAdminMenu.append("<tr><td align=\"center\">");
		sbAdminMenu.append(this.makeAdminHREFDSPRunJobs());
		sbAdminMenu.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbAdminMenu.append("Run Jobs");
		sbAdminMenu.append("</font></a></td></tr>");
		
		sbAdminMenu.append("<tr><td align=\"center\">");
		sbAdminMenu.append(this.makeAdminHREFDSPMonJobs());
		sbAdminMenu.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbAdminMenu.append("Monitor Jobs");
		sbAdminMenu.append("</font></a></td></tr>");

		sbAdminMenu.append("<tr><td align=\"center\">");
		sbAdminMenu.append(this.makeAdminHREFDSPConfSettings());
		sbAdminMenu.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbAdminMenu.append("Change Configuration");
		sbAdminMenu.append("</font></a></td></tr>");

		sbAdminMenu.append("<tr><td align=\"center\">");
		sbAdminMenu.append(this.makeAdminHREFDSPAccessCodes());
		sbAdminMenu.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbAdminMenu.append("Change Access Codes");
		sbAdminMenu.append("</font></a></td></tr>");

		sbAdminMenu.append("</table>");

		return sbAdminMenu.toString();
	}
*/
	public String dspGotoHome() {
		StringBuffer sbHTML = new StringBuffer();


		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">\n");
		sbHTML.append(this.makeHREFArchiveHome());
		sbHTML.append(Globals.USER_WEB_TITLE);
		sbHTML.append("</a>\n");
		sbHTML.append("		</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}
	public String dspExceptionError(Exception e) {
		StringBuffer sbAdminMenu = new StringBuffer();

		sbAdminMenu.append("<table border=\"0\" width=\"50%\">");

		sbAdminMenu.append("<tr><td align=\"right\">");
		sbAdminMenu.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_LESS + "\" size=\"4\" face=\"arial\">");
		sbAdminMenu.append("Error: " + e.getMessage());
		sbAdminMenu.append("</font></a></td></tr>");

		sbAdminMenu.append("</table>");

		return sbAdminMenu.toString();
	}



	public String dspMessage(String sMessage) {
		StringBuffer sbHTML = new StringBuffer();

		if (Globals.checkUserAgentHTML5()) {
			sbHTML.append("	<div class=\"galleries-title\">\n");
			sbHTML.append("		<p class=\"vlarge\">" + sMessage + "</p>\n");
			sbHTML.append("	</div <!-- galleries-title -->\n");

		} else {
		sbHTML.append("<center>\n");

		sbHTML.append("<table border=\"0\" width=\"400\" cellpadding=\"1\" bgcolor=\""
				+ Globals.USER_HTML_FOREGROUND_COLOR + "\" >\n");
		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td height=\"20\" colspan=\"3\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" >\n");
		sbHTML.append("<bold><font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"+2\" face=\"arial\">"
				+ sMessage + "</font></bold>");
		sbHTML.append("</td></tr>\n");

		sbHTML.append("</td></tr></table>\n");
		}
		
		return sbHTML.toString();
	}

	public String debugAccess(String sCode, String sXMLFile, int iXMLFileLocation, int iUserLocation, boolean bFound) {
		StringBuffer sbHTML = new StringBuffer();
		if (Globals.USER_DEBUG_ACCESS) {

			sbHTML.append("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			sbHTML.append("<tr><td colspan=\"2\"><h2>Master Code Check 1</h2></td></tr>");

			sbHTML.append("<tr><th align=\"right\">Access Code:</th>");
			sbHTML.append("<td>" + sCode + "</td>");
			sbHTML.append("</tr>");

			sbHTML.append("<tr><th align=\"right\">XML File:</th>");
			sbHTML.append("<td>" + sXMLFile + "</td>");
			sbHTML.append("</tr>");

			sbHTML.append("<tr><th align=\"right\">Location XMLFile:</th>");
			sbHTML.append("<td>" + iXMLFileLocation + "</td>");
			sbHTML.append("</tr>");

			sbHTML.append("<tr><th align=\"right\">Location User:</th>");
			sbHTML.append("<td>" + iUserLocation + "</td>");
			sbHTML.append("</tr>");

			sbHTML.append("<tr><th align=\"right\">Valid User:</th>");
			sbHTML.append("<td>" + bFound + "</td>");
			sbHTML.append("</tr>");
			sbHTML.append("</table>");
		}

		return sbHTML.toString();
	}

	public String debugValidAccessCode(String sCode, int iUserLocation, boolean bFound) {
		StringBuffer sbHTML = new StringBuffer();
		
		if (Globals.USER_DEBUG) {

			sbHTML.append("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			sbHTML.append("<tr><td colspan=\"2\"><h2>Master Code Check</h2></td></tr>");

			sbHTML.append("<tr><th align=\"right\">Access Code:</th>");
			sbHTML.append("<td>" + sCode + "</td>");
			sbHTML.append("</tr>");

			sbHTML.append("<tr><th align=\"right\">Location User:</th>");
			sbHTML.append("<td>" + iUserLocation + "</td>");
			sbHTML.append("</tr>");
			sbHTML.append("<tr><th align=\"right\">Valid User:</th>");
			sbHTML.append("<td>" + bFound + "</td>");
			sbHTML.append("</tr>");
			sbHTML.append("</table>");
		}

		return sbHTML.toString();
	}
	
	
	public String dspGoogleAnalytics() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<!-- Google Analytics -->\n");

		sbHTML.append("<script type=\"text/javascript\">\n");
		sbHTML.append("(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){ (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n");

		sbHTML.append("ga('create', 'UA-5048550-2', 'auto');\n");  // Replace with your property ID.
				sbHTML.append("ga('send', 'pageview');\n");

		sbHTML.append("</script>\n");
		sbHTML.append("<!-- End Google Analytics -->\n");
		
		
		return sbHTML.toString();
	}	
	

}