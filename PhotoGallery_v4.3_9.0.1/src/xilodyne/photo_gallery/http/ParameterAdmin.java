package xilodyne.photo_gallery.http;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;



import javax.servlet.http.HttpServletRequest;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

/**
 * @author aholiday
 *
 */
public class ParameterAdmin {

	//admin
	public final static String PARAM_ADMIN_LOGIN="admin_login";
	public final static String PARAM_ADMIN_ACCESS_CODE="admin_access";
	public final static String PARAM_ADMIN_MENU = "admin_menu";
	public final static String PARAM_CONFIG_GOTO_DSPUSERSETTINGS = "dsp_config_settings";
	//config file
	public final static String PARAM_CONFIG_GOTO_FILE="change_config";
	public final static String PARAM_CONFIG_FILE_NEW="config_new";
	public final static String PARAM_CONFIG_FILE_VALUE="config_file_value";
	//admin password
	public final static String PARAM_CONFIG_GOTO_ADMINPASS="change_adminpass";
	public final static String PARAM_CONFIG_ADMINPASS_NEW="adminpass_new";
	public final static String PARAM_CONFIG_ADMINPASS_VALUE="adminpass_value";
	//admin debug
	public final static String PARAM_CONFIG_GOTO_DEBUG="change_debug";
	public final static String PARAM_CONFIG_DEBUG_NEW="debug_new";
	public final static String PARAM_CONFIG_DEBUG_VALUE="debug_value";
	public final static String PARAM_CONFIG_DEBUGACCESS_NEW="debugaccess_new";
	public final static String PARAM_CONFIG_DEBUGACCESS_VALUE="debugaccess_value";
	//admin html title
	public final static String PARAM_CONFIG_GOTO_HTML_TITLE="change_htmltitle";
	public final static String PARAM_CONFIG_HTMLTITLE_NEW="htmltitle_new";
	public final static String PARAM_CONFIG_HTMLTITLE_Value="htmltitle_value";
	//admin html color
	public final static String PARAM_CONFIG_GOTO_HTML_COLOR="change_htmlcolor";
	public final static String PARAM_CONFIG_HTMLCOLOR_NEW="htmlfg_new";
	public final static String PARAM_CONFIG_HTMLFG_VALUE="htmlfg_value";
	public final static String PARAM_CONFIG_HTMLFGLS_VALUE="htmlfgls_value";
	public final static String PARAM_CONFIG_HTMLBG_VALUE="htmlbg_value";
	
	//admin refer
	public final static String PARAM_HTTP_GOTO_REFER="http_refer_change";
	public final static String PARAM_HTTP_NEW="http_refer_new";
	public final static String PARAM_HTTP_REFER_VALUE="http_refer";
	public final static String PARAM_HTTPS_REFER_VALUE="https_refer";
	public final static String PARAM_HTTP_ENABLESSL_VALUE="http_enable_ssl";


	//access display
	public final static String PARAM_ACCESS_GOTO_DSPACCESSSETTINGS = "dsp_access_settings";

	//admin access codes
	public final static String PARAM_ACCESS_GOTO_FILE_CHANGE="change_accessfiles";
	

	//access file 
	public final static String PARAM_ACCESS_GOTO_FILE="change_access_file";
	public final static String PARAM_ACCESSGALLERY_FILE_NEW="access_file_new";
	public final static String PARAM_ACCESS_FILE_VALUE="access_file_value";
	//gallery dir
	public final static String PARAM_GALLERY_DIR_VALUE="gallery_dir_value";
	//access codes assign file
	public final static String PARAM_ACCESS_GOTO_ACCESSCODES="access_codes_new";
	//access codes xml
	public final static String PARAM_ACCESS_XMLFILE_NEW="access_xmlfile_new";
	public final static String PARAM_ACCESS_XMLFILE_VALUE="access_xmlfile_value";
	public final static String PARAM_ACCESS_XMLFILE_DEACTIVATE="access_xmlfile_deactivate";
	public final static String PARAM_ACCESS_XMLFILE_ASSIGNPUBLIC="access_xmlfile_assignpublic";
	public final static String PARAM_ACCESS_XMLFILE_ASSIGNACCESS="access_xmlfile_assigncoces";
	//access code users
	public final static String PARAM_ACCESS_GOTO_USERS="access_uses_change";
	public final static String PARAM_ACCESS_USERS_NEW="access_users_new";
	public final static String PARAM_ACCESS_USERS_VALUE="access_users_value";
	public final static String PARAM_ACCESS_USERS_REMOVE="access_users_remove";
	
	//access user add
	public final static String PARAM_ACCESS_GOTO_ADDUSER="access_users_goto_adduser";
	public final static String PARAM_ACCESS_USERS_ADDUSER_NEW="access_users_adduser_new";
	
	//access code change
	public final static String PARAM_ACCESS_GOTO_ASSIGNACCESS="change_access_codes";
	public final static String PARAM_ACCESS_XMLFILE_CODENEW="access_xmlfile_code_change";
	public final static String PARAM_ACCESS_XMLFILE_ASSIGNVALUE="accuser";
	
