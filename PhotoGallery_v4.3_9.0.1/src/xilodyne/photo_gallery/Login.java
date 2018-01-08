package xilodyne.photo_gallery;

import xilodyne.photo_gallery.access.AccessCodes;
import xilodyne.photo_gallery.access.Encrypt;
import xilodyne.photo_gallery.fileio.ConfigFile;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.HTTPInfo;
import xilodyne.photo_gallery.http.ImageCookie;
import xilodyne.photo_gallery.http.ParameterAdmin;
import xilodyne.photo_gallery.http.ParameterPhoto;
import xilodyne.photo_gallery.http.ProcessHTTP;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author aholiday
 *
 */
public class Login extends HttpServlet {
	static final long serialVersionUID = 1;
	ConfigFile configFile;
	AccessCodes accessCodes = new AccessCodes();
	Encrypt encrypt = new Encrypt();
	ImageCookie imageCookie = new ImageCookie();

	// ask for access code
	// verify access code is good for path
	// send back to orginating webpage

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sError = "";
		Boolean bError = false;

		try {
			configFile = new ConfigFile();
		} catch (IOException e) {
			sError = e.getMessage();
			bError = true;
		}

		AssembleHTTP assembleHTTP = new AssembleHTTP();
		HTTPInfo httpInfo = new HTTPInfo();
		ParameterPhoto paramPhoto = new ParameterPhoto();
		ParameterAdmin paramAdmin = new ParameterAdmin();
		PrintWriter writer = response.getWriter();

		response.setContentType("text/html");
		response.setHeader("pragma", "no-cache");

		HttpSession session = request.getSession(true);

		// writer.println(assembleHTTP.makeHeaderStart_HTML5());
		if (Globals.checkUserAgentHTML5()) {
			writer.println(assembleHTTP.dspHeaderStart_HTML5());
		} else {
			writer.println(assembleHTTP.dspHeaderStart_HTML4());
		}

		if (bError) {
			writer.println(assembleHTTP.dspHeaderEnd());
			writer.println(assembleHTTP.dspMessage(sError));
		}

		paramPhoto.checkParameterNames(request, response);
		// paramPhoto.checkParameterNames(writer, request);
		paramAdmin.checkParameterNames(writer, request);

		
		if (Globals.USER_DEBUG) {
			writer.println("<font color=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR
					+ "\">Login POST:  access loaded: "
					+ Globals.checkDataLoaded() + "<br></font>");
			writer.println("<font color=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR
					+ "\">Login POST:  access code: "
					+ paramPhoto.getAccessCode() + "<br></font>");

			httpInfo.showAssignedValuesForLogin(writer, paramPhoto);
			AccessCodes.printAccessMatrix(writer);
		}
	 
		boolean bValidAccessCode = false;
		boolean bValidThumbnailFile = false;

