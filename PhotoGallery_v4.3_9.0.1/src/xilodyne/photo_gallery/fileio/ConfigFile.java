package xilodyne.photo_gallery.fileio;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import xilodyne.photo_gallery.globals.AccessMatrix;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

/**
 * Read the default config file that points to the user defined values. User
 * defined can be located in a different directory. If default config is not
 * found, load default settings into file and save. If user defined found and
 * not default and bad values, ignore and use default user config
 * 
 * @author aholiday
 * 
 */

/**
 * @author aholiday
 * 
 */

public class ConfigFile {
	
	Logging log = new Logging(); //init the logging

	private FileInputStream galleryStream = null;
	private FileInputStream userStream = null;
	private FileInputStream accessUserStream = null;
	private FileInputStream accessFileStream = null;

	private FileOutputStream galleryOutStream = null;
	private FileOutputStream userOutStream = null;
	private FileOutputStream accessUserOutStream = null;
	private FileOutputStream accessFileOutStream = null;

	private Properties propertiesGallery = new Properties();
	private Properties propertiesUser = new Properties();

	private boolean bGalleryFileRead = false;
	private boolean bUserFileRead = false;

	// user property keys
	private final static String PROP_DEBUG = "DEBUG";
	private final static String PROP_DEBUG_ACCESS = "DEBUG_ACCESS";
	private final static String PROP_WEB_TITLE = "WEB_TITLE";
	private final static String PROP_ADMIN_PASSWORD = "ADMIN_PASSWORD";
	private final static String PROP_HTML_FOREGROUND_COLOR = "HTML_FOREGROUND_COLOR";
	private final static String PROP_HTML_BACKGROUND_COLOR = "HTML_BACKGROUND_COLOR";
	private final static String PROP_HTML_FOREGROUND_LESS = "HTML_FOREGROUND_LESS";
	private final static String PROP_HTTP_REFER = "HTTP_REFER";
	private final static String PROP_HTTPS_REFER = "HTTPS_REFER";
	private final static String PROP_ENABLE_SSL = "ENABLE_SSL";

	private final static String PROP_ROOT_ORIGINALS_DIR = "ROOT_ORIGINALS_DIR";
	private final static String PROP_ROOT_WEB_DIR = "ROOT_WEB_DIR";
	private final static String PROP_ROOT_THUMBNAILS_DIR = "ROOT_THUMBNAILS_DIR";

	// gallery property keys
	private final static String PROP_ACCESSCODE_FILE = "ACCESS_CODE_FILE";
	private final static String PROP_DB_NAME = "DB_NAME";
	private final static String PROP_DB_PATH = "DB_PATH";
	private final static String PROP_DB_USER = "DB_USER";
	private final static String PROP_DB_PASSWORD = "DB_PASSWORD";
	private final static String PROP_GALLERY_DIR = "GALLERY_DIRECTORY";
	private final static String PROP_USER_FILE = "USER_PROPERTY_FILE";
	private final static String PROP_ACCESSUSERS_FILE = "ACCESS_USERS_FILE";
	private final static String PROP_ACCESSFILES_FILE = "ACCESS_FILES_FILE";

	public final static String FILE_ACCESS_CODE = "access.txt";
	public final static String FILE_ACCESS_FILES = "access_files.property";
	public final static String FILE_GALLERY_XML = "xmlfiles";
	public final static String FILE_ACCESS_USERS = "access_users.property";
	public final static String FILE_USER_PROP = "gallery_user.property";

	public final static String TRACKING_DIR_LISTINGS = "/tracking/directory-listings";
	public final static String TRACKING_QUEUE_RAST = "/tracking/queue-rasterization";
	public final static String TRACKING_QUEUE_XMLUPDATE = "/tracking/queue-xmlfileupdate";

	public ConfigFile() throws IOException {

		Logging.debug(this.getClass().getName(), "Config init, data loaded: "
				+ Globals.checkDataLoaded());

		if (!Globals.checkDataLoaded())
			propertiesGallery = new Properties();
		this.readGalleryPropertyFile();
		this.loadGalleryGlobals();
		this.readUserPropertyFile();
		this.readAccessPropertyFiles();
		this.loadUserSettingGlobals();

		Globals.setDataLoaded();
		Logging.setLoggingLevel();
	}