	//access user change
	public final static String PARAM_ACCESS_USERS_GOTO_ASSIGNXML="access_users_assignxml";
	public final static String PARAM_ACCESS_USERS_XMLFILENEW="access_user_xmlfile_new";
	public final static String PARAM_ACCESS_USERS_XMLVALUE="accxmlf";
	
	
	//root directory settings
	public final static String PARAM_ROOT_GOTO_ASSIGNDIR="root_goto_assign_dir";
	public final static String PARAM_ROOT_NEW="root_directory_new";
	public final static String PARAM_ROOT_ORIGINALS_DIR_VALUE="root_originals_dir";
	public final static String PARAM_ROOT_WEB_DIR_VALUE="root_web_dir";
	public final static String PARAM_ROOT_THUMBNAIL_DIR_VALUE="root_thumbnail_dir";
	
	//run jobs
	public final static String PARAM_JOBS_GOTO_DSPRUNJOBS="dsp_run_jobs";
	public final static String PARAM_JOBS_RUN_SITEMAP="run_sitemap";
	public final static String PARAM_JOBS_GOTO_DSPMONJOBS="dsp_mon_jobs";
	public final static String PARAM_JOBS_PAUSE_JOB="pause_job";
	public final static String PARAM_JOBS_RESTART_JOB="restart_job";
	
	public final static String PARAM_JOBS_ID_VALUE="job_idvalue";
	public final static String PARAM_JOBS_QUEUE_CHANGE_STATUS="set_queue_status";
	public final static String PARAM_JOBS_QUEUE_CHANGE_VALUE="set_queue_value";

	
	//access save file
	public final static String PARAM_ACCESS_SAVE_FILE="access_save_file";

	
	private boolean bDoAccessCheck;
	private boolean bDoAdminMenu;
	private boolean bDoLogin;
	private boolean bFoundCookie;
	private boolean bValidAdminAccess;

	//config display
	private boolean bConfigGotoDSPUserSettings;

	
	//config file
	private boolean bConfigGotoChangeFile;
	private boolean bConfigFileChangeNew;
	private String sConfigFileChangeValue;
	
	//config admin password
	private boolean bConfigGotoAdminPassword;
	private boolean bConfigAdminPasswordNew;
	private String sConfigAdminPasswordValue;
	
	//config admin debug
	private boolean bConfigGotoDebug;
	private boolean bConfigDebugNew;
	private String sConfigDebugValue;
	private boolean bConfigDebugAccessNew;
	private String sConfigDebugAccessValue;
	
	//config admin html title
	private boolean bConfigGotoHTMLTitle;
	private boolean bConfigHTMLTitleNew;
	private String sConfigHTMLTitleValue;
	//config admin html color
	private boolean bConfigGotoHTMLColor;
	private boolean bConfigHTMLColorNew;
	private String sConfigHTMLFGValue;
	private String sConfigHTMLFGLSValue;
	private String sConfigHTMLBGValue;
	

	
	//admin refer
	private boolean bHTTPGotoHREF;
	private boolean bHTTPNew;
	private String sHTTPReferValue;
	private String sHTTPSReferValue;
	private String sHTTPEnableSSL;

	

	//access display
	private boolean bAccessGotoDSPAccessSettings;

	//access file
	private boolean bAccessGotoFileChange;
	private boolean bAccessGalleryFileChangeNew;
	private String sAccessFileChangeValue;
	//gallery xml file
	private String sGalleryFileChangeValue;
	//access codes
	private boolean bAccessGotoAccessChange;
	private boolean bAccessXMLFileNew;
	private String sAccessXMLFileValue;
	private boolean bAccessXMLFileDeactivate;
	private boolean bAccessXMLFileAssignPublic;
	private boolean bAccessXMLFileAssignAccess;
	
	private boolean bAccessXMLFileCodeNew;

	private ArrayList<String> alAccessXMLFileValue;

	//access users add
	private boolean bAccessGotoUsersAdd;
	private boolean bAccessUsersAddUserNew;
	
	//access users
	private boolean bAccessGotoUsersChange;
	private boolean bAccessUsersNew;
	private String sAccessUsersValue;
	private boolean bAccessUsersRemove;
	private boolean bAccessUsersGotoAssignXML;
	
	//access users change
	private boolean bAccessUsersXMLFileNew;
	private ArrayList<String> alAccessUsersXMLValue;
	
	
	//root directory settings
	private boolean bRootGotoAssignDir;
	private boolean bRootNew;
	private String sRootOriginalsDirValue;
	private String sRootWebDirValue;
	private String sRootThumbnailDirValue;
	
 	
	//run jobs
	private boolean bJobsGotoDSPRunJobs;
	private boolean bJobsRunSitemap;
	private boolean bJobsGotoDSPMonJobs;
	private boolean bJobsPauseJob;
	private boolean bJobsRestartJob;
	private int iJobsPauseID;
	private boolean bJobsQueueChangeStatus;
	private boolean bJobsQueueChangeValue;
	


	//access save file
	private boolean bAccessSaveFile;
	
	private String sAccessCode;


