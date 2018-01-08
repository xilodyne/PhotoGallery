package xilodyne.photo_gallery.http;

import xilodyne.photo_gallery.fileio.HSQLDB;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.globals.Logging;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aholiday
 *
 */
public class ParameterPhoto {
	private boolean bArchiveLogin;
	private boolean bDoAccessCheck;
	private boolean bDoImage;
	private boolean bDoLogin;
	private boolean bDoThumbnails;
	private boolean bFoundCookie;
	private boolean bOriginals;
	private boolean bDownload;
	private boolean bNewLogin;
	private boolean bNewSession;
	private boolean bPassedLogin;
	private boolean bShowAll;
	private boolean bShowEXIF;
	private String sXMLFile;
	private String sImageFile;
	private String sOrigSize;
	private Vector<Image> imageList;

	private String sAccessCode;

	public ParameterPhoto() {
		this.bArchiveLogin = false;
		this.bDoAccessCheck = false;
		this.bDoImage = false;
		this.bDoLogin = false;
		this.bDoThumbnails = false;
		this.bFoundCookie = false;
		this.bOriginals = false;
		this.bDownload = false;
		this.bPassedLogin = false;
		this.bNewLogin = false;
		this.bNewSession = false;
		this.bShowAll = false;
		this.bShowEXIF = false;
		this.sXMLFile = "";
		this.sImageFile = "";
		this.imageList = new Vector<Image>();

		this.sAccessCode = "";
	}

	public void setDoThumbnailsTrue() {
		this.bDoThumbnails = true;
	}

	public void setDoThumbnailsFalse() {
		this.bDoThumbnails = false;
	}

	public boolean getDoThumbnails() {
		return this.bDoThumbnails;
	}

	public void setDoImageTrue() {
		this.bDoImage = true;
	}

	public void setDoImageFalse() {
		this.bDoImage = false;
	}

	public boolean getDoImage() {
		return this.bDoImage;
	}

	public void setPassedLoginTrue() {
		this.bPassedLogin = true;
	}

	public void setPassedLoginFalse() {
		this.bPassedLogin = false;
	}

	public boolean getPassedLogin() {
		return this.bPassedLogin;
	}

	public void setShowAllTrue() {
		this.bShowAll = true;
	}

	public void setShowAllFalse() {
		this.bShowAll = false;
	}

	public boolean getShowAll() {
		return this.bShowAll;
	}

	public void setFoundCookie(boolean value) {
		this.bFoundCookie = value;
	}

	public boolean getFoundCookie() {
		return this.bFoundCookie;
	}
	
	
	public void setOriginals() {
		this.bOriginals = true;
	}
	public boolean getOriginals() {
		return this.bOriginals;
	}
	
	public void setDownload() {
		this.bDownload = true;
	}
	public boolean getDownload() {
		return this.bDownload;
	}
	

	public void setNewSessionTrue() {
		this.bNewSession = true;
	}

	public void setNewSessionFalse() {
		this.bNewSession = false;
	}

	public boolean getNewSession() {
		return this.bNewSession;
	}
	
	public void setShowEXIFTrue() {
		this.bShowEXIF = true;
	}
	public void setShowEXIFFalse() {
		this.bShowEXIF = false;
	}
	public boolean getShowEXIF() {
		return this.bShowEXIF;
	}

	public void setNewLoginTrue() {
		this.bNewLogin = true;
	}

	public void setNewLoginFalse() {
		this.bNewLogin = false;
	}

	public boolean getNewLogin() {
		return this.bNewLogin;
	}

	public void setDoAccessCheckTrue() {
		this.bDoAccessCheck = true;
	}

	public void setDoAccessCheckFalse() {
		this.bDoAccessCheck = false;
	}