	/*
	 * Load the Gallery Property file that points to user file
	 */
	private void readGalleryPropertyFile() {
//		this.getCatalinaHome();
		String sFileName = Globals.CATALINE_HOME
				+ Globals.GALLERY_DEFAULT_CONF_DIR + Globals.DEFAULT_PROP_FILE;
		Logging.debug(this.getClass().getName(), "Reading " + Globals.DEFAULT_PROP_FILE +": " + sFileName);
		try {
			this.galleryStream = new FileInputStream(sFileName);

			propertiesGallery.loadFromXML(this.galleryStream);
			Logging.debug(this.getClass().getName(), "Size: " + propertiesGallery.size());
			this.galleryStream.close();
		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}

		this.bGalleryFileRead = true;
	}

	/*
	 * Load the User Property file
	 */
	private void readUserPropertyFile() {
		Logging.debug(this.getClass().getName(), "Reading " + Globals.DEFAULT_USER_PROP_FILE +": " + Globals.GALLERY_USER_PROP_LOCATION);
		try {
			this.userStream = new FileInputStream(
					Globals.GALLERY_USER_PROP_LOCATION);
			this.propertiesUser.loadFromXML(this.userStream);

			Logging.debug(this.getClass().getName(), "Size: " + propertiesUser.size());
			this.userStream.close();
		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}

		this.bUserFileRead = true;
	}

	public void saveAccessFiles() throws IOException {
		updateAccessProperties();
		writeAccessFiles();
	}

	private void updateAccessProperties() {
		Globals.propertiesAccessUsers = new Properties();

		for (int i = 0; i < AccessMatrix.getUserSize(); i++) {
			Globals.propertiesAccessUsers.put(String.valueOf(i),
					AccessMatrix.getUser(i));
		}

		Globals.propertiesAccessFiles = new Properties();
		for (int i = 0; i < AccessMatrix.getXMLSize(); i++) {
			Globals.propertiesAccessFiles.put(String.valueOf(i),
					AccessMatrix.getXMLFile(i));
		}
	}

