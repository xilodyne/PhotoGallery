package xilodyne.photo_gallery.http;

import xilodyne.photo_gallery.access.AccessCodes;
import xilodyne.photo_gallery.access.Encrypt;
import xilodyne.photo_gallery.display.PhotoHTML4;
import xilodyne.photo_gallery.display.PhotoHTML5;
import xilodyne.photo_gallery.display.PhotoInterface;
import xilodyne.photo_gallery.display.ThumbnailsHTML4;
import xilodyne.photo_gallery.display.ThumbnailsHTML5;
import xilodyne.photo_gallery.display.ThumbnailsInterface;
import xilodyne.photo_gallery.fileio.ReadGalleryXML;
import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.HTTPInfo;
import xilodyne.photo_gallery.http.ImageCookie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InvalidPropertiesFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author aholiday
 *
 */
public class ProcessHTTP {

	ArchiveInfo archive = new ArchiveInfo();
	AccessCodes accessCodes = new AccessCodes();
	AssembleHTTP assembleHTTP = new AssembleHTTP();
	ImageCookie imageCookie = new ImageCookie();
	// ThumbnailsHTML4 thumbs = new ThumbnailsHTML4();
	Encrypt encrypt = new Encrypt();

	HttpSession session;
	PrintWriter writer;
	HttpServletRequest request;
	HttpServletResponse response;

	boolean bValidAccessCode = false;

	public ProcessHTTP(HttpServletRequest tRequest,
			HttpServletResponse tResponse) throws IOException, ServletException {
		request = tRequest;
		response = tResponse;

		session = request.getSession(true);
		writer = response.getWriter();
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("pragma", "no-cache");

		if (!Globals.checkDataLoaded()) {
			accessCodes = new AccessCodes();
			accessCodes.loadAccessCodes(writer);
		}
	}

	public ProcessHTTP(HttpServletRequest tRequest,
			HttpServletResponse tResponse, int fileLength, String filePath,
			String mimeType) throws IOException, ServletException {
		request = tRequest;
		response = tResponse;

		session = request.getSession(true);
		// writer = response.getWriter();

		// response.setContentType("application/force-download");
		// response.setContentType("application/octet-stream");
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setContentLength(fileLength);
		// response.setContentLength(-1);
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filePath + "\"");

