package xilodyne.photo_gallery.globals;

import java.util.Properties;

import xilodyne.photo_gallery.fileio.HSQLDB;

/**
 * @author aholiday
 *
 */
public class Globals {


	public final static String SERVLET_PHOTO="photo";
	public final static String SERVLET_LOGIN="login";
	public final static String SERVLET_ADMIN="admin";

	public static Properties propertiesAccessUsers = new Properties();
	public static Properties propertiesAccessFiles = new Properties();
	
	public static String CATALINE_HOME = "";

	
/*****************************/
/**  Development Settings   **/
/*****************************/

	//copy conf/gallery_defaults.property-dev to conf/gallery_defaults.property
	//disable xilodyne.photo_gallery.fileio.configfile.java line 479 
	//change log4j in web.xml
	//comment line 1143, Google Analytics in xilodyne.photo_gallery.http.AssembleHTTP:makeFooter

	
	public static String HTTP_Refer = "http://localhost:8080/gallery";
//	public static String HTTPS_Refer = "https://localhost:8444/gallery";

	public final static String GALLERY_DEFAULT_CONF_DIR="/wtpwebapps/PhotoGallery_v4.3_9.0.1/conf/";
	public final static String GALLERY_DEFAULT_DEPLOY_DIR="/wtpwebapps/PhotoGallery_v4.3_9.0.1/";
	public static String HTTP_ROOT_MARQUEE_DIR="galleries/marquee";
	public static String FILESYSTEM_ROOT_DIR="/wtpwebapps/PhotoGallery_v4.3_9.0.1/galleries/";
	public static String JQUERY = Globals.HTTP_Refer + "/support_files/jquery-2.1.4.js";
	//public static String JQUERY = "https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js";
//	public static String dbPath = "C:/Backup/JavaCode/Java8-Luna/Projects/Web/PhotoGalleryProjects/PhotoGallery-v4.1(hashDB)/database/dbfilesProd/gallery";
	public static String dbPath = "C:/Backup/Java/Web/workspace-PhotoGallery/PhotoGallery_v4.3_9.0.1/WebContent/database/db-prod/gallery";
//	private static String dbPath = "C:.Backup.JavaCode.Java8-Luna.Projects.Web.PhotoGalleryProjects.PhotoGallery-v4.1(hashDB).database.dbfilesProd.gallery";
	
	
/******************************/
/** END Development Settings **/
/******************************/

/*****************************/
/**   Production Settings   **/
/*****************************/

	//copy conf/gallery_defaults.property-prod to conf/gallery_defaults.property
	//enable xilodyne.photo_gallery.fileio.configfile.java line 479 
	//change log4j in web.xml
	//uncomment line 1143, Google Analytics in xilodyne.photo_gallery.http.AssembleHTTP:makeFooter
	/*
	public static String HTTP_Refer = "/gallery";
	public static String GALLERY_DEFAULT_CONF_DIR="/webapps/gallery/conf/";
	public static String GALLERY_DEFAULT_DEPLOY_DIR="/webapps/gallery/";
	public static String HTTP_ROOT_MARQUEE_DIR="images/marquee";
	public static String FILESYSTEM_ROOT_DIR=GALLERY_DEFAULT_DEPLOY_DIR;
	public static String JQUERY = "https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js";
//	public static String dbPath = "/webapps/gallery/database/db-prod/gallery";
	public static String dbPath = "/opt/gallery.app/database/db-prod/gallery";
	*/
	
/*****************************/
/** End Production Settings **/
/*****************************/

	private static String className = "";
	
	public Globals() {
		//production
		CATALINE_HOME = System.getProperty("catalina.base");
		Globals.className = this.getClass().getName();
		HSQLDB.makeDBConnection();
		Logging.info(Globals.className, "CATALINE_HOME: " + CATALINE_HOME);
	}
	

	//gallery defined properties from config file
	public static String GALLERY_USER_PROP_LOCATION="";
	public static String GALLERY_XML_DIR_LOCATION="";
	public static String GALLERY_ACCESS_CODE_LOCATION="";
	public static String GALLERY_ACCESS_USERS_LOCATION="";
	public static String GALLERY_ACCESS_FILE_LOCATION="";
	public static String GALLERY_METADATA_FILE_LOCATION="";
	
	public static String GALLERY_DIRECTORY_LISTINGS="";
	public static String GALLERY_QUEUE_RASTERIZATION="";
	public static String GALLERY_QUEUE_XML_FILE_UPDATE="";
	
	public static String DB_NAME="PhotoGalleryDB";
	public static String GALLERY_DB_PATH="";
	public static String DB_USER="Photo";
	public static String DB_PASSWORD="Gallery";
	