	private void writeAccessFiles() {
		Date date = new Date();

		Logging.debug(this.getClass().getName(), "File: reading --> " + PROP_ACCESSUSERS_FILE);
		try {
			this.accessUserOutStream = new FileOutputStream(
					this.propertiesGallery.getProperty(PROP_ACCESSUSERS_FILE));
			this.accessFileOutStream = new FileOutputStream(
					this.propertiesGallery.getProperty(PROP_ACCESSFILES_FILE));

			Globals.propertiesAccessUsers.storeToXML(this.accessUserOutStream,
					date.toString());
			Globals.propertiesAccessFiles.storeToXML(this.accessFileOutStream,
					date.toString());
			Logging.debug(this.getClass().getName(), "Size: " + propertiesGallery.size());

			this.accessUserOutStream.close();
			this.accessFileOutStream.close();
		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}

		Logging.debug(this.getClass().getName(), "File: writing --> "
				+ Globals.GALLERY_ACCESS_CODE_LOCATION);
		try {
			BufferedWriter config_file = new BufferedWriter(new FileWriter(
					Globals.GALLERY_ACCESS_CODE_LOCATION));
			int iBoolean = 0;
			for (int x = 0; x < AccessMatrix.getXMLSize(); x++) {
				StringBuffer sbLine = new StringBuffer();

				sbLine.append(x);
				sbLine.append(",");

				for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
					if (AccessMatrix.getCode(x, y)) {
						iBoolean = Globals.TRUE;
					} else {
						iBoolean = Globals.FALSE;
					}
					sbLine.append(iBoolean);
					sbLine.append(",");
				}

				config_file.write(sbLine.toString());
				config_file.newLine();
			}
			config_file.close();
		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}
	}

	private void writeGalleryFile() throws IOException {
		Date date = new Date();
		String sFileName = Globals.CATALINE_HOME
				+ Globals.GALLERY_DEFAULT_CONF_DIR + "/"
				+ Globals.DEFAULT_PROP_FILE;
		this.galleryOutStream = new FileOutputStream(sFileName);
		propertiesGallery.storeToXML(this.galleryOutStream, date.toString());
		this.galleryOutStream.close();
	}

	private void writeUserPropertyFile() throws IOException {
		Date date = new Date();
		String sFileName = this.propertiesGallery.getProperty(PROP_USER_FILE);
		this.userOutStream = new FileOutputStream(sFileName);
		this.propertiesUser.storeToXML(this.userOutStream, date.toString());
		this.userOutStream.close();

	}

	/*
	 * Load the User Property file
	 */
	public void readUserPropertyFile(String sFileName)
			throws FileNotFoundException {
		// String sFileName =
		// this.propertiesGallery.getProperty(PROP_USER_FILE);
		Logging.debug(this.getClass().getName(), "File: reading --> " + sFileName);
		try {
			this.userStream = new FileInputStream(sFileName);
			// System.out.println("ConfigFile: reading --> " + sFileName );
			this.propertiesUser.loadFromXML(this.userStream);
			this.userStream.close();

			Logging.debug(this.getClass().getName(), "Size: " + propertiesUser.size());

			this.loadUserSettingGlobals();
		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}

	}

	/*
	 * Load the Access Property files
	 */
	public void readAccessPropertyFiles() {
		Logging.debug(this.getClass().getName(), "File access users: reading --> "
				+ Globals.GALLERY_ACCESS_USERS_LOCATION);

		try {
			this.accessUserStream = new FileInputStream(
					Globals.GALLERY_ACCESS_USERS_LOCATION);
			this.accessFileStream = new FileInputStream(
					Globals.GALLERY_ACCESS_FILE_LOCATION);

			Globals.propertiesAccessUsers.loadFromXML(this.accessUserStream);
			Globals.propertiesAccessFiles.loadFromXML(this.accessFileStream);
			Logging.debug(this.getClass().getName(), "Size: " + Globals.propertiesAccessFiles.size());

		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());

		}

		String sFileLine;

		// open the file
		// read the first line
		// format: xmlfile,user1,user2,user3,
		// count the number of commas, total-1 = # of users
		// count the number of lines, total = # of xml files
		// initialize the accessmatrix
		// read the line
		// 0 = false
		// 1 = true

		Logging.debug(this.getClass().getName(), "File access code: reading --> "
				+ Globals.GALLERY_ACCESS_CODE_LOCATION);

		BufferedReader config_file;
		boolean doFirstLine = true;
		int iCommas = 0;
		int iLineCount = 0;

		try {
			config_file = new BufferedReader(new FileReader(
					Globals.GALLERY_ACCESS_CODE_LOCATION));
	
			// keep reading each line until eol
			while ((sFileLine = config_file.readLine()) != null) {
				Logging.debug(this.getClass().getName(), "Line: " + sFileLine);
				if (doFirstLine) {
					StringTokenizer stCount = new StringTokenizer(sFileLine,
							",");
					iCommas = stCount.countTokens() - 1;
					doFirstLine = false;
				}
				iLineCount++;
			}

			config_file.close();

		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}

		Logging.debug(this.getClass().getName(), "File access code: reading --> "
				+ Globals.GALLERY_ACCESS_CODE_LOCATION);

		try {
			config_file = new BufferedReader(new FileReader(
					Globals.GALLERY_ACCESS_CODE_LOCATION));

			Logging.debug(this.getClass().getName(), "iLineCount: " + iLineCount + "," + iCommas);
			AccessMatrix.createAccessMatrix(iLineCount, iCommas);
			Logging.debug(this.getClass().getName(), "AccessMatrix: " + AccessMatrix.getUserSize());
			int iLineLocation = 0;
			iLineCount = 0;

			// keep reading each line until eol
			while ((sFileLine = config_file.readLine()) != null) {
				StringTokenizer stValue = new StringTokenizer(sFileLine, ",");

				// first token is file, so skip
				if (stValue.hasMoreElements()) {
					stValue.nextToken();
				}

				while (stValue.hasMoreElements()) {

					if (stValue.nextToken().compareTo(Globals.TRUE_STRING) == 0) {
						Logging.debug(this.getClass().getName(), iLineCount + ":" + iLineLocation
									+ ":" + "Array Size: "
									+ AccessMatrix.getXMLSize() + ":"
									+ AccessMatrix.getUserSize());
						AccessMatrix.updateAccessMatrix(iLineCount,
								iLineLocation);
					}
					iLineLocation++;
				}

				iLineCount++;
				iLineLocation = 0;
			}

			config_file.close();
		} catch (IOException e) {
			Logging.error(e.getLocalizedMessage());
		}

	}

	/*
	 * Read from default location unless otherwise specified.
	 */
	private void loadGalleryGlobals() {

		if (!this.bGalleryFileRead)
			return;

		String sLocation = "";

		sLocation = this.propertiesGallery.getProperty(PROP_USER_FILE);
		if (sLocation.isEmpty() || (sLocation.length() == 0)) {
			sLocation = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_CONF_DIR + FILE_USER_PROP;
			this.propertiesGallery.setProperty(PROP_USER_FILE, sLocation);
		}
		Globals.GALLERY_USER_PROP_LOCATION = sLocation;

		sLocation = this.propertiesGallery.getProperty(PROP_GALLERY_DIR);
		if (sLocation.isEmpty() || (sLocation.length() == 0)) {
			sLocation = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_CONF_DIR + FILE_GALLERY_XML;
			this.propertiesGallery.setProperty(PROP_GALLERY_DIR, sLocation);
		}

		Globals.GALLERY_XML_DIR_LOCATION = sLocation;

		Globals.GALLERY_METADATA_FILE_LOCATION = Globals.GALLERY_XML_DIR_LOCATION
				+ "/../metadata";
		Globals.GALLERY_DIRECTORY_LISTINGS = Globals.GALLERY_XML_DIR_LOCATION
				+ TRACKING_DIR_LISTINGS;
		Globals.GALLERY_QUEUE_RASTERIZATION = Globals.GALLERY_XML_DIR_LOCATION
				+ TRACKING_QUEUE_RAST;
		Globals.GALLERY_QUEUE_XML_FILE_UPDATE = Globals.GALLERY_XML_DIR_LOCATION
				+ TRACKING_QUEUE_XMLUPDATE;

		sLocation = this.propertiesGallery.getProperty(PROP_ACCESSCODE_FILE);
		if (sLocation.isEmpty() || (sLocation.length() == 0)) {
			sLocation = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_CONF_DIR + FILE_ACCESS_CODE;
			this.propertiesGallery.setProperty(PROP_ACCESSCODE_FILE, sLocation);
		}
		Globals.GALLERY_ACCESS_CODE_LOCATION = sLocation;

		sLocation = this.propertiesGallery.getProperty(PROP_ACCESSUSERS_FILE);
		if (sLocation.isEmpty() || (sLocation.length() == 0)) {
			sLocation = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_CONF_DIR + FILE_ACCESS_USERS;
			this.propertiesGallery
					.setProperty(PROP_ACCESSUSERS_FILE, sLocation);
		}
		Globals.GALLERY_ACCESS_USERS_LOCATION = sLocation;

		sLocation = this.propertiesGallery.getProperty(PROP_ACCESSFILES_FILE);
		if (sLocation.isEmpty() || (sLocation.length() == 0)) {
			sLocation = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_CONF_DIR + FILE_ACCESS_FILES;
			this.propertiesGallery
					.setProperty(PROP_ACCESSFILES_FILE, sLocation);
		}
		Globals.GALLERY_ACCESS_FILE_LOCATION = this.propertiesGallery
				.getProperty(PROP_ACCESSFILES_FILE);

		sLocation = this.propertiesGallery.getProperty(PROP_DB_PATH);
		try {
			if (sLocation.isEmpty() || (sLocation.length() == 0)) {
				sLocation = Globals.CATALINE_HOME
						+ Globals.GALLERY_DEFAULT_CONF_DIR;
				this.propertiesGallery.setProperty(PROP_DB_PATH, sLocation);
			}

		} catch (NullPointerException e) {
			sLocation = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_CONF_DIR;
			this.propertiesGallery.setProperty(PROP_DB_PATH, sLocation);
		}

		Globals.GALLERY_DB_PATH = this.propertiesGallery
				.getProperty(PROP_DB_PATH);

	}

	/*
	 * Move the User properties into the Globals class so that all of the
	 * application can have access to it.
	 */
	private void loadUserSettingGlobals() {

		String sCheckValues = "";

		if (!this.bUserFileRead)
			return;

		Globals.USER_DEBUG = Boolean.valueOf(this.propertiesUser
				.getProperty(PROP_DEBUG));
		Logging.debug(this.getClass().getName(), "USER_DEBUG: "
				+ Boolean.valueOf(this.propertiesUser.getProperty(PROP_DEBUG)));
		// Globals.USER_DEBUG=true;
		Globals.USER_DEBUG_ACCESS = Boolean.valueOf(this.propertiesUser
				.getProperty(PROP_DEBUG_ACCESS));
		Globals.USER_WEB_TITLE = this.propertiesUser
				.getProperty(PROP_WEB_TITLE);
		Globals.USER_ADMIN_PASSWORD = this.propertiesUser
				.getProperty(PROP_ADMIN_PASSWORD);
		Logging.debug(this.getClass().getName(), "password:" + Globals.USER_ADMIN_PASSWORD);
		Globals.USER_HTML_FOREGROUND_COLOR = this.propertiesUser
				.getProperty(PROP_HTML_FOREGROUND_COLOR);
		Globals.USER_HTML_BACKGROUND_COLOR = this.propertiesUser
				.getProperty(PROP_HTML_BACKGROUND_COLOR);
		Globals.USER_HTML_FOREGROUND_LESS = this.propertiesUser
				.getProperty(PROP_HTML_FOREGROUND_LESS);

		/***************************/
		/* comment out for testing */
		/*
		Globals.HTTP_Refer = this.propertiesUser.getProperty(PROP_HTTP_REFER);
		Globals.HTTPS_Refer = this.propertiesUser.getProperty(PROP_HTTPS_REFER);
		Globals.ENABLE_SSL = Boolean.valueOf(this.propertiesUser
				.getProperty(PROP_ENABLE_SSL));
		*/
		/******end section *********/
		
		sCheckValues = this.propertiesUser.getProperty(PROP_DB_NAME);
		try {
			if (sCheckValues.isEmpty() || (sCheckValues.length() == 0)) {
				sCheckValues = Globals.DB_NAME;
				this.propertiesGallery.setProperty(PROP_DB_NAME, sCheckValues);
			}

		} catch (NullPointerException e) {
			sCheckValues = Globals.DB_NAME;
			this.propertiesGallery.setProperty(PROP_DB_NAME, sCheckValues);
		}

		sCheckValues = this.propertiesUser.getProperty(PROP_DB_USER);
		try {
			if (sCheckValues.isEmpty() || (sCheckValues.length() == 0)) {
				sCheckValues = Globals.DB_USER;
				this.propertiesGallery.setProperty(PROP_DB_USER, sCheckValues);
			}

		} catch (NullPointerException e) {
			sCheckValues = Globals.DB_USER;
			this.propertiesGallery.setProperty(PROP_DB_USER, sCheckValues);
		}

		sCheckValues = this.propertiesUser.getProperty(PROP_DB_PASSWORD);
		try {
			if (sCheckValues.isEmpty() || (sCheckValues.length() == 0)) {
				sCheckValues = Globals.DB_PASSWORD;
				this.propertiesGallery.setProperty(PROP_DB_PASSWORD,
						sCheckValues);
			}

		} catch (NullPointerException e) {
			sCheckValues = Globals.DB_PASSWORD;
			this.propertiesGallery.setProperty(PROP_DB_PASSWORD, sCheckValues);
		}

		// check if values have been loaded from config file as older versions
		// do not have this variable
		if (this.propertiesUser.getProperty(PROP_ROOT_ORIGINALS_DIR) != null) {
			Globals.HTTP_ROOT_ORIGINALS_DIR = this.propertiesUser
					.getProperty(PROP_ROOT_ORIGINALS_DIR);
		} else {
			this.propertiesUser.setProperty(PROP_ROOT_ORIGINALS_DIR,
					Globals.HTTP_ROOT_ORIGINALS_DIR);
		}

		if (this.propertiesUser.getProperty(PROP_ROOT_WEB_DIR) != null) {
			Globals.HTTP_ROOT_WEB_DIR = this.propertiesUser
					.getProperty(PROP_ROOT_WEB_DIR);
		} else {
			this.propertiesUser.setProperty(PROP_ROOT_WEB_DIR,
					Globals.HTTP_ROOT_WEB_DIR);
		}

		if (this.propertiesUser.getProperty(PROP_ROOT_THUMBNAILS_DIR) != null) {
			Globals.HTTP_ROOT_THUMBNAIL_DIR = this.propertiesUser
					.getProperty(PROP_ROOT_THUMBNAILS_DIR);
		} else {
			this.propertiesUser.setProperty(PROP_ROOT_THUMBNAILS_DIR,
					Globals.HTTP_ROOT_THUMBNAIL_DIR);
		}

		Globals.setDataUnloaded();

	}

	/*
	 * Save the Gallery conf
	 */

	public void updateGalleryProperty(String sGalleryFile) throws IOException {
		propertiesGallery.setProperty(PROP_USER_FILE, sGalleryFile);
		this.writeGalleryFile();
	}

	public void updateUser_AdminPassword(String sPassword) throws IOException {
		// System.out.println("new value: " + sPassword);
		this.propertiesUser.setProperty(PROP_ADMIN_PASSWORD, sPassword);
		this.loadUserSettingGlobals();
		this.writeUserPropertyFile();
	}

	public void updateUser_DEBUG(String sDebug, String sDebugAccess)
			throws IOException {
		this.propertiesUser.setProperty(PROP_DEBUG, sDebug);
		this.propertiesUser.setProperty(PROP_DEBUG_ACCESS, sDebugAccess);
		this.loadUserSettingGlobals();
		this.writeUserPropertyFile();
	}

	public void updateUser_HTMLTitle(String sTitle) throws IOException {
		this.propertiesUser.setProperty(PROP_WEB_TITLE, sTitle);
		this.loadUserSettingGlobals();
		this.writeUserPropertyFile();
	}

	public void updateUser_HTMLColor(String sForeground, String sBackground,
			String sForeground_less) throws IOException {
		this.propertiesUser
				.setProperty(PROP_HTML_FOREGROUND_COLOR, sForeground);
		this.propertiesUser
				.setProperty(PROP_HTML_BACKGROUND_COLOR, sBackground);
		this.propertiesUser.setProperty(PROP_HTML_FOREGROUND_LESS,
				sForeground_less);
		this.loadUserSettingGlobals();
		this.writeUserPropertyFile();
	}

	public void updateHTTP_HREFER(String sHTTP, String sHTTPS, String sEnableSSL)
			throws IOException {
		this.propertiesUser.setProperty(PROP_HTTP_REFER, sHTTP);
		this.propertiesUser.setProperty(PROP_HTTPS_REFER, sHTTPS);
		this.propertiesUser.setProperty(PROP_ENABLE_SSL, sEnableSSL);
		this.loadUserSettingGlobals();
		this.writeUserPropertyFile();
	}

	public void updateGallery_AccessAndXMLLocation() throws IOException {
		this.propertiesGallery.setProperty(PROP_ACCESSCODE_FILE,
				Globals.GALLERY_ACCESS_CODE_LOCATION);
		this.propertiesGallery.setProperty(PROP_GALLERY_DIR,
				Globals.GALLERY_XML_DIR_LOCATION);
		// updated in admin screen
		this.propertiesGallery.setProperty(PROP_ACCESSFILES_FILE,
				Globals.GALLERY_ACCESS_FILE_LOCATION);
		this.propertiesGallery.setProperty(PROP_ACCESSUSERS_FILE,
				Globals.GALLERY_ACCESS_USERS_LOCATION);
		this.loadGalleryGlobals();
		this.writeGalleryFile();
	}

	public void updateRootDir(String sRootOriginals, String sRootWeb,
			String sRootThumbnail) throws IOException {
		this.propertiesUser
				.setProperty(PROP_ROOT_ORIGINALS_DIR, sRootOriginals);
		this.propertiesUser.setProperty(PROP_ROOT_WEB_DIR, sRootWeb);
		this.propertiesUser.setProperty(PROP_ROOT_THUMBNAILS_DIR,
				sRootThumbnail);
		this.loadUserSettingGlobals();
		this.writeUserPropertyFile();
	}

	/*
	 * //check if values have been loaded from config file as older versions do
	 * not have this variable if (this.propertiesUser.getProperty(
	 * PROP_ROOT_ORIGINALS_DIR).length() > 0 ) { Globals.ROOT_ORIGINALS_DIR =
	 * this.propertiesUser.getProperty( PROP_ROOT_ORIGINALS_DIR); } if
	 * (this.propertiesUser.getProperty( PROP_ROOT_WEB_DIR).length() > 0 ) {
	 * Globals.ROOT_WEB_DIR = this.propertiesUser.getProperty( PROP_ROOT_WEB_DIR
	 * ); } if (this.propertiesUser.getProperty(
	 * PROP_ROOT_THUMBNAILS_DIR).length() > 0 ) { Globals.ROOT_THUMBNAIL_DIR =
	 * this.propertiesUser.getProperty( PROP_ROOT_THUMBNAILS_DIR ); }
	 */

	// determine catalina.home to find out where our default
	// file lives
//	private void getCatalinaHome() {
//		String sTemp = System.getProperty("catalina.home");
//		Globals.CATALINE_HOME = sTemp;
//	}

	/*
	 * private void getSystemProperties() { String sKey = ""; Properties test =
	 * new Properties(); test = System.getProperties();
	 * 
	 * Enumeration<?> keys = test.keys();
	 * 
	 * System.out.println( "getting properties"); while (keys.hasMoreElements()
	 * ) { sKey = (String)keys.nextElement(); System.out.println(sKey +":" +
	 * test.getProperty( sKey ) ); } }
	 */
}