		if (!Globals.checkDataLoaded()) {
			accessCodes = new AccessCodes();
			accessCodes.loadAccessCodes(writer);
		}
	}

	public boolean getCookiePhoto(ParameterPhoto paramInfo) {
		String sCode = this.imageCookie.getCookieValue(writer, request,
				Globals.COOKIE_PHOTO);
		// String sCode = encrypt.decrypt(sEncryptedCode);
		// String sCode = sEncryptedCode;

		paramInfo.setAccessCode(sCode);
		bValidAccessCode = accessCodes.validAccessCode(writer, sCode);

		Logging.debug(this.getClass().getName(),
				"ProcessHTTP.getCookiePhoto: text: " + sCode);

		if (bValidAccessCode) {
			// session.setAttribute(Globals.SESSION_CODE,
			// encrypt.encrypt(sCode));
			this.setSessionCode(sCode, Globals.SESSION_PHOTO_ACCESS);
			paramInfo.setAccessCode(sCode);
		} else {
			this.setSessionCode(Globals.access_PUBLIC,
					Globals.SESSION_PHOTO_ACCESS);
			paramInfo.setAccessCode(Globals.access_PUBLIC);
		}

		return bValidAccessCode;
	}

	public boolean getCookieAdmin(ParameterAdmin paramInfo) {
		boolean bValidAdminCookie = false;
		String sCode = this.imageCookie.getCookieValue(writer, request,
				Globals.COOKIE_ADMIN);
		// String sCode = encrypt.decrypt(sEncryptedCode);

		Logging.debug(this.getClass().getName(),
				"ProcessHTTP.getCookieAdmin: text: " + sCode);

		if (sCode.length() > 0) {

			paramInfo.setAccessCode(sCode);

			if (sCode.compareTo(Globals.USER_ADMIN_PASSWORD) == 0) {
				bValidAdminCookie = true;
				paramInfo.setValidAdminAccessTrue();
				this.setSessionCode(sCode, Globals.SESSION_ADMIN_ACCESS);
			}
		}

		return bValidAdminCookie;
	}

	public void setSessionCode(String sSessionCode, String sAccessType) {
		session.setAttribute(sAccessType, encrypt.encrypt(sSessionCode));
	}

	public void listArchives(String sTempCode) {
		String[] xmlFiles;
		String sCode;

		if (!Globals.checkUserAgentHTML5()) {
			writer.println("<table align=\"center\" border=\"0\" width=\"50%\">\n");
		}

		if (bValidAccessCode) {
			sCode = sTempCode;
			xmlFiles = accessCodes.returnPermitedArchives(sCode);
			if (Globals.checkUserAgentHTML5()) {
				writer.println(assembleHTTP.dspAvailableArchives_HTML5(sCode,
						xmlFiles));
			} else {
				writer.println(assembleHTTP.dspAvailableArchives_HTML4(sCode,
						xmlFiles));
			}
			// thumbs.dspAvailableArchives(writer, paramInfo.getAccessCode(),
			// accessCodes);
		}

		xmlFiles = accessCodes.returnPermitedArchives(Globals.access_PUBLIC);

		if (Globals.checkUserAgentHTML5()) {
			writer.println(assembleHTTP.dspAvailableArchives_HTML5(
					Globals.access_PUBLIC, xmlFiles));
		} else {
			writer.println(assembleHTTP.dspAvailableArchives_HTML4(
					Globals.access_PUBLIC, xmlFiles));
		}

		if (!Globals.checkUserAgentHTML5()) {

			writer.println("</table>\n");
			writer.println("</div> <!-- gallery-box -->\n");
		}
	}

	public void showEXIF(ParameterPhoto paramInfo) {
		boolean bCheckAllowed = false;
		bCheckAllowed = accessCodes.validThumbnailFile(writer,
				paramInfo.getAccessCode(), paramInfo.getXMLFile());

		// if false and not public, check if a public photo and show
		// else show login
		if (!bCheckAllowed
				&& !(paramInfo.getAccessCode().compareTo(Globals.access_PUBLIC) == 0)) {
			bCheckAllowed = accessCodes.validThumbnailFile(writer,
					Globals.access_PUBLIC, paramInfo.getXMLFile());
		}

		if (bCheckAllowed) {
			// this.initVar(writer, archive, paramInfo);
			this.initVar(archive, paramInfo);

			PhotoInterface photo;

			if (Globals.checkUserAgentHTML5()) {
				photo = new PhotoHTML5();
			} else {
				photo = new PhotoHTML4();
			}

//			int index = assembleHTTP.getIndexOfImageFromList(paramInfo);
//			Image image = new Image();
//			image = assembleHTTP.getImageAtIndex(paramInfo, index);
			Image image = paramInfo.getImageFromList();
			
			String sPhotoTitle = image.getTitle() + " - EXIF data";

			writer.println(assembleHTTP.dspHeaderEnd(
					archive.getXMLFilenameTitle(), sPhotoTitle));
			writer.println(photo.dspEXIFData(archive, paramInfo));

		} else {
			writer.println(assembleHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assembleHTTP.dspLogin_Image_HTML5());
				writer.println(assembleHTTP.dspLogin_FormImage_HTML5(
						paramInfo.getXMLFile(), paramInfo.getImageFile()));
				writer.println("</div> <!-- gallery-box -->\n");
			} else {

			writer.println(assembleHTTP.dspLogin_Image_HTML4(paramInfo.getXMLFile(),
					paramInfo.getImageFile()));
			}
		}

	}

	public void listThumbnails(ParameterPhoto paramInfo) {
		boolean bCheckAllowed = false;

		// this.initVar(writer, archive, paramInfo);
		this.initVar(archive, paramInfo);
		bCheckAllowed = accessCodes.validThumbnailFile(writer,
				paramInfo.getAccessCode(), paramInfo.getXMLFile());

		if (!bCheckAllowed
				&& !(paramInfo.getAccessCode().compareTo(Globals.access_PUBLIC) == 0)) {
			bCheckAllowed = accessCodes.validThumbnailFile(writer,
					Globals.access_PUBLIC, paramInfo.getXMLFile());
		}

		if (bCheckAllowed) {
			ThumbnailsInterface thumbs;

			if (Globals.checkUserAgentHTML5()) {
				thumbs = new ThumbnailsHTML5();
			} else {
				thumbs = new ThumbnailsHTML4();
			}
			writer.println(assembleHTTP.dspHeaderEnd(
					archive.getXMLFilenameTitle(), ""));
			writer.println(thumbs.dspThumbnails(archive, paramInfo));
		} else {
			writer.println(assembleHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assembleHTTP.dspWrongAccessCode_HTML5());
				writer.println(assembleHTTP.dspLoginArchive_HTML5());
				writer.println(assembleHTTP.dspLogin_FormGallery_HTML5(paramInfo.getXMLFile()));
				writer.println("</div> <!-- gallery-box -->\n");
			} else {
			writer.println(assembleHTTP.dspLoginArchive_HTML4(paramInfo
					.getXMLFile()));
			}
		}
	}

	public void showPhoto(ParameterPhoto paramInfo) {
		boolean bCheckAllowed = false;
		bCheckAllowed = accessCodes.validThumbnailFile(writer,
				paramInfo.getAccessCode(), paramInfo.getXMLFile());

		Logging.info(this, "param access code: " + paramInfo.getAccessCode());

		// if false and not public, check if a public photo and show
		// else show login
		if (!bCheckAllowed
				&& !(paramInfo.getAccessCode().compareTo(Globals.access_PUBLIC) == 0)) {
			bCheckAllowed = accessCodes.validThumbnailFile(writer,
					Globals.access_PUBLIC, paramInfo.getXMLFile());
		}

		if (bCheckAllowed) {
			// this.initVar(writer, archive, paramInfo);
			this.initVar(archive, paramInfo);

			PhotoInterface photo;

			if (Globals.checkUserAgentHTML5()) {
				photo = new PhotoHTML5();
			} else {
				photo = new PhotoHTML4();
			}

	//		int index = assembleHTTP.getIndexOfImageFromList(paramInfo);
			int index = paramInfo.getIndexOfImageFromList();
			if (index == -9999 ) { //image not found
				writer.println("<p class=\"large\">Image not found.<p>");
				Logging.info(this, "Image not found: " + paramInfo.getImageFile() + ":" + paramInfo.getXMLFile() );
				//set 404 response code
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {
			Image image = new Image();
			//image = assembleHTTP.getImageAtIndex(paramInfo, index);
			image = paramInfo.getImageAtIndex(index);
			
			String sPhotoTitle = image.getTitle();

			writer.println(assembleHTTP.dspHeaderEnd(
					archive.getXMLFilenameTitle(), sPhotoTitle));
			writer.println(photo.dspImage(archive, paramInfo));
			}
		} else {
			writer.println(assembleHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assembleHTTP.dspLogin_Image_HTML5());
				writer.println(assembleHTTP.dspLogin_FormImage_HTML5(
						paramInfo.getXMLFile(), paramInfo.getImageFile()));
				writer.println("</div> <!-- gallery-box -->\n");
			} else {

			writer.println(assembleHTTP.dspLogin_Image_HTML4(paramInfo.getXMLFile(),
					paramInfo.getImageFile()));
			}
		}

		if (Globals.USER_DEBUG) {
			writer.println("<table border=\"1\" width=\"50%\" bgcolor=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			writer.println("<tr><td colspan=\"2\"><h2>Image Checking</h2></td></tr>");

			writer.println("<tr><th align=\"right\">Access Code</th>");
			writer.println("<td>" + paramInfo.getAccessCode() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Check Allowed</th>");
			writer.println("<td>" + bCheckAllowed + "</td>");
			writer.println("</tr>");

			writer.println("</table>");
		}

		this.showDebugParamInfo(paramInfo);
	}

	public void showDownload(ParameterPhoto paramInfo) throws IOException {
		boolean bCheckAllowed = false;
		bCheckAllowed = accessCodes.validThumbnailFile(
				paramInfo.getAccessCode(), paramInfo.getXMLFile());

		Logging.info(this, "param access code: " + paramInfo.getAccessCode());

		// if false and not public, check if a public photo and show
		// else show login
		if (!bCheckAllowed
				&& !(paramInfo.getAccessCode().compareTo(Globals.access_PUBLIC) == 0)) {
			bCheckAllowed = accessCodes.validThumbnailFile(
					Globals.access_PUBLIC, paramInfo.getXMLFile());
		}

		Logging.info(this, "Access allowed: " + bCheckAllowed);
		if (bCheckAllowed) {

			this.initVar(archive, paramInfo);

			// obtains response's output stream
			// reads input file from an absolute path
			// String filePath = Globals.FILESYSTEM_ROOT_DIR_PATH + "/" +
			// archive.getDirOriginals() + "/" + paramInfo.getImageFile();
			// String filePath = Globals.HTTP_Refer +"/" +
			// paramInfo.getImageFile();
			String filePath = Globals.CATALINE_HOME
					+ Globals.GALLERY_DEFAULT_DEPLOY_DIR
					+ archive.getDirOriginals() + "/"
					+ paramInfo.getImageFile();

			Logging.info(this.getClass().getName(), "Cataline home: "
					+ Globals.CATALINE_HOME);
			Logging.info(this.getClass().getName(), "Default deploy "
					+ Globals.GALLERY_DEFAULT_DEPLOY_DIR);
			Logging.info(this.getClass().getName(), "To Download: " + filePath);
			File downloadFile = new File(filePath);

			OutputStream outStream = response.getOutputStream();

			try {
				FileInputStream inStream = new FileInputStream(downloadFile);

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inStream.close();
				outStream.close();
				//
			} catch (FileNotFoundException e) {
				// outStream.close();
				Logging.error("File not found: " + filePath);
				// throw new FileNotFoundException ("File not found.");
				// // System.out.println(e);
				/*
				 * PrintWriter writer = response.getWriter();
				 * 
				 * writer.println(assembleHTTP.makeHeaderStart());
				 * writer.println(assembleHTTP.makeHeaderEnd());
				 * 
				 * writer.println("Error:  File not found.");
				 */
			}

		} else {
			writer.println(assembleHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assembleHTTP.dspLogin_Image_HTML5());
				writer.println(assembleHTTP.dspLogin_FormImage_HTML5(
						paramInfo.getXMLFile(), paramInfo.getImageFile()));
				writer.println("</div> <!-- gallery-box -->\n");
			} else {
			writer.println(assembleHTTP.dspLogin_Image_HTML4(paramInfo.getXMLFile(),
					paramInfo.getImageFile()));
			}
		}

		if (Globals.USER_DEBUG) {
			writer.println("<table border=\"1\" width=\"50%\" bgcolor=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			writer.println("<tr><td colspan=\"2\"><h2>Image Checking</h2></td></tr>");

			writer.println("<tr><th align=\"right\">Access Code</th>");
			writer.println("<td>" + paramInfo.getAccessCode() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Check Allowed</th>");
			writer.println("<td>" + bCheckAllowed + "</td>");
			writer.println("</tr>");

			writer.println("</table>");

		}
		this.showDebugParamInfo(paramInfo);

	}

	public void showOriginals(ParameterPhoto paramInfo) {
		boolean bCheckAllowed = false;
		bCheckAllowed = accessCodes.validThumbnailFile(writer,
				paramInfo.getAccessCode(), paramInfo.getXMLFile());

		// if false and not public, check if a public photo and show
		// else show login
		if (!bCheckAllowed
				&& !(paramInfo.getAccessCode().compareTo(Globals.access_PUBLIC) == 0)) {
			bCheckAllowed = accessCodes.validThumbnailFile(writer,
					Globals.access_PUBLIC, paramInfo.getXMLFile());
		}

		if (bCheckAllowed) {

			// this.initVar(writer, archive, paramInfo);
			this.initVar(archive, paramInfo);
			PhotoHTML5 photo = new PhotoHTML5();

			String sPhotoTitle = paramInfo.getImageFile();

			writer.println(assembleHTTP.dspHeaderEnd("(Original) "
					+ sPhotoTitle, ""));

			writer.println(photo.dspOriginals(archive, paramInfo));
		} else {
			writer.println(assembleHTTP.dspHeaderEnd());
			if (Globals.checkUserAgentHTML5()) {
				writer.println("<div class=\"gallery-box\">\n");
				writer.println(assembleHTTP.dspLogin_Image_HTML5());
				writer.println(assembleHTTP.dspLogin_FormImage_HTML5(
						paramInfo.getXMLFile(), paramInfo.getImageFile()));
				writer.println("</div> <!-- gallery-box -->\n");
			} else {

			writer.println(assembleHTTP.dspLogin_Image_HTML4(paramInfo.getXMLFile(),
					paramInfo.getImageFile()));
			}
		}

		if (Globals.USER_DEBUG) {
			writer.println("<table border=\"1\" width=\"50%\" bgcolor=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			writer.println("<tr><td colspan=\"2\"><h2>Image Checking</h2></td></tr>");

			writer.println("<tr><th align=\"right\">Access Code</th>");
			writer.println("<td>" + paramInfo.getAccessCode() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Check Allowed</th>");
			writer.println("<td>" + bCheckAllowed + "</td>");
			writer.println("</tr>");

			writer.println("</table>");

		}
		this.showDebugParamInfo(paramInfo);
	}


	public void showDebug(ParameterPhoto paramInfo) {
		HTTPInfo httpInfo = new HTTPInfo();

		if (Globals.USER_DEBUG) {
			httpInfo.showAssignedValuesForGallery(writer, paramInfo);
			httpInfo.showRequest(writer, request);

			writer.println("<table border=\"1\" width=\"50%\" bgcolor=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			writer.println("<tr><td colspan=\"2\"><h2>ProcessHTTP:  Session</h2></td></tr>");

			writer.println("<tr><th align=\"right\">(Gallery) Session is new:</th>");
			writer.println("<td>" + paramInfo.getNewSession() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">(Gallery) Loaded passwords:</th>");
			writer.println("<td>" + Globals.checkDataLoaded() + "</td>");
			writer.println("</tr>");

			// used for generating a key
			// Encrypt encrypt = new Encrypt();
			// encrypt.makeKey( writer );

			writer.println("</table>");

		}
		this.showDebugParamInfo(paramInfo);
	}

	public void showDebugParamInfo(ParameterPhoto paramInfo) {
		if (Globals.USER_DEBUG) {

			writer.println("<table border=\"1\" width=\"50%\" bgcolor=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			writer.println("<tr><td colspan=\"2\"><h2>Process HTTP: Parameter Info</h2></td></tr>");

			writer.println("<tr><th align=\"right\">Get Thumbnails</th>");
			writer.println("<td>" + paramInfo.getDoThumbnails() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Get Image</th>");
			writer.println("<td>" + paramInfo.getDoImage() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Get Original</th>");
			writer.println("<td>" + paramInfo.getOriginals() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Do Download</th>");
			writer.println("<td>" + paramInfo.getDownload() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Passed Login</th>");
			writer.println("<td>" + paramInfo.getPassedLogin() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Show All</th>");
			writer.println("<td>" + paramInfo.getShowAll() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Found Cookie</th>");
			writer.println("<td>" + paramInfo.getFoundCookie() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Session</th>");
			writer.println("<td>" + paramInfo.getNewSession() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">New Login</th>");
			writer.println("<td>" + paramInfo.getNewLogin() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Do Access Check</th>");
			writer.println("<td>" + paramInfo.getDoAccessCheck() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Do Login</th>");
			writer.println("<td>" + paramInfo.getDoLogin() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">XMLFile</th>");
			writer.println("<td>" + paramInfo.getXMLFile() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Image File</th>");
			writer.println("<td>" + paramInfo.getImageFile() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Access Code</th>");
			writer.println("<td>" + paramInfo.getAccessCode() + "</td>");
			writer.println("</tr>");

			writer.println("<tr><th align=\"right\">Archive Login</th>");
			writer.println("<td>" + paramInfo.getArchiveLogin() + "</td>");
			writer.println("</tr>");

			writer.println("</table>");
		}
	}

	private void initVar(ArchiveInfo archive, ParameterPhoto paramInfo) {
		Logging.info(this.getClass().getName(), "param getxmlfile " + paramInfo.getXMLFile());

	//.out.println("call archive...");
		archive.setXMLFilename(paramInfo.getXMLFile());
	//	System.out.println("from archive...");
	//	archive.dumpData();
		


		try {
			Logging.info(this.getClass().getName(), "Reading: " + archive.getDirWeb());
			
			new ReadGalleryXML(archive, paramInfo);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e ) {
			e.printStackTrace();
		}
			Logging.info(this.getClass().getName(), "archive get dir web" + archive.getDirWeb());

/*		} catch (ParserConfigurationException e) {
			Logging.error(e.getLocalizedMessage());
		} catch (SAXException e) {
			Logging.error(e.getLocalizedMessage());
		} catch (IOException e) {
			Logging.error(e);
			Logging.error("No images exist for this archive.");
		} */
	}
}
