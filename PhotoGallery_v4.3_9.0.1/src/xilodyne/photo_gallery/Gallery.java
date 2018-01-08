package xilodyne.photo_gallery;

import xilodyne.photo_gallery.access.Encrypt;
import xilodyne.photo_gallery.fileio.ConfigFile;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterPhoto;
import xilodyne.photo_gallery.http.ProcessHTTP;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author Austin Davis Holiday (aholiday@xilodyne.com)
 * @version 1.2_9.0.1
 *
 * Photo Gallery for Tomcat
 */

/*
 * if new session and in doGet, redirect to login(nonssl) to see if cookie
 * exists, pass parameter list if old session and in doGet, and no cookie or
 * access code, only show public, show loginall if doPost and new session,
 * problem so continue without cookie if doPost and old session, came from
 * login, check if accesscode if access code, show valid files, gallery or
 * photo; show public, show loginAll if no access code, show only public,
 * loginAll
 */
public final class Gallery extends HttpServlet {
	static final long serialVersionUID = 0;

	Globals globals = new Globals();
	ConfigFile configFile;
	Encrypt encrypt = new Encrypt();

	/**
	 * Respond to a GET request for the content produced by this servlet.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are producing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sError = "";
		Boolean bError = false;

		if (!Globals.checkDataLoaded()) {

			try {
				configFile = new ConfigFile();
			} catch (IOException e) {
				sError = e.getMessage();
				bError = true;
			}
		}

		response.setCharacterEncoding("UTF-8");

		// HttpSession session = request.getSession(true);
		// ProcessHTTP procHTTP = new ProcessHTTP(request, response);
		AssembleHTTP assembleHTTP = new AssembleHTTP();
		ParameterPhoto paramInfo = new ParameterPhoto();

		paramInfo.checkParameterNames(request, response);
		this.checkUserAgent(request.getHeader("User-Agent"));
		// procHTTP.getCookiePhoto(paramInfo);
		// Austin- check that can't download non-public files
		// set page to force download, else show all other pages
		if (paramInfo.getDownload()) {
			// obtains ServletContext
			ServletContext context = getServletContext();

			// close the writer (which is used for text) an d switch to output
			// stream (used for binary)

			// gets MIME type of the file
			String mimeType = context.getMimeType(paramInfo.getImageFile());

			Logging.info(this.getClass().getName(), "Mime type: " + mimeType);
			Logging.info(this.getClass().getName(),
					"file size: " + paramInfo.getOrigSize());

			ProcessHTTP procHTTP = new ProcessHTTP(request, response,
					Integer.parseInt(paramInfo.getOrigSize()),
					paramInfo.getImageFile(), mimeType);
			procHTTP.getCookiePhoto(paramInfo);

			try {
				procHTTP.showDownload(paramInfo);
			} catch (FileNotFoundException e) {
				// PrintWriter writer = response.getWriter();

				// writer.println(assembleHTTP.makeHeaderStart());
				// writer.println(assembleHTTP.makeHeaderEnd());
				// writer.println("Error:  File not found.");
				Logging.error(e);
			}
			// } //don't write any header info into download file
			// writer.println(assembleHTTP.makeHeaderStartDownload(paramInfo.getXMLFile(),
			// paramInfo.getImageFile()));
		} else {

			ProcessHTTP procHTTP = new ProcessHTTP(request, response);
			procHTTP.getCookiePhoto(paramInfo);

			PrintWriter writer = response.getWriter();

			if (Globals.checkUserAgentHTML5()) {
				writer.println(assembleHTTP.dspHeaderStart_HTML5());
			} else {
				writer.println(assembleHTTP.dspHeaderStart_HTML4());
			}
			// writer.println(assembleHTTP.makeHeaderEnd());

			// ask for cookie, do redirect
			/*
			 * if (session.isNew() && !paramInfo.getNewSession()) {
			 * 
			 * if (bError) { writer.println(assembleHTTP.makeHeaderEnd());
			 * writer.println(assembleHTTP.dspMessage(sError)); } else { //
			 * response
			 * .sendRedirect(assembleHTTP.makeHTTPNewSessionToLogin(request
			 * .getQueryString()));
			 * response.sendRedirect(assembleHTTP.makeHTTPNewSessionToGallery
			 * (request.getQueryString())); }
			 * 
			 * } else {
			 */
			// get access from session
			if (bError) {
				// writer.println(assembleHTTP.makeHeaderEnd() );
				writer.println(assembleHTTP.dspMessage(sError));
			}

			if (paramInfo.getShowAll()) {
				writer.println(assembleHTTP.dspHeaderEnd());
				if (Globals.checkUserAgentHTML5()) {
					writer.println("<div class=\"gallery-box\">\n");
					writer.println(assembleHTTP.dspLogin_All_HTML5());
					writer.println(assembleHTTP.dspLogin_FormGalleries_HTML5());
				} else {
					writer.println(assembleHTTP.dspLogin_All_HTML4());
				}
				procHTTP.listArchives(paramInfo.getAccessCode());
				if (Globals.checkUserAgentHTML5()) {
					writer.println("</div> <!-- gallery-box -->\n");
				}

			} else if (paramInfo.getDoThumbnails()) {
				procHTTP.listThumbnails(paramInfo);

			} else if (paramInfo.getShowEXIF()) {
				procHTTP.showEXIF(paramInfo);

			} else if (paramInfo.getDoImage()) {
				procHTTP.showPhoto(paramInfo);

				// } else if (paramInfo.getOriginals()) {
				// procHTTP.showOriginals(paramInfo);

				// } else if (paramInfo.getDownload()) {
				// procHTTP.showDownload(paramInfo);

			} else {
				writer.println(assembleHTTP.dspHeaderEnd());
				if (Globals.checkUserAgentHTML5()) {
					writer.println("<div class=\"gallery-box\">\n");
					writer.println(assembleHTTP.dspLogin_All_HTML5());
					writer.println(assembleHTTP.dspLogin_FormGalleries_HTML5());
				} else {

					writer.println(assembleHTTP.dspLogin_All_HTML4());
				}
				procHTTP.listArchives(paramInfo.getAccessCode());
				if (Globals.checkUserAgentHTML5()) {
					writer.println("</div> <!-- gallery-box -->\n");
				}
			}
			// }
			procHTTP.showDebug(paramInfo);

			// don't give head if download as it appears in the file
			if (!paramInfo.getDownload()) {
				writer.println(assembleHTTP.dspFooter());
			}
		}
		
		
	}

	// check first part of User-Agent: Mozilla/5.0 (Macintosh; U; Intel Mac OS X
	// 10.5; en-US; rv:1.9.0.13) Gecko/2009073021 Firefox
	private void checkUserAgent(String browserDetails) {
		// String browserDetails = request.getHeader("User-Agent");
		String userAgent = browserDetails;

		int iFoundMozilla = userAgent.toLowerCase().indexOf("mozilla");

		if (iFoundMozilla >= 0) {
			String ver = userAgent.substring(iFoundMozilla + 8,
					iFoundMozilla + 11);
			Float fver = Float.valueOf(ver);
			Logging.info(this.getClass().getName(), "version: " + ver
					+ ", num: " + fver);
			if (fver < 5.0) {
				Globals.setUserAgentHMTL5False();
			} else {
				Globals.setUserAgentHTML5True();
			}
		}

	}
}