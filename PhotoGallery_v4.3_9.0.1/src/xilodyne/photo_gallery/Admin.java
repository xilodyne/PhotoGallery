package xilodyne.photo_gallery;

import xilodyne.photo_gallery.access.Encrypt;
import xilodyne.photo_gallery.display.MaintenanceAccessCodes;
import xilodyne.photo_gallery.display.MaintenanceConfiguration;
import xilodyne.photo_gallery.display.RunJobs;
import xilodyne.photo_gallery.fileio.ConfigFile;
import xilodyne.photo_gallery.fileio.ReadGalleryXML;
import xilodyne.photo_gallery.globals.AccessMatrix;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.HTTPInfo;
import xilodyne.photo_gallery.http.ParameterAdmin;
import xilodyne.photo_gallery.http.ProcessHTTP;
import xilodyne.photo_gallery.threads.Queue;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author aholiday
 * 
 */
public class Admin extends HttpServlet {
	static final long serialVersionUID = 2;

	ConfigFile configFile;
	Encrypt encrypt = new Encrypt();

	// temp place until we know where to put it permanently
	// Queue queue = new Queue();

	/**
	 * If no admin cookie, redirect to admin login If admin cookie, show menu
	 * for changes
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are producing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs ServletException if a
	 *                servlet error occurs
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sError = "";
		Boolean bError = false;

		// always load config file
		try {
			configFile = new ConfigFile();
		} catch (IOException e) {
			// Logging.entry(e);
			sError = e.getMessage();
			bError = true;
		}

		HttpSession session = request.getSession(true);
		ProcessHTTP procHTTP = new ProcessHTTP(request, response);
		MaintenanceConfiguration maintConf = new MaintenanceConfiguration();
		MaintenanceAccessCodes maintAccess = new MaintenanceAccessCodes();
		RunJobs runJobs = new RunJobs();
		ParameterAdmin paramInfo = new ParameterAdmin();
		PrintWriter writer = response.getWriter();
		HTTPInfo httpInfo = new HTTPInfo();
		AssembleHTTP assembleHTTP = new AssembleHTTP();

		// if no cookie && no password in session, redirect
		paramInfo.checkParameterNames(writer, request);
		procHTTP.getCookieAdmin(paramInfo);

	//	writer.println(assembleHTTP.makeHeaderStart_HTML5());
		if (Globals.checkUserAgentHTML5()) {
		writer.println(assembleHTTP.dspHeaderStart_HTML5());
		} else {
			writer.println(assembleHTTP.dspHeaderStart_HTML4());
		}

		
		writer.println(assembleHTTP.dspHeaderEnd());
		writer.println("<div class=\"gallery-box\">\n");

		// austin
		// test
		// CheckForNewPhotos check = new CheckForNewPhotos();
		// check.getNewPhotos();
		// WriteGalleryXML writeXML = new WriteGalleryXML();
		// writeXML.checkDirectoryQueue();
		// Queue.startXMLFileWatcher();

		// WatchRastQueue rastQueue = new WatchRastQueue();
		// rastQueue.start();

		if (bError) {
			writer.println(assembleHTTP.dspMessage(sError));
		}

		// see if password is in session
		String sCode = "";
		String sEncrypt = (String) session
				.getAttribute(Globals.SESSION_ADMIN_ACCESS);
		Logging.debug(this.getClass().getName(), "Admin pass in session: "
				+ sEncrypt);
		if (sEncrypt != null)
			sCode = encrypt.decrypt(sEncrypt);

		if (!paramInfo.getFoundCookie()
				&& !(sCode.compareTo(Globals.USER_ADMIN_PASSWORD) == 0)) {
			Logging.debug(this.getClass().getName(), "No Admin cookie found.");
			response.sendRedirect(assembleHTTP
					.makeHTTPNewSessionToAdminLogin(request.getQueryString()));

			// passed login so continue to choice
		} else {
			if (Globals.USER_DEBUG) {
				Logging.debug(this.getClass().getName(), "Admin cookie found.");
				httpInfo.showAssignedValuesForAdmin(writer, paramInfo);
			}

			if (paramInfo.getConfigGotoDSPUserSettings()) {
				writer.println(maintConf.dspConfigurationSettings());

				// configuration file
			} else if (paramInfo.getConfigGotoChangeFile()) {
				writer.println(maintConf.dspConfigFileChange());
			} else if (paramInfo.getConfigFileChangeNew()) {
				// load in new data
				boolean bValidFile = false;
				try {
					configFile.readUserPropertyFile(paramInfo
							.getConfigFileChangeValue());
					bValidFile = true;
				} catch (FileNotFoundException e) {
					writer.println(assembleHTTP.dspExceptionError(e));
					Logging.error(e);
				} catch (NullPointerException e) {
					writer.println(assembleHTTP.dspExceptionError(e));
					Logging.error(e);
				}
				if (bValidFile) {
					Globals.GALLERY_USER_PROP_LOCATION = paramInfo
							.getConfigFileChangeValue();
					try {
						configFile.updateGalleryProperty(paramInfo
								.getConfigFileChangeValue());
					} catch (IOException e) {
						writer.println(assembleHTTP.dspExceptionError(e));
						Logging.error(e);
					}
					writer.println(maintConf.dspConfigurationSettings());

				} else {
					writer.println(assembleHTTP.dspMessage("File Not Found: "
							+ paramInfo.getConfigFileChangeValue()));
					writer.println(maintConf.dspConfigFileChange());

				}
			}
			// admin password
			else if (paramInfo.getConfigGogoAdminPassword()) {
				writer.println(maintConf.dspConfAdminPassword());
			} else if (paramInfo.getConfigAdminPasswordNew()) {
				if (paramInfo.getConfigAdminPasswordValue().length() == 0) {
					writer.println(assembleHTTP
							.dspMessage("Password cannot be blank."));
				} else {
					try {
						configFile.updateUser_AdminPassword(paramInfo
								.getConfigAdminPasswordValue());
						writer.println(assembleHTTP
								.dspMessage("Password change was successful."));
						writer.println(maintConf.dspConfigurationSettings());
					} catch (IOException e) {

						writer.println(assembleHTTP.dspExceptionError(e));
					}
					writer.println(maintConf.dspConfigurationSettings());

				}
			}
			// admin refer
			else if (paramInfo.getHTTPGotoHREF()) {
				writer.println(maintConf.dspHTTPRefer());
			} else if (paramInfo.getHTTPNew()) {
				// if ssl enabled, fields cannot be blank
				if (Boolean.valueOf(paramInfo.getHTTPEnableSSL())) {
					if ((paramInfo.getHTTPReferValue().length() == 0)
							|| (paramInfo.getHTTPSReferValue().length() == 0)) {
						writer.println(assembleHTTP
								.dspMessage("HTTP Refer cannot be blank"));
					}
				} else {
					configFile.updateHTTP_HREFER(paramInfo.getHTTPReferValue(),
							paramInfo.getHTTPEnableSSL(),
							paramInfo.getHTTPEnableSSL());
					writer.println(assembleHTTP
							.dspMessage("HTTP Refered updated."));
				}
				writer.println(maintConf.dspConfigurationSettings());
			}

			// admin debug
			else if (paramInfo.getConfigGotoDebug()) {
				Logging.debug(this.getClass().getName(), "debug");
				writer.println(maintConf.dspConfDebug());
			} else if (paramInfo.getConfigDebugNew()) {
				try {
					configFile.updateUser_DEBUG(
							paramInfo.getConfigDebugValue(),
							paramInfo.getConfigDebugAccessValue());
					this.updateFile(writer, "Debug settings updated.");
				} catch (IOException e) {

					writer.println(assembleHTTP.dspExceptionError(e));
					Logging.error(e);

				}
			}
			// admin html title
			else if (paramInfo.getConfigGotoHTMLTitle()) {
				writer.println(maintConf.dspConfHTMLTitle());
			} else if (paramInfo.getConfigHTMLTitleNew()) {
				try {
					configFile.updateUser_HTMLTitle(paramInfo
							.getConfigHTMLTitleValue());
					this.updateFile(writer, "Web Title Updated.");

				} catch (IOException e) {

					writer.println(assembleHTTP.dspExceptionError(e));
					Logging.error(e);

				}
			}
			// admin html color
			else if (paramInfo.getConfigGotoHTMLColor()) {
				writer.println(maintConf.dspConfHTMLColors());
			} else if (paramInfo.getConfigHTMLColorNew()) {
				// make sure values are not blank
				if ((paramInfo.getConfigHTMLFGValue().length() == 0)
						|| (paramInfo.getConfigHTMLFGLSValue().length() == 0)
						|| (paramInfo.getConfigHTMLBGValue().length() == 0)) {
					writer.println(assembleHTTP
							.dspMessage("Color settings cannot be blank."));

				} else {
					try {
						configFile.updateUser_HTMLColor(
								paramInfo.getConfigHTMLFGValue(),
								paramInfo.getConfigHTMLBGValue(),
								paramInfo.getConfigHTMLFGLSValue());
						this.updateFile(writer, "HTML Colors Updated.");
					} catch (IOException e) {
						writer.println(assembleHTTP.dspExceptionError(e));
					}
				}
			}
			// access change screen
			else if (paramInfo.getAccessDSPAccessSettings()) {
				writer.println(maintAccess.dspAccessMatrix());
			}

			// access file change
			else if (paramInfo.getAccessGalleryGotoFileChange()) {
				writer.println(maintAccess.dspAccessGalleryFileLocationChange());
			} else if (paramInfo.getAccessFileChangeNew()) {
				boolean bInvalidFiles = true;
				// load new access file
				// AccessMatrix.resetAccessMatrix();
				String sTempFile = Globals.GALLERY_ACCESS_CODE_LOCATION;
				try {

					Globals.GALLERY_ACCESS_CODE_LOCATION = paramInfo
							.getAccessFileChangeValue();
					Globals.GALLERY_XML_DIR_LOCATION = paramInfo
							.getGalleryChangeFileValue();
					Globals.GALLERY_METADATA_FILE_LOCATION = Globals.GALLERY_XML_DIR_LOCATION
							+ "/sitemap.xml";

					// change the access_user & access_file locations to
					Globals.GALLERY_ACCESS_USERS_LOCATION = (this.getFilePath(
							paramInfo.getAccessFileChangeValue(),
							ConfigFile.FILE_ACCESS_CODE) + ConfigFile.FILE_ACCESS_USERS);
					Globals.GALLERY_ACCESS_FILE_LOCATION = (this.getFilePath(
							paramInfo.getAccessFileChangeValue(),
							ConfigFile.FILE_ACCESS_CODE) + ConfigFile.FILE_ACCESS_FILES);

					procHTTP = new ProcessHTTP(request, response);
					bInvalidFiles = false;
				} catch (IOException e) {

					writer.println(assembleHTTP
							.dspMessage("Error:  cannot find access.txt file."));
					writer.println(maintAccess
							.dspAccessGalleryFileLocationChange());
					Globals.GALLERY_ACCESS_CODE_LOCATION = sTempFile;

				}

				// verify valid gallery xml files
				ReadGalleryXML readXML = new ReadGalleryXML();
				Vector<String> vFileList = readXML.getFileList();
				if (vFileList.size() > 0) {
					bInvalidFiles = false;
				} else {
					writer.println(assembleHTTP
							.dspMessage("Error:  no valid Gallery XML files."));
					writer.println(maintAccess
							.dspAccessGalleryFileLocationChange());

				}
				if (!bInvalidFiles) {
					// reload files
					try {
						configFile.updateGallery_AccessAndXMLLocation();
						Globals.setDataUnloaded();
						configFile = new ConfigFile();
						writer.println(maintAccess.dspAccessMatrix());
					} catch (IOException e) {
						writer.println(assembleHTTP.dspMessage(e
								.getLocalizedMessage()));
						writer.println(maintAccess
								.dspAccessGalleryFileLocationChange());
					}
				} else {
					writer.println(assembleHTTP
							.dspMessage("Error:  please check server log."));
					writer.println(maintAccess
							.dspAccessGalleryFileLocationChange());
				}
			}

			// xml file
			else if (paramInfo.getAccessXMLFileNew()) {
				writer.println(maintAccess.dspAccessEditXML(paramInfo
						.getAccessXMLFileValue()));
			}
			// remove xml file from access matrix
			else if (paramInfo.getAccessXMLFileDeactivate()) {
				AccessMatrix.deleteXMLFile(paramInfo.getAccessXMLFileValue());
				Globals.setCheckFileSavedTrue();
				configFile.saveAccessFiles();
				writer.println(maintAccess.dspAccessMatrix());
			} else if (paramInfo.getAccessXMLFileAssignPublic()) {
				AccessMatrix.makeXMLFilePublic(paramInfo
						.getAccessXMLFileValue());
				Globals.setCheckFileSavedTrue();
				configFile.saveAccessFiles();
				writer.println(maintAccess.dspAccessMatrix());
				// } else if (paramInfo.getAccessGotoAccessCodesChange() ) {
				// writer.println(maintAccess.dspAccessAssignUsersToFiles(paramInfo.getAccessXMLFileValue()));
			} else if (paramInfo.getAccessXMLFileAssignAccess()) {

				writer.println(maintAccess
						.dspAccessAssignUsersToFiles(paramInfo
								.getAccessXMLFileValue()));

			} else if (paramInfo.getAccessXMLFileCodeNew()) {  // assign to xmlfile codes in list
				ArrayList<String> list = paramInfo
						.getAccessXMLFileAssignValue();
				AccessMatrix.changeXMLFileAccessCodes(
						paramInfo.getAccessXMLFileValue(), list);
				Globals.setCheckFileSavedTrue();
				configFile.saveAccessFiles();
				writer.println(maintAccess.dspAccessMatrix());
			}

			else if (paramInfo.getAccessSaveFile()) {
				try {
					configFile.saveAccessFiles();
					writer.println(assembleHTTP.dspMessage("Files updated"));

				} catch (IOException e) {
					writer.println(assembleHTTP.dspExceptionError(e));
					Logging.error(e);
				}
			}

			// access users add
			else if (paramInfo.getAccessGotoUserAdd()) {
				writer.println(maintAccess.dspAcessNewUser());
			} else if (paramInfo.getAccessUserAddUserNew()) {
				if (paramInfo.getAccessUsersValue().length() == 0) {
					writer.println(assembleHTTP
							.dspMessage("Access Code cannot be blank."));
				} else {
					if (AccessMatrix.UsersContains(paramInfo
							.getAccessUsersValue())) {
						writer.println(assembleHTTP
								.dspMessage("Access Code already exists.<br><br>"));
						writer.println(maintAccess.dspAccessMatrix());

					} else {
						AccessMatrix.addUsers(paramInfo.getAccessUsersValue());
						writer.println(assembleHTTP
								.dspMessage("Access Code Added."));
						writer.println(maintAccess.dspAccessMatrix());
					}
				}
			}

			// access users
			else if (paramInfo.getAccessGotoUsersChange()) {
				writer.println(maintAccess.dspAccessEditUsers(paramInfo
						.getAccessUsersValue()));
			} else if (paramInfo.getAccessUsersNew()) {
				writer.println(maintAccess.dspAccessEditUsers(paramInfo
						.getAccessUsersValue()));
			} else if (paramInfo.getAccessUsersRemove()) {
				AccessMatrix.deleteUsers(paramInfo.getAccessUsersValue());
				writer.println(maintAccess.dspAccessMatrix());

			} else if (paramInfo.getAccessUsersGotoAssignXML()) {
				writer.println(maintAccess
						.dspAccessAssignFilesToUsers(paramInfo
								.getAccessUsersValue()));
			} else if (paramInfo.getAccessUsersXMLFileNew()) {
				ArrayList<String> XMLFileList = paramInfo
						.getAccessUsersXMLValue();
				AccessMatrix.changeAccessCodesToXMLFiles(
						paramInfo.getAccessUsersValue(), XMLFileList);
				writer.println(maintAccess.dspAccessMatrix());
			}

			// root directories
			else if (paramInfo.getRootGotoAssignDir()) {
				writer.println(maintConf.dspRootDir());

			} else if (paramInfo.getRootNewTrue()) {
				if (paramInfo.getRootOriginalsDirValue().length() == 0
						|| paramInfo.getRootWebDirValue().length() == 0
						|| paramInfo.getRootThumbnailDirValue().length() == 0) {
					writer.println(assembleHTTP
							.dspMessage("Values cannot be blank."));
				} else {
					configFile.updateRootDir(
							paramInfo.getRootOriginalsDirValue(),
							paramInfo.getRootWebDirValue(),
							paramInfo.getRootThumbnailDirValue());
					writer.println(assembleHTTP
							.dspMessage("Root directories updated."));
				}
			}

			// run jobs
			else if (paramInfo.getJobsDSPRunJobs()) {
				writer.println(runJobs.dspJobsMenu());
			} else if (paramInfo.getJobsRunSitemap()) {
				writer.println(runJobs.dspRunSitemap());
			} else if (paramInfo.getJobsDSPMonJobs()) {
				writer.println(runJobs.dspQueues());
			} else if (paramInfo.getJobsPauseJob()) {
				writer.println(runJobs.dspPauseJob(paramInfo.getJobsIDValue()));
				writer.println(runJobs.dspQueues());
			} else if (paramInfo.getJobsRestartJob()) {
				writer.println(runJobs.dspRestartJob(paramInfo.getJobsIDValue()));
				writer.println(runJobs.dspQueues());

			} else if (paramInfo.getJobsQueueChangeStatus()) {
				if (paramInfo.getJobsQueueChangeValue()) {
					Queue.setQueueRunningTRUE();
				} else {
					Queue.setQueueRunningFALSE();
				}
				writer.println(runJobs.dspQueues());
			}

		}
		writer.println(assembleHTTP.dspAdminMenu_HTML5());

		writer.println(assembleHTTP.dspGotoHome());
		writer.println("</div> <!-- gallery-box -->\n");

		writer.println(assembleHTTP.dspFooter());

	}

	private void updateFile(PrintWriter writer, String sMessage)
			throws IOException {
		AssembleHTTP assembleHTTP = new AssembleHTTP();
		MaintenanceConfiguration confSettings = new MaintenanceConfiguration();
		writer.println(assembleHTTP.dspMessage(sMessage));
		writer.println(confSettings.dspConfigurationSettings());
	}

	private String getFilePath(String sPath, String sMatchTo) {
		int iEnd = sPath.indexOf(sMatchTo);
		return (sPath.substring(0, iEnd));
	}

}