	public ParameterAdmin() {
		
		this.bDoAccessCheck = false;
		this.bDoAdminMenu = false;
		this.bDoLogin = false;
		this.bFoundCookie = false;
		this.bValidAdminAccess = false;

		//config display
		this.bConfigGotoDSPUserSettings = false;

		//config file
		this.bConfigGotoChangeFile = false;
		this.bConfigFileChangeNew = false;
		this.sConfigFileChangeValue = "";
	
		
		//config admin password
		this.bConfigGotoAdminPassword = false;
		this.bConfigAdminPasswordNew = false;
		this.sConfigAdminPasswordValue = "";
		
		//config admin debug
		this.bConfigGotoDebug = false;
		this.bConfigDebugNew = false;
		this.sConfigDebugValue = "";
		this.bConfigDebugAccessNew = false;
		this.sConfigDebugAccessValue = "";

		//config admin html title
		this.bConfigGotoHTMLTitle = false;
		this.bConfigHTMLTitleNew = false;
		this.sConfigHTMLTitleValue = "";
		//config admin html color
		this.bConfigGotoHTMLColor = false;
		this.bConfigHTMLColorNew = false;
		this.sConfigHTMLFGValue = "";
		this.sConfigHTMLFGLSValue = "";
		this.sConfigHTMLBGValue = "";
		
		//admin refer
	
		this.bHTTPGotoHREF = false;
		this.bHTTPNew = false;
		this.sHTTPReferValue = "";
		this.sHTTPSReferValue = "";
		this.sHTTPEnableSSL = "";

		//access display
		this.bAccessGotoDSPAccessSettings = false;

		
		//access codes
		this.bAccessGotoAccessChange = false;
		this.bAccessXMLFileNew = false;
		this.sAccessXMLFileValue = "";
		this.bAccessXMLFileDeactivate = false;
		this.bAccessXMLFileAssignPublic = false;
		this.bAccessXMLFileAssignAccess = false;
		this.bAccessXMLFileCodeNew = false;
		this.alAccessXMLFileValue = new ArrayList<String>();

		//access users add
		//access users add
		this.bAccessGotoUsersAdd = false;
		this.bAccessUsersAddUserNew = false;

		//access users
		this.bAccessGotoUsersChange = false;
		this.bAccessUsersNew = false;
		this.sAccessUsersValue = "";
		this.bAccessUsersRemove = false;
		this.bAccessUsersGotoAssignXML = false;
		this.alAccessUsersXMLValue = new ArrayList<String>();
		//access save file
		this.bAccessSaveFile = false;
		
		
		//root directory settings
		this.bRootGotoAssignDir = false;
		this.bRootNew = false;
		this.sRootOriginalsDirValue = "";
		this.sRootWebDirValue = "";
		this.sRootThumbnailDirValue = "";
		

		
		//runjobs
		this.bJobsGotoDSPRunJobs = false;
		this.bJobsRunSitemap = false;
		this.bJobsGotoDSPMonJobs = false;
		this.bJobsPauseJob = false;
		this.bJobsRestartJob = false;
		this.iJobsPauseID = 0;
		this.bJobsQueueChangeValue = false;

		
		this.sAccessCode = "";
	}





	public void setAccessCode(String sCode) {
		this.sAccessCode = sCode.trim();
	}

	public String getAccessCode() {
		return this.sAccessCode;
	}



	public boolean getDoAccessCheck() {
		return this.bDoAccessCheck;
	}

	public void setDoAccessCheckTrue() {
		this.bDoAccessCheck = true;
	}

	public void setDoAccessCheckFalse() {
		this.bDoAccessCheck = false;
	}



	public void setDoAdminMenuTrue() {
		this.bDoAdminMenu = true;
	}
	
	public boolean getDoAdminMenu() {
		return this.bDoAdminMenu;
	}
	
	

	public void setDoLoginTrue() {
		this.bDoLogin = true;
	}

	public void setDoLoginFalse() {
		this.bDoLogin = false;
	}

	public boolean getDoLogin() {
		return this.bDoLogin;
	}


	public void setFoundCookie(boolean value) {
		this.bFoundCookie = value;
	}

	public boolean getFoundCookie() {
		return this.bFoundCookie;
	}



	public void setValidAdminAccessTrue() {
		this.bValidAdminAccess = true;
	}

	public boolean getValidAdminAccess() {
		return this.bValidAdminAccess;
	}


	
	
	
	public void setConfigGotoDSPUserSettingsTrue() {
		this.bConfigGotoDSPUserSettings = true;
	}
	
	public boolean getConfigGotoDSPUserSettings() {
		return this.bConfigGotoDSPUserSettings;
	}
	
	
	
	//config file
	public void setConfigGotoChangeFileTrue() {
		this.bConfigGotoChangeFile = true;
	}
	public boolean getConfigGotoChangeFile() {
		return this.bConfigGotoChangeFile;
	}
	
	
	public void setConfigFileChangeNewTrue() {
		this.bConfigFileChangeNew = true;
	}
	public boolean getConfigFileChangeNew() {
		return this.bConfigFileChangeNew;
	}
	
	
	public void setConfigFileChangeValue(String sNewValue) {
		this.sConfigFileChangeValue = sNewValue;
	}
	public String getConfigFileChangeValue() {
		return this.sConfigFileChangeValue;
	}
	
	
	//config admin
	public void setConfigGotoAdminPasswordTrue() {
		this.bConfigGotoAdminPassword = true;
	}
	public boolean getConfigGogoAdminPassword(){
		return this.bConfigGotoAdminPassword;
	}
	