	public boolean getDoAccessCheck() {
		return this.bDoAccessCheck;
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

	public void setXMLFile(String sXML) {
		this.sXMLFile = sXML.trim();
	}

	public String getXMLFile() {
		return this.sXMLFile;
	}

	public void setImageFile(String sImage) {
		this.sImageFile = sImage.trim();
		
		//assume that when image file is set that we also set the 
		//xml file too
		String sXMLFile = HSQLDB.getXMLFile(sImageFile.toLowerCase());
		System.out.println("Found xmlfile: " + sXMLFile);
		if ( sXMLFile != "9999" ) {
			this.setXMLFile(sXMLFile);
		}
	}

	public String getImageFile() {
		return this.sImageFile;
	}

	public void setAccessCode(String sCode) {
		this.sAccessCode = sCode.trim();
	}

	public String getAccessCode() {
		return this.sAccessCode;
	}
	
	public void setOrigSize( String sSize) {
		this.sOrigSize = sSize;
	}
	public String getOrigSize() {
		return this.sOrigSize;
	}

	public void setImageList(Image image) {
		this.imageList.add(image);
	}

	public Vector<Image> getImageList() {
		return this.imageList;
	}
	public HashMap<String, Image> getImageListHashMap() {
		HashMap<String, Image> hImage = new HashMap<String, Image>();
		Enumeration<Image> loop = this.imageList.elements();
		while (loop.hasMoreElements() ) {
			Image image = (Image) loop.nextElement();
			hImage.put(image.getFileOriginal(), image);
		}
		
		
		return hImage;
		
	}
	
	public int getImageListSize() {
		return this.imageList.size();
	}

	public void setArchiveLoginTrue() {
		this.bArchiveLogin = true;
	}

	public void setArchiveLoginFalse() {
		this.bArchiveLogin = false;
	}

	public boolean getArchiveLogin() {
		return this.bArchiveLogin;
	}


	public int getIndexOfImageFromList () {
		//loop through the vector until the location of the image object is found
		int i = 0;

		Enumeration<?> loop = this.getImageList().elements();
		while (loop.hasMoreElements()){
			Image image = (Image) loop.nextElement();

			if (this.getImageFile().compareTo( image.getFileWeb() ) == 0 ){
				break;
			}
			i++;
		}
		
		if ( i == this.getImageListSize() ) { //image not found, send error code -9999
			i = -9999;
		}
		return i;
	}
	
	public Image getImageAtIndex(int index) {
		Image image = new Image();

		if (index < this.getImageListSize() ) {
			Enumeration<?> loop = this.getImageList().elements();
			for (int i=0; i < index; i++) {
				loop.nextElement();
			}
			image = (Image) loop.nextElement();
		}

		return image;

	}


	public Image getImageFromList() {
		Image image = new Image();

		Enumeration<?> loop = this.getImageList().elements();
		while (loop.hasMoreElements()){
			image = (Image) loop.nextElement();

			if (this.getImageFile().compareTo( image.getFileWeb() ) == 0 ){
				break;
			}
		}
		return image;
	}

	
	public void checkParameterNames(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<?> param = request.getParameterNames();
		while (param.hasMoreElements()) {
			String name = (String) param.nextElement();
			Logging.debug(this.getClass().getName(), "Parameter name: " + name);

			if (Globals.USER_DEBUG) {
				System.out.println("param: " + name );
			}

			if (name.compareTo(Globals.PARAM_ACCESS_CODE) == 0) {
				this.setAccessCode(request.getParameter(name));
				this.setDoAccessCheckTrue();

				if (Globals.USER_DEBUG) {
					System.out.println("Login:  access code: " + this.getAccessCode());
				}
			}
			
			if (name.compareToIgnoreCase(Globals.PARAM_ORIGINALS ) == 0 ) {
				this.setOriginals();
				this.setImageFile(request.getParameter(name));
			}			
			
			if (name.compareToIgnoreCase(Globals.PARAM_ORIGINALS_DOWNLOAD ) == 0 ) {
				this.setDownload();
				this.setImageFile(request.getParameter(name));
			}
			
			if (name.compareToIgnoreCase(Globals.PARAM_ORIG_FILESIZE ) == 0 ) {
				this.setOrigSize(request.getParameter(name));

			}
			if (name.compareToIgnoreCase(Globals.PARAM_EXIF) == 0) {
				this.setShowEXIFTrue();
				this.setImageFile(request.getParameter(name));
			}


			// check if login
			if (name.compareTo(Globals.PARAM_LOGIN) == 0) {
				this.setXMLFile(request.getParameter(name));
				this.setArchiveLoginTrue();
			}

			if (name.compareTo(Globals.PARAM_LOGIN_CHECK) == 0) {
				this.setPassedLoginTrue();
			}

			if (name.compareTo(Globals.PARAM_NEW_LOGIN) == 0) {
				this.setNewLoginTrue();
			}

			if (name.compareTo(Globals.PARAM_NEW_SESSION) == 0) {
				this.setNewSessionTrue();
			}

			if (name.compareTo(Globals.PARAM_SHOW_ALL) == 0) {
				this.setShowAllTrue();
			}

			// check thumbnails
			if (name.compareToIgnoreCase(Globals.PARAM_THUMB) == 0) {
				this.setDoThumbnailsTrue();
				this.setXMLFile(request.getParameter(name));
				if (Globals.USER_DEBUG) {
					System.out.println("found: " + Globals.PARAM_THUMB);
				}
			}

			if (name.compareToIgnoreCase(Globals.PARAM_XML) == 0) {
				this.setXMLFile(request.getParameter(name));
			}

			// check if just image
			if (name.compareToIgnoreCase(Globals.PARAM_IMAGE) == 0) {
				this.setDoImageTrue();
				//legacy, if "-web" appears on filename, remove from filename
				//regardless of case, if filename ends in "-web.jpg" remove "-web"
				//set http 302 to inform user that URI has changed
				String sFilename = request.getParameter(name);
				Logging.debug(this.getClass().getName(), "Image: " + sFilename);
				int pos = sFilename.toLowerCase().indexOf("-web.jpg".toLowerCase());
				if ( pos > 0) {
					sFilename = sFilename.substring(0, pos) + sFilename.substring(pos+4);
					Logging.debug(this.getClass().getName(), "Image: " + sFilename);
					Logging.debug(this.getClass().getName(), "pos: " + pos);
			//		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			//		AssembleHTTP assHTTP = new AssembleHTTP();
			//		try {
			//			response.sendRedirect(assHTTP.makeHREFNextPhoto(this.getXMLFile(), sFilename));
			//		} catch (IOException e) {
						// TODO Auto-generated catch block
			//			e.printStackTrace();
			//		}
				}

				
				this.setImageFile(sFilename)	;
			}

		}
	}
}