		// check first if we are handling admin tasks, then check photo tasks
		if (paramAdmin.getDoAccessCheck()) {
			writer.println("<div class=\"gallery-box\">\n");

			// if valid code, and send on to admin servlet, else display error
			if (paramAdmin.getValidAdminAccess()) {
				imageCookie.createCookie(response, paramAdmin.getAccessCode(),
						Globals.COOKIE_ADMIN);
				session.setAttribute(Globals.SESSION_ADMIN_ACCESS,
						encrypt.encrypt(paramPhoto.getAccessCode()));

				response.sendRedirect(assembleHTTP.makeHTTPAdminHomeFromLogin());
			} else {
				writer.println(assembleHTTP.dspHeaderEnd());
					writer.println(assembleHTTP.dspWrongAccessCode_HTML5());
				writer.println(assembleHTTP.dspLogin_Admin_HTML5());
				writer.println(assembleHTTP.dspLogin_FormAdmin_HTML5());

			}
		} else {

			if (paramPhoto.getDoAccessCheck()) {
				// if archive login, else general login

				this.log("Access: " + paramPhoto.getAccessCode() + ":"
						+ request.getLocalAddr());
				if (paramPhoto.getArchiveLogin() || paramPhoto.getDoImage()) {
					bValidAccessCode = accessCodes.validAccessCode(writer,
							paramPhoto.getAccessCode());
					bValidThumbnailFile = accessCodes.validThumbnailFile(
							writer, paramPhoto.getAccessCode(),
							paramPhoto.getXMLFile());
				} else {
					bValidAccessCode = accessCodes.validAccessCode(writer,
							paramPhoto.getAccessCode());
				}

				if (Globals.USER_DEBUG) {
					writer.println("<font color=\""
							+ Globals.USER_HTML_FOREGROUND_COLOR
							+ "\">Login POST: ValidAccessCode: "
							+ bValidAccessCode + "<br></font>");
				}

			}

			if (bValidAccessCode) {

				imageCookie.createCookie(response, paramPhoto.getAccessCode(),
						Globals.COOKIE_PHOTO);
				this.log("Access: " + paramPhoto.getAccessCode() + ":"
						+ request.getLocalAddr());

				session.setAttribute(Globals.SESSION_PHOTO_ACCESS,
						encrypt.encrypt(paramPhoto.getAccessCode()));

				if (paramPhoto.getArchiveLogin() && bValidThumbnailFile) {
					response.sendRedirect(assembleHTTP
							.makeHTTPGalleryHome(paramPhoto.getXMLFile()));
				} else if (paramPhoto.getDoImage() && bValidThumbnailFile) {
					response.sendRedirect(assembleHTTP.makeHTTPImageHomeUsingXML(
							paramPhoto.getXMLFile(), paramPhoto.getImageFile()));
				} else {
					response.sendRedirect(assembleHTTP.makeHTTPArchivesHome());
				}
				response.flushBuffer();
			} else {
				writer.println(assembleHTTP.dspHeaderEnd());
				if (Globals.checkUserAgentHTML5()) {
					writer.println("<div class=\"gallery-box\">\n");
				}
				// login failed, show login depending on where it came from
				this.log("Access failure: " + paramPhoto.getAccessCode() + ":"
						+ request.getLocalAddr());
				if (Globals.checkUserAgentHTML5()) {
					writer.println("<div class=\"gallery-box\">\n");
					writer.println(assembleHTTP.dspWrongAccessCode_HTML5());
				} else {
					writer.println(assembleHTTP.dspWrongAccessCode_HTML4());
				}

				if (paramPhoto.getArchiveLogin()) {
					if (Globals.checkUserAgentHTML5()) {
						writer.println(assembleHTTP.dspWrongAccessCode_HTML5());
						writer.println(assembleHTTP.dspLoginArchive_HTML5());
						writer.println(assembleHTTP.dspLogin_FormGallery_HTML5(paramPhoto
								.getXMLFile()));
					} else {
						writer.println(assembleHTTP.dspLoginArchive_HTML4(paramPhoto
							.getXMLFile()));
					}
				} else if (paramPhoto.getDoImage()) {
					if (Globals.checkUserAgentHTML5()) {
						writer.println(assembleHTTP.dspLogin_Image_HTML5());
						writer.println(assembleHTTP.dspLogin_FormImage_HTML5(
								paramPhoto.getXMLFile(), paramPhoto.getImageFile()));
						writer.println("</div> <!-- gallery-box -->\n");

					} else {
					writer.println(assembleHTTP.dspLogin_Image_HTML4(
							paramPhoto.getXMLFile(), paramPhoto.getImageFile()));
					}
				} else {
					if (Globals.checkUserAgentHTML5()) {
						writer.println(assembleHTTP.dspLogin_All_HTML5());
						writer.println(assembleHTTP.dspLogin_FormGalleries_HTML5());
					} else {
						writer.println(assembleHTTP.dspLogin_All_HTML4());
					}
				}
				
				if (Globals.checkUserAgentHTML5()) {
					writer.println("</div> <!-- gallery-box -->\n");
				}
			}
		}
		writer.println(assembleHTTP.dspFooter());

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ProcessHTTP procHTTP = new ProcessHTTP(request, response);
		AssembleHTTP assHTTP = new AssembleHTTP();
		ParameterPhoto paramInfo = new ParameterPhoto();
		ParameterAdmin paramInfoAdmin = new ParameterAdmin();
		PrintWriter writer = response.getWriter();

		paramInfo.checkParameterNames(request, response);
		// paramInfo.checkParameterNames(writer, request);
		paramInfoAdmin.checkParameterNames(writer, request);
		procHTTP.getCookiePhoto(paramInfo);
		procHTTP.getCookieAdmin(paramInfoAdmin);

		if (Globals.checkUserAgentHTML5()) {
			writer.println(assHTTP.dspHeaderStart_HTML5());
		} else {
			writer.println(assHTTP.dspHeaderStart_HTML4());
		}

		// check if admin
		if (paramInfoAdmin.getDoLogin()) {
			// check if cookie or session
			writer.println(assHTTP.dspHeaderEnd());
				writer.println("<div class=\"gallery-box\">\n");
			if (paramInfoAdmin.getValidAdminAccess()) {
				writer.println(assHTTP.dspAdminMenu_HTML5());

			} else {
				// show login screen
				writer.println(assHTTP.dspLogin_Admin_HTML5());
				writer.println(assHTTP.dspLogin_FormAdmin_HTML5());
				// else bad attempt, give message and goto regular display
			}
			
			writer.println("</div> <!-- gallery-box -->\n");

		}

		else if (paramInfo.getShowAll()) {
			writer.println(assHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assHTTP.dspLogin_All_HTML5());
				writer.println(assHTTP.dspLogin_FormGalleries_HTML5());
			} else {
				writer.println(assHTTP.dspLogin_All_HTML4());
			}

			procHTTP.listArchives(paramInfo.getAccessCode());

		} else if (paramInfo.getDoThumbnails()) {
			procHTTP.listThumbnails(paramInfo);

		} else if (paramInfo.getDoImage()) {
			procHTTP.showPhoto(paramInfo);

		} else {
			writer.println(assHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assHTTP.dspLogin_All_HTML5());
				writer.println(assHTTP.dspLogin_FormGalleries_HTML5());
			} else {
				writer.println(assHTTP.dspLogin_All_HTML4());
			}
			procHTTP.listArchives(paramInfo.getAccessCode());
		}

		procHTTP.showDebug(paramInfo);
		writer.println(assHTTP.dspFooter());

	}

	public String doRedirect(String sXMLFile) {
		AssembleHTTP assembleHTTP = new AssembleHTTP();
		return assembleHTTP.makeHTTPSLogin(sXMLFile);

	}

	public String doRedirect() {
		AssembleHTTP assembleHTTP = new AssembleHTTP();
		return assembleHTTP.makeHTTPSLogin();
	}
}