	public void setConfigAdminPasswordNewTrue() {
		this.bConfigAdminPasswordNew = true;
	}
	public boolean getConfigAdminPasswordNew() {
		return this.bConfigAdminPasswordNew;
	}
	
	public void setConfigAdminPasswordValue( String sNewValue ) {
		this.sConfigAdminPasswordValue = sNewValue;
	}
	public String getConfigAdminPasswordValue() {
		return this.sConfigAdminPasswordValue;
	}
	
	
	
	
	//admin debug
	public void setConfigGotoDebugTrue() {
		this.bConfigGotoDebug = true;
	}
	public boolean getConfigGotoDebug() {
		return this.bConfigGotoDebug;
	}
	
	public void setConfigDebugNewTrue() {
		this.bConfigDebugNew = true;
	}
	public boolean getConfigDebugNew() {
		return this.bConfigDebugNew;
	}
	
	public void setConfigDebugValue(String sDebug) {
		this.sConfigDebugValue = sDebug;
	}
	public String getConfigDebugValue() {
		return this.sConfigDebugValue;
	}
	
	public void setConfigDebugAccessNewTrue() {
		this.bConfigDebugAccessNew = true;
	}
	public boolean getConfigDebugAccessNew() {
		return this.bConfigDebugAccessNew;
	}
	
	public void setConfigDebugAccessValue( String sDebugAccess ) {
		this.sConfigDebugAccessValue = sDebugAccess;
	}
	public String getConfigDebugAccessValue() {
		return this.sConfigDebugAccessValue;
	}
	
	
	
	//config admin html title
	public void setConfigGotoHTMLTitleTrue() {
		this.bConfigGotoHTMLTitle = true;
	}
	
	public boolean getConfigGotoHTMLTitle() {
		return this.bConfigGotoHTMLTitle;
	}
	
	public void setConfigHTMLTitleNewTrue() {
		this.bConfigHTMLTitleNew = true;
	}
	public boolean getConfigHTMLTitleNew() {
		return this.bConfigHTMLTitleNew;
	}
	
	public void setConfigHTMLTitleValue(String sTitle) {
		this.sConfigHTMLTitleValue = sTitle;
	}
	public String getConfigHTMLTitleValue(){
		return this.sConfigHTMLTitleValue;
	}
	
	
	//config admin html color
	public void setConfigGotoHTMLColorTrue() {
		this.bConfigGotoHTMLColor = true;
	}
	public boolean getConfigGotoHTMLColor() {
		return this.bConfigGotoHTMLColor;
	}
	
	public void setConfigHTMLColorNewTrue() {
		this.bConfigHTMLColorNew = true;
	}
	public boolean getConfigHTMLColorNew() {
		return this.bConfigHTMLColorNew;
	}
	
	public void setConfigHTMLFGValue (String sFG) {
		this.sConfigHTMLFGValue = sFG;
	}
	public String getConfigHTMLFGValue() {
		return this.sConfigHTMLFGValue;
	}
	
	public void setConfigHTMLFGLSValue (String sFGLS) {
		this.sConfigHTMLFGLSValue = sFGLS;
	}
	public String getConfigHTMLFGLSValue() {
		return this.sConfigHTMLFGLSValue;
	}

	public void setConfigHTMLBGValue(String sBG) {
		this.sConfigHTMLBGValue = sBG;
	}
	public String getConfigHTMLBGValue() {
		return this.sConfigHTMLBGValue;
	}
	
	
	//admin refer
	
	public void setHTTPGotoHREFTrue() {
		this.bHTTPGotoHREF = true;
	}
	public boolean getHTTPGotoHREF() {
		return this.bHTTPGotoHREF;
	}
	
	public void setHTTPNewTrue() {
		this.bHTTPNew = true;
	}
	public boolean getHTTPNew(){
		return this.bHTTPNew;
	}
	
	public void setHTTPReferValue(String sValue) {
		this.sHTTPReferValue = sValue;
	}
	public String getHTTPReferValue() {
		return this.sHTTPReferValue;
	}
	
	public void setHTTPSReferValue(String sValue){
		this.sHTTPSReferValue = sValue;
	}
	public String getHTTPSReferValue(){
		return this.sHTTPSReferValue;
	}
	
	public void setHTTPEnableSSL(String sValue) {
		this.sHTTPEnableSSL = sValue;
	}
	public String getHTTPEnableSSL() {
		return this.sHTTPEnableSSL;
	}
	//access display
	public void setAccessDSPAccessSettingsTrue(){
		this.bAccessGotoDSPAccessSettings = true;
	}
	public boolean getAccessDSPAccessSettings(){
		return this.bAccessGotoDSPAccessSettings;
	}
	
	//access file 
	public void setAccessGalleryGotoFileChangeTrue(){
		this.bAccessGotoFileChange = true;
	}
	public boolean getAccessGalleryGotoFileChange() {
		return this.bAccessGotoFileChange;
	}
	