	public static String HTTP_ROOT_ORIGINALS_DIR="galleries/originals";
	public static String HTTP_ROOT_WEB_DIR="galleries/web";
	public static String HTTP_ROOT_THUMBNAIL_DIR="galleries/thumbnails";

	
//	public static String FILESYSTEM_ROOT_ORIGINALS_DIR="S:/Photos";
//	public static String FILESYSTEM_ROOT_DIR="F:/Java/TomcatWorkArea/wtpwebapps/PhotoGallery/galleries/";
//	public static String FILESYSTEM_ROOT_DIR="C:/Backup/Projects/Web/PhotoGalleryProjects/TomcatPhotoGallery-v2.0/WebContent/galleries/";
//	public static String FILESYSTEM_ROOT_ORIGINALS_DIR=		GALLERY_DEFAULT_PHOTO_DIR + "originals";
//*	public static String FILESYSTEM_ROOT_ORIGINALS_DIR=		FILESYSTEM_ROOT_DIR + "originals";
//*	public static String FILESYSTEM_ROOT_WEB_DIR=			FILESYSTEM_ROOT_DIR + "web";
//*	public static String FILESYSTEM_ROOT_THUMBNAIL_DIR=		FILESYSTEM_ROOT_DIR + "thumbnails";
//*	public static String FILESYSTEM_ROOM_MARQUEE=			FILESYSTEM_ROOT_DIR + "marquee";
	

	
	public static String SITEMAP = "";
	
//	public static String SITEMAP = "f:/backup/Development/Projects/Web/PhotoGallery/WebContent/conf/sitemap.xml";
	
	public static String HTTPS_Refer = "";
	private static boolean UserAgentHTML5 = true;

	public static boolean ENABLE_SSL = false;


	public final static String KEY_NAME="GalleryKey.ser";
	public final static String SESSION_PHOTO_ACCESS="photo_access";
	public final static String SESSION_ADMIN_ACCESS="admin_access";
	public final static String DEFAULT_PROP_FILE="gallery_defaults.property";
	public final static String DEFAULT_USER_PROP_FILE="gallery_user.property";
	
	public final static String PROP_USER_FILE="user_property_file";
	
	public final static String COOKIE_PHOTO="Gallery";
	public final static String COOKIE_ADMIN="Admin";
	public final static String COOKIE_Path="/gallery";
	
	public final static String EXIF_COMMENTS="Windows XP Comment";
	public final static String EXIF_AUTHOR="Windows XP Author";
	
	public final static String PARAM_DEBUG="debug";
	public final static String PARAM_THUMB="thumbnails";
	public final static String PARAM_IMAGE="image";
	public final static String PARAM_XML="xml";
	public final static String PARAM_ORIGINALS="original";
	public final static String PARAM_ORIGINALS_DOWNLOAD="download";
	public final static String PARAM_ORIG_FILESIZE="origfilesize";
	public final static String PARAM_LOGIN="photo_login";
	public final static String PARAM_NEW_LOGIN="new_login";
	public final static String PARAM_ACCESS_CODE="photo_access_code";
	public final static String PARAM_LOGIN_CHECK="checkedlogin";
	public final static String PARAM_SHOW_ALL="show_all";
	public final static String PARAM_NEW_SESSION="NEWSESSION";
	public final static String PARAM_EXIF="exif";
	
	
	public final static String EXIFTOOL_DESC="XPComment";
	public final static String EXIFTOOL_AUTHOR="XPAuthor";
	public final static int EXIFTOOL_DESC_POS = 1;
	public final static int EXIFTOOL_AUTHOR_POS = 0;

	public final static String REMOVE="remove";
	public final static int FALSE=0;
	public final static int TRUE=1;
	public final static int NOT_FOUND=-1;
	public final static String TRUE_STRING="1";
	
	
	
	public static String access_PUBLIC="PublicGuest";
	public static String access_XML_FILE="xml_file";
	public static String access_FILE_NAME="file_name";
	public static String access_ARRAY_VALUE="array_value";
	public static String access_USER="user";
	public static String access_NAME="name";
	public static String access_ACCESS_TO="access_to";
	
	
	
	//user defined properties from config file
	public static boolean USER_DEBUG=false;
	public static boolean USER_DEBUG_ACCESS=false;
	public static boolean THREAD_DEBUG=true;
	public static String USER_WEB_TITLE="";
	public static String USER_ADMIN_PASSWORD="";
	public static String USER_HTML_FOREGROUND_COLOR="black";
	public static String USER_HTML_BACKGROUND_COLOR="white";
	public static String USER_HTML_FOREGROUND_LESS="silver";
	
	
	private static boolean accessDataLoaded = false;
	private static boolean saveFiles = false;
	

	public static boolean checkDataLoaded() {
		return accessDataLoaded;
	}
	public static void setDataLoaded(){
		accessDataLoaded = true;
	}
	public static void setDataUnloaded() {
		accessDataLoaded = false;
	}
	
	public static boolean checkFileSaved() {
		return saveFiles;
	}
	public static void setCheckFileSavedTrue() {
		saveFiles = true;
	}
	public static void setCheckFileSavedDone() {
		saveFiles = false;
	}
	
	public static boolean checkUserAgentHTML5() {
		return UserAgentHTML5;
	}
	public static void setUserAgentHTML5True() {
		UserAgentHTML5 = true;
	    Logging.info(className,"UserAgent HTML 5 = true");

	}
	public static void setUserAgentHMTL5False() {
		UserAgentHTML5 = false;
		Logging.info(className,"UserAgent HTML 5 = false");
	}
}
	