	public void setAccessFileChangeNewTrue(){
		this.bAccessGalleryFileChangeNew = true;
	}
	public boolean getAccessFileChangeNew(){
		return this.bAccessGalleryFileChangeNew;
	}
	
	public void setAccessFileChangeValue( String sNewFile ) {
		this.sAccessFileChangeValue = sNewFile;
	}
	public String getAccessFileChangeValue(){
		return this.sAccessFileChangeValue;
	}
	

	//gallery xml file
	public void setGalleryChangeFileValue (String sDirName) {
		this.sGalleryFileChangeValue = sDirName;
	}
	public String getGalleryChangeFileValue () {
		return this.sGalleryFileChangeValue;
	}
	
	
	//access codes
	public void setAccessGotoAccessChangeTrue() {
		this.bAccessGotoAccessChange = true;
	}
	public boolean getAccessGotoAccessChange(){
		return this.bAccessGotoAccessChange;
	}
	
	//access xml file
	public void setAccessXMLFileNewTrue(){
		this.bAccessXMLFileNew = true;
	}
	public boolean getAccessXMLFileNew() {
		return this.bAccessXMLFileNew;
	}
	
	public void setAccessXMLFileValue (String sFile) {
		this.sAccessXMLFileValue = sFile;
	}
	public String getAccessXMLFileValue () {
		return this.sAccessXMLFileValue;
	}
	
	public void setAccessXMLFileDeactivateTrue(){
		this.bAccessXMLFileDeactivate = true;
	}
	public boolean getAccessXMLFileDeactivate() {
		return this.bAccessXMLFileDeactivate;
	}
	
	public void setAccessXMLFileAssignPublicTrue() {
		this.bAccessXMLFileAssignPublic = true;
	}
	public boolean getAccessXMLFileAssignPublic() {
		return this.bAccessXMLFileAssignPublic;
	}
	
	public void setAccessXMLFileAssignAccessTrue () {
		this.bAccessXMLFileAssignAccess = true;
	}
	public boolean getAccessXMLFileAssignAccess () {
		return this.bAccessXMLFileAssignAccess;
	}
	
	
	
	public void setAccessXMLFileCodeNewTrue() {
		this.bAccessXMLFileCodeNew = true;
	}
	public boolean getAccessXMLFileCodeNew() {
		return this.bAccessXMLFileCodeNew;
	}
	
	

	public void setAccessXMLFileAssignValue( String sFile) {
		this.alAccessXMLFileValue.add( sFile );
	}

	public ArrayList<String> getAccessXMLFileAssignValue()
	{
		return alAccessXMLFileValue;
	}
	
	
	//access users add
	public void setAccessGotoUserAddTrue() {
		this.bAccessGotoUsersAdd = true;
	}
	public boolean getAccessGotoUserAdd() {
		return this.bAccessGotoUsersAdd;
	}
	
	public void setAccessUsersAddUserNewTrue(){
		this.bAccessUsersAddUserNew = true;
	}
	public boolean getAccessUserAddUserNew() {
		return this.bAccessUsersAddUserNew;
	}

	//access users

	public void setAccessGotoUsersChangeTrue() {
		this.bAccessGotoUsersChange = true;
	}
	public boolean getAccessGotoUsersChange() {
		return this.bAccessGotoUsersChange;
	}
	
	public void setAccessUsersNewTrue(){
		this.bAccessUsersNew = true;
	}
	public boolean getAccessUsersNew(){
		return this.bAccessUsersNew;
	}
	
	public void setAccessUsersValue(String sUsers) {
		this.sAccessUsersValue = sUsers;
	}
	public String getAccessUsersValue() {
		return this.sAccessUsersValue;
	}
	
	public void setAccessUsersRemoveTrue() {
		this.bAccessUsersRemove = true;
	}
	
	public boolean getAccessUsersRemove(){
		return this.bAccessUsersRemove;
	}

	public void setAccessUsersGotoAssignXMLTrue(){
		this.bAccessUsersGotoAssignXML = true;
	}
	public boolean getAccessUsersGotoAssignXML() {
		return this.bAccessUsersGotoAssignXML;
	}

	
	//assign users xml
	public void setAccessUsersXMLFileNewTrue() {
		this.bAccessUsersXMLFileNew = true;
	}
	public boolean getAccessUsersXMLFileNew() {
		return this.bAccessUsersXMLFileNew;
	}
	
	
	public void setAccessUsersXMLValue(String sXMLFile){
		this.alAccessUsersXMLValue.add( sXMLFile );
	}
	public ArrayList<String> getAccessUsersXMLValue() {
		return this.alAccessUsersXMLValue;
	}
	
	//access save file
	public void setAccessSaveFileTrue() {
		this.bAccessSaveFile = true;
	}
	public boolean getAccessSaveFile() {
		return this.bAccessSaveFile;
	}
	
	
	//root directory settings
/*	private boolean bRootGotoAssignDir;
	private boolean bRootNew;
	private boolean bRootOriginalsDirValue;
	private boolean bRootWebDirValue;
	private boolean bRootThumbnailDirValue;
*/
	public void setRootGotoAssignDirTrue(){
		this.bRootGotoAssignDir = true;
	}
	public boolean getRootGotoAssignDir() {
		return this.bRootGotoAssignDir;
	}
	public void setRootNewTrue() {
		this.bRootNew = true;
	}
	public boolean getRootNewTrue() {
		return this.bRootNew;
	}
	public void setRootOriginalsDirValue (String sOriginals) {
		this.sRootOriginalsDirValue = sOriginals;
	}
	public String getRootOriginalsDirValue() {
		return this.sRootOriginalsDirValue;
	}
	public void setRootWebDirValue (String sWeb) {
		this.sRootWebDirValue = sWeb;
	}
	public String getRootWebDirValue() {
		return this.sRootWebDirValue;
	}
	public void setRootThumbnailDirValue( String sThumbnail ) {
		this.sRootThumbnailDirValue = sThumbnail;
	}
	public String getRootThumbnailDirValue() {
		return this.sRootThumbnailDirValue;
	}
	
	
	
	
	
	//run jobs
	public void setJobsDSPRunJobsTrue(){
		this.bJobsGotoDSPRunJobs = true;
	}
	public boolean getJobsDSPRunJobs(){
		return this.bJobsGotoDSPRunJobs;
	}
	
	public void setJobsRunSitemapTrue() {
		this.bJobsRunSitemap = true;
	}
	public boolean getJobsRunSitemap() {
		return this.bJobsRunSitemap;
	}
	
	public void setJobsDSPMonJobsTrue() {
		this.bJobsGotoDSPMonJobs = true;
	}
	public boolean getJobsDSPMonJobs() {
		return this.bJobsGotoDSPMonJobs;
	}
	
	public void setJobsPauseJobTrue() {
		this.bJobsPauseJob = true;
	}
	public boolean getJobsPauseJob() {
		return this.bJobsPauseJob;
	}
	
	public void setJobsRestartJobTrue() {
		this.bJobsRestartJob = true;
	}
	public boolean getJobsRestartJob() {
		return this.bJobsRestartJob;
	}
	
	public void setJobsIDValue (int iTemp ) {
		this.iJobsPauseID = iTemp;
	}
	public int getJobsIDValue() {
		return this.iJobsPauseID;
	}
	
	public void setJobsQueueChangeValueTrue () {
		this.bJobsQueueChangeValue = true;
	}
	public void setJobsQueueChangeValueFalse() {
		this.bJobsQueueChangeValue = false;
	}
	public  boolean getJobsQueueChangeValue() {
		return this.bJobsQueueChangeValue;
	}
	
	public void setJobsQueueChangeStatusTrue() {
		this.bJobsQueueChangeStatus = true;
	}
	public boolean getJobsQueueChangeStatus() {
		return this.bJobsQueueChangeStatus;
	}
	
	public void checkParameterNames(PrintWriter writer, HttpServletRequest request) {
		this.alAccessXMLFileValue.clear();
		
		Enumeration<?> param = request.getParameterNames();
		while (param.hasMoreElements()) {
			String name = (String) param.nextElement();
			String sNamePrefix = "";
			//System.out.println("looking at:"  + name );
			
			if (name.length() >6 ) {
				sNamePrefix = name.substring(0,7);
			}
			
			if (Globals.USER_DEBUG) {
				writer.println("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR
						+ "\">param: " + name + "<br></font>");
			}

			if (name.compareTo(PARAM_ADMIN_LOGIN) == 0) {
				this.setDoLoginTrue();
			}
			
			if (name.compareTo(PARAM_ADMIN_ACCESS_CODE) == 0) {
				this.setAccessCode(request.getParameter(name));
				this.setDoAccessCheckTrue();

				if (this.checkValidAccessCode( this.getAccessCode() )) {
					this.setValidAdminAccessTrue();
				}

				if (Globals.USER_DEBUG) {
					writer.println("<font color=\""
							+ Globals.USER_HTML_FOREGROUND_COLOR
							+ "\">Login:  access code: " + this.getAccessCode()
							+ "<br></font>");
				}
			}
			
			if (name.compareTo(PARAM_ADMIN_MENU) == 0) {
				this.setDoAdminMenuTrue();
			}
			
			
			if (name.compareTo(PARAM_CONFIG_GOTO_DSPUSERSETTINGS) == 0 ) {
				this.setConfigGotoDSPUserSettingsTrue();
			}
			
			//config file
			if (name.compareTo( PARAM_CONFIG_GOTO_FILE ) == 0 ) {
				this.setConfigGotoChangeFileTrue();
			}	
			
			if (name.compareTo( PARAM_CONFIG_FILE_NEW ) == 0 ) {
				this.setConfigFileChangeNewTrue();
			}
			
			if (name.compareTo( PARAM_CONFIG_FILE_VALUE ) == 0 ) {
				this.setConfigFileChangeValue( request.getParameter(name));	
			}
			
			
			//admin password
			if (name.compareTo( PARAM_CONFIG_GOTO_ADMINPASS) == 0 ){
				this.setConfigGotoAdminPasswordTrue();
			}
			
			if (name.compareTo( PARAM_CONFIG_ADMINPASS_NEW) == 0 ) {
				this.setConfigAdminPasswordNewTrue();
			}
			
			if (name.compareTo( PARAM_CONFIG_ADMINPASS_VALUE ) == 0 ) {
				this.setConfigAdminPasswordValue( request.getParameter(name));
			}
			
			//admin debug
			if (name.compareTo( PARAM_CONFIG_GOTO_DEBUG ) == 0 ) {
				this.setConfigGotoDebugTrue();
			}
			if (name.compareTo( PARAM_CONFIG_DEBUG_NEW ) == 0 ){
				this.setConfigDebugNewTrue();
			}
			if (name.compareTo( PARAM_CONFIG_DEBUG_VALUE ) == 0 ) {
				this.setConfigDebugValue(request.getParameter(name));
			}
			if (name.compareTo( PARAM_CONFIG_DEBUGACCESS_NEW) == 0 ) {
				this.setConfigDebugAccessNewTrue();
			}
			if (name.compareTo( PARAM_CONFIG_DEBUGACCESS_VALUE ) == 0 ) {
				this.setConfigDebugAccessValue( request.getParameter(name));
			}
			
			

			//admin html title
			if (name.compareTo( PARAM_CONFIG_GOTO_HTML_TITLE) == 0 ) {
				this.setConfigGotoHTMLTitleTrue();
			}
			if (name.compareTo( PARAM_CONFIG_HTMLTITLE_NEW ) == 0 ) {
				this.setConfigHTMLTitleNewTrue();
			}
			if (name.compareTo( PARAM_CONFIG_HTMLTITLE_Value ) == 0) {
				this.setConfigHTMLTitleValue( request.getParameter( name ));
			}
			//admin html color
			if (name.compareTo( PARAM_CONFIG_GOTO_HTML_COLOR) == 0 ) {
				this.setConfigGotoHTMLColorTrue();
			}
			if (name.compareTo( PARAM_CONFIG_HTMLCOLOR_NEW) == 0 ) {
				this.setConfigHTMLColorNewTrue();
			}
			if (name.compareTo( PARAM_CONFIG_HTMLFG_VALUE) == 0 ) {
				this.setConfigHTMLFGValue( request.getParameter( name ));
			}
			if (name.compareTo( PARAM_CONFIG_HTMLFGLS_VALUE) == 0 ) {
				this.setConfigHTMLFGLSValue( request.getParameter( name ));
			}
			if (name.compareTo( PARAM_CONFIG_HTMLBG_VALUE) == 0 ) {
				this.setConfigHTMLBGValue( request.getParameter(name));
			}


			//admin refer
			if (name.equals( PARAM_HTTP_GOTO_REFER)) {
				this.setHTTPGotoHREFTrue();
			}
			if (name.equals( PARAM_HTTP_NEW )) {
				this.setHTTPNewTrue();
			}
			if (name.equals( PARAM_HTTP_REFER_VALUE)) {
				this.setHTTPReferValue( request.getParameter( name ));
			}
			if (name.equals( PARAM_HTTPS_REFER_VALUE )) {
				this.setHTTPSReferValue( request.getParameter( name ));
			}
			if (name.equals( PARAM_HTTP_ENABLESSL_VALUE )) {
				this.setHTTPEnableSSL( request.getParameter( name ));
			}
			//access display
			if (name.compareTo( PARAM_ACCESS_GOTO_DSPACCESSSETTINGS) == 0) {
				this.setAccessDSPAccessSettingsTrue();
			}
			//access file
			if (name.compareTo( PARAM_ACCESS_GOTO_FILE_CHANGE) == 0)  {
				this.setAccessGalleryGotoFileChangeTrue();
			}
			if (name.compareTo( PARAM_ACCESSGALLERY_FILE_NEW ) == 0 ) {
				this.setAccessFileChangeNewTrue();
			}
			if (name.compareTo( PARAM_ACCESS_FILE_VALUE ) == 0 ) {
				this.setAccessFileChangeValue( request.getParameter(name));
			}
			
			//gallery file
			if (name.compareTo( PARAM_GALLERY_DIR_VALUE) == 0 ) {
				this.setGalleryChangeFileValue( request.getParameter(name));
			}
			
			//access codes
			if (name.compareTo( PARAM_ACCESS_GOTO_ACCESSCODES ) == 0 ) {
				this.setAccessGotoAccessChangeTrue();
			}
			
			//access xml file
			if (name.compareTo( PARAM_ACCESS_XMLFILE_NEW ) == 0 ) {
				this.setAccessXMLFileNewTrue();
			}
			if (name.compareTo( PARAM_ACCESS_XMLFILE_VALUE ) == 0 ) {
				this.setAccessXMLFileValue( request.getParameter(name));
			}
			
			if (name.compareTo( PARAM_ACCESS_XMLFILE_DEACTIVATE) == 0) {
				this.setAccessXMLFileDeactivateTrue();
			}
			if (name.compareTo( PARAM_ACCESS_XMLFILE_ASSIGNPUBLIC ) ==0 ) {
				this.setAccessXMLFileAssignPublicTrue();
			}
			if (name.compareTo( PARAM_ACCESS_XMLFILE_ASSIGNACCESS) == 0) {
				this.setAccessXMLFileAssignAccessTrue();
			}
			if (name.equals( PARAM_ACCESS_XMLFILE_CODENEW)) {
				this.setAccessXMLFileCodeNewTrue();
			}
			if (sNamePrefix.compareTo( PARAM_ACCESS_XMLFILE_ASSIGNVALUE ) ==0 ) {
			//if (name.equals( PARAM_ACCESS_XMLFILE_ASSIGNVALUE)){
				this.setAccessXMLFileAssignValue( request.getParameter(name ));
			}
			

			//access users add
			if (name.equals( PARAM_ACCESS_GOTO_ADDUSER)) {
				this.setAccessGotoUserAddTrue();
			}
			if (name.equals( PARAM_ACCESS_USERS_ADDUSER_NEW )) {
				this.setAccessUsersAddUserNewTrue();
			}
			
			

			//access users
			if (name.equals( PARAM_ACCESS_GOTO_USERS)) {
				this.setAccessGotoUsersChangeTrue();
			}
			if (name.equals( PARAM_ACCESS_USERS_NEW)){
				this.setAccessUsersNewTrue();
			}
			if (name.equals( PARAM_ACCESS_USERS_VALUE)){
				this.setAccessUsersValue( request.getParameter(name));
			}
			if (name.equals( PARAM_ACCESS_USERS_REMOVE)){
				this.setAccessUsersRemoveTrue();
			}
			if (name.equals( PARAM_ACCESS_USERS_GOTO_ASSIGNXML)) {
				this.setAccessUsersGotoAssignXMLTrue();			
			}
			if (name.equals( PARAM_ACCESS_USERS_XMLFILENEW)) {
				this.setAccessUsersXMLFileNewTrue();
			}

			if (sNamePrefix.compareTo( PARAM_ACCESS_USERS_XMLVALUE ) ==0 ) {
				this.setAccessUsersXMLValue( request.getParameter(name ));
			}
			
			//root directory
			if (name.compareTo( PARAM_ROOT_GOTO_ASSIGNDIR) == 0 ) {
				this.setRootGotoAssignDirTrue();
			}
			if (name.compareTo( PARAM_ROOT_NEW) == 0 ) {
				this.setRootNewTrue();
			}
			if (name.compareTo( PARAM_ROOT_ORIGINALS_DIR_VALUE) == 0 ) {
				this.setRootOriginalsDirValue( request.getParameter(name));
			}
			if (name.compareTo( PARAM_ROOT_WEB_DIR_VALUE) == 0 ) {
				this.setRootWebDirValue( request.getParameter( name ));
			}
			if (name.compareTo( PARAM_ROOT_THUMBNAIL_DIR_VALUE) == 0 ) {
				this.setRootThumbnailDirValue( request.getParameter( name ));
			}
			
			
			
			//run jobs
			if (name.compareTo( PARAM_JOBS_GOTO_DSPRUNJOBS) == 0) {
				this.setJobsDSPRunJobsTrue();
			}
			if (name.compareTo( PARAM_JOBS_RUN_SITEMAP) == 0 ) {
				this.setJobsRunSitemapTrue();
			}
			if (name.compareTo( PARAM_JOBS_GOTO_DSPMONJOBS) == 0 ) {
				this.setJobsDSPMonJobsTrue();
			}
			if (name.compareTo( PARAM_JOBS_PAUSE_JOB) == 0 ) {
				this.setJobsPauseJobTrue();
			}
			if (name.compareTo( PARAM_JOBS_RESTART_JOB) == 0 ) {
				this.setJobsRestartJobTrue();
			}
			if (name.compareTo( PARAM_JOBS_ID_VALUE) == 0 ) {
				this.setJobsIDValue( Integer.valueOf( request.getParameter(name )));	
				Logging.info(this.getClass().getName(), "Job: " + this.getJobsIDValue() );
			}
			if (name.compareTo( PARAM_JOBS_QUEUE_CHANGE_STATUS) == 0 ) { 
				this.setJobsQueueChangeStatusTrue();
				
			}
			if (name.compareTo( ParameterAdmin.PARAM_JOBS_QUEUE_CHANGE_VALUE ) == 0 ) {
				boolean bValue = Boolean.valueOf(request.getParameter(name));
				if (bValue) {
					this.setJobsQueueChangeValueTrue();
				} else {
					this.setJobsQueueChangeValueFalse();
				}
			}
			
		
			//access save file
			if (name.equals( PARAM_ACCESS_SAVE_FILE )) {
				this.setAccessSaveFileTrue();
			}
				
		}
	}


	private boolean checkValidAccessCode(String sCode) {
		boolean bValid = false;

		if (Globals.USER_DEBUG ) {
			Logging.info(this.getClass().getName(), "Checking code:  param code: " + sCode + " password: " + Globals.USER_ADMIN_PASSWORD );
		}
		
		if (sCode.compareTo( Globals.USER_ADMIN_PASSWORD) == 0 ) {
			bValid = true;
		} 

		return bValid;
	}



}
