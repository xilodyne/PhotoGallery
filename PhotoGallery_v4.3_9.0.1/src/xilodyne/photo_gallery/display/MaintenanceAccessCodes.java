package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.access.AccessCodes;
import xilodyne.photo_gallery.fileio.ReadGalleryXML;
import xilodyne.photo_gallery.globals.AccessMatrix;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterAdmin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;


/**
 * @author aholiday
 * 
 */
public class MaintenanceAccessCodes {

	AssembleHTTP assembleHTTP = new AssembleHTTP();
	AccessCodes accessMatrix = new AccessCodes();
	ReadGalleryXML readXML = new ReadGalleryXML();

	public String dspAccessMatrix() {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Access Code and Gallery XML Directory</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Access Codes File Location</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.GALLERY_ACCESS_CODE_LOCATION + "</td>\n");
		sbHTML.append("		<td class=\"button\" rowspan=\"2\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_GOTO_FILE_CHANGE
				+ "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Gallery XML Directory Location</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.GALLERY_XML_DIR_LOCATION + "</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\"><td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Access Code Assignment</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Galleries</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append(this.printAccessMatrixForEdit());

		sbHTML.append("		<td class=\"exifhtml_name\">");
		
		if (Globals.checkFileSaved()) {
			sbHTML.append("Config files updated");
		}
		/* save file automatically after each change
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_SAVE_FILE
				+ "\" value=\"Save To File\">");
				*/
		sbHTML.append("</td>\n");
		sbHTML.append("	</tr>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");
	
		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");
		Globals.setCheckFileSavedDone();  

		return sbHTML.toString();
	}

	public String dspAccessGalleryFileLocationChange() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Access Code and Gallery XML Directory</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Access Codes File Location</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input size=\"100\" type=\"text\"  name=\"" + ParameterAdmin.PARAM_ACCESS_FILE_VALUE
				+ "\" value=\"" + Globals.GALLERY_ACCESS_CODE_LOCATION + "\">\n");
		sbHTML.append("		<td class=\"exifhtml_value\" rowspan=\"2\">\n");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESSGALLERY_FILE_NEW
				+ "\" value=\"Update\">");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Gallery XML Directory Location</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append("				<input size=\"100\" type=\"text\"  name=\"" + ParameterAdmin.PARAM_GALLERY_DIR_VALUE
				+ "\" value=\"" + Globals.GALLERY_XML_DIR_LOCATION + "\">\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}

	private String printAccessMatrixForEdit() {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("			<table>\n");

		if (Globals.USER_DEBUG_ACCESS)
			Logging.debug(this, "# number of users: " + AccessMatrix.getUserSize());

		// table heading
		sbHTML.append("				<tr>\n");
		sbHTML.append("					<td class=\"exifhtml_name\"></td>\n");

		for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
			sbHTML.append("					<td class=\"exifhtml_name\">");
			sbHTML.append(this.assembleHTTP.makeAccessHREFUsersUpdate(AccessMatrix.getUser(y)));
			sbHTML.append(AccessMatrix.getUser(y));
			sbHTML.append("</a></td>\n");
			if (Globals.USER_DEBUG_ACCESS)
				Logging.debug(this, "User index: " + y + ", value: " + AccessMatrix.getUser(y));
		}

		sbHTML.append("					<td class=\"exifhtml_name\">");
		sbHTML.append(this.assembleHTTP.makeAccessHREFAddUsers());
		sbHTML.append("New Access Code");
		sbHTML.append("</td>\n");
		sbHTML.append("			</tr>\n");

		if (Globals.USER_DEBUG_ACCESS)
			Logging.debug(this, "# number of files: " + AccessMatrix.getXMLSize());

	//	List list = new Vector<String,int>();
		
		//create a copy of the xml file array and track it's location
		String[][] xml = new String[AccessMatrix.getXMLSize()][2];
		for (int x=0; x< AccessMatrix.getXMLSize(); x++) {
			xml[x][0] = AccessMatrix.getXMLFile(x);
			xml[x][1] = String.valueOf(x);			
		}
		
		//let's check:
		for (int x = 0; x < xml.length; x++) {
			System.out.println(x+",0: " + xml[x][0] + ", " + x +",1: " + xml[x][1]);
		}
		//Arrays.sort(xml);
		Arrays.sort(xml, new Comparator<String[]>() {
		    @Override
		    public int compare(String[] s1, String[] s2) {
		        String t1 = s1[0];
		        String t2 = s2[0];
		        return t1.compareTo(t2);
		    }
		});

		//let's check:
		for (int x = 0; x < xml.length; x++) {
			System.out.println(x+",0: " + xml[x][0] + ", " + x +",1: " + xml[x][1]);
		}

		int userLoop = 0;
		for (int x = 0; x < AccessMatrix.getXMLSize(); x++) {
			sbHTML.append("				<tr>\n");
			sbHTML.append("					<td class=\"exifhtml_name\">");
	//		sbHTML.append(this.assembleHTTP.makeAccessHREFXMLFileUpdate(AccessMatrix.getXMLFile(userLoop)));
	//		sbHTML.append(AccessMatrix.getXMLFile(userLoop) + "</a></td>");
			sbHTML.append(this.assembleHTTP.makeAccessHREFXMLFileUpdate(xml[userLoop][0]));
			sbHTML.append(xml[userLoop][0] + "</a></td>\n");

			for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
				sbHTML.append("					<td class=\"exifhtml_value\">");
				sbHTML.append(AccessMatrix.getCode(Integer.valueOf(xml[x][1]), y));
				sbHTML.append("</td>\n");
			}
	/*		for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
				sbHTML.append("<td>");
				sbHTML.append(AccessMatrix.getCode(x, y));
				sbHTML.append("</td>");
				if (Globals.USER_DEBUG_ACCESS)
					Logging.debug(this, "File index: " + y + ", value: " + AccessMatrix.getXMLFile(y));
			}*/

			sbHTML.append("					<td class=\"exifhtml_value\">&nbsp;</td>\n");

			sbHTML.append("			</tr>\n");
			userLoop++;
		}

		// loop through the file directory. For each file not found in the
		// access matrix, list here

		Enumeration<String> loopFileDir = readXML.getFileList().elements();

		while (loopFileDir.hasMoreElements()) {
			String sTempXML = loopFileDir.nextElement();
			boolean bFoundInMatrix = false;
			for (int i = 0; i < AccessMatrix.getXMLSize(); i++) {
				String sAccessTemp = AccessMatrix.getXMLFile(i);
				if (sAccessTemp.compareTo(sTempXML) == 0) {
					bFoundInMatrix = true;
					break;
				}
			}
			if (!bFoundInMatrix) {
				sbHTML.append("			<tr>\n");
				sbHTML.append("					<td class=\"exifhtml_name\">");
				sbHTML.append(this.assembleHTTP.makeAccessHREFXMLFileUpdate(sTempXML));
				sbHTML.append(sTempXML + "</a></td>\n");
				sbHTML.append("					<td class=\"exifhtml_value\" colspan=\"" + AccessMatrix.getUserSize() + "\"></td>\n");
				sbHTML.append("			</tr>\n");
			}
		}

		sbHTML.append("			</table>\n");

		return sbHTML.toString();
	}

	public String dspAccessEditXML(String sXMLFile) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\">Modify Gallery XML</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">" + sXMLFile + "</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">\n");
		sbHTML.append("			<form class=\"button\" action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
		sbHTML.append("				<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_VALUE + "\" value=\""
				+ sXMLFile + "\">\n");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_DEACTIVATE
				+ "\" value=\"Deactivate\">\n");
		sbHTML.append("			</form>\n");
		sbHTML.append("			<form class=\"button\" action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
		sbHTML.append("				<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_VALUE + "\" value=\""
				+ sXMLFile + "\">\n");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_ASSIGNPUBLIC
				+ "\" value=\"Assign To Public\">\n");
		sbHTML.append("			</form>\n");
		sbHTML.append("			<form class=\"button\" action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
		sbHTML.append("				<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_VALUE + "\" value=\""
				+ sXMLFile + "\">\n");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_ASSIGNACCESS
				+ "\" value=\"Assign Access Codes\">\n");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();

	}

	public String dspAccessAssignUsersToFiles(String sXMLFile) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Access Code Assignment</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">" + sXMLFile + "</td>\n");
		sbHTML.append("<td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");

		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_VALUE + "\" value=\""
				+ sXMLFile + "\">");
		sbHTML.append("<table>");

		if (!AccessMatrix.XMLFilesContains(sXMLFile)) {
			AccessMatrix.addXMLFiles(sXMLFile);
		}
		int iRow = AccessMatrix.getIndexXMLFile(sXMLFile);

		// skip the first entry as it PublicGuest
		for (int i = 1; i < AccessMatrix.getUserSize(); i++) {
			sbHTML.append("<tr><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
			sbHTML.append("<input type=\"checkbox\" name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_ASSIGNVALUE + "_"
					+ i + "\" ");
			sbHTML.append("value=\"" + AccessMatrix.getUser(i) + "\" ");
			if (AccessMatrix.getCode(iRow, i)) {
				sbHTML.append("checked=\"check\"");
			}
			sbHTML.append("/>");
			sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">"
					+ AccessMatrix.getUser(i) + "</font>\n");
			sbHTML.append("		</td>\n");
			sbHTML.append("	</tr>\n");

		}

		sbHTML.append("</table>");

		sbHTML.append("</td><td rowspan=\"2\" valign=\"middle\" align=\"left\" bgcolor=\""
				+ Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
//		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_GOTO_ASSIGNXML	+ "\" value=\"Update\">");
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_XMLFILE_CODENEW	+ "\" value=\"Update\">");
		sbHTML.append("</form>");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");

		return sbHTML.toString();
	}

	public String dspAcessNewUser() {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"2\">Add New Access Code</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("<tr><td valign=\"middle\" align=\"center\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR
				+ "\">\n");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"text\" name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_VALUE + "\">");
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_ADDUSER_NEW
				+ "\" value=\"Add Access Code\">");
		sbHTML.append("</form>");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"2\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}

	public String dspAccessEditUsers(String sUsers) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"2\">Modify Users</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">" + sUsers + "</td>\n>");
		sbHTML.append("	</tr>\n");

		// cannot delete public
		if (!sUsers.equals(Globals.access_USER)) {
			sbHTML.append("<tr><td valign=\"middle\" align=\"center\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR
					+ "\">\n");
			sbHTML.append("<form action=\"");
			if (Globals.ENABLE_SSL) {
				sbHTML.append(Globals.HTTPS_Refer);
			} else {
				sbHTML.append(Globals.HTTP_Refer);
			}

			sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
			sbHTML.append("<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_VALUE + "\" value=\""
					+ sUsers + "\">");
			sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_REMOVE
					+ "\" value=\"Delete\">");
			sbHTML.append("</form>");
			sbHTML.append("		</td>\n");
			sbHTML.append("	</tr>\n");
		}

		sbHTML.append("<tr><td valign=\"middle\" align=\"center\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR
				+ "\">\n");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_VALUE + "\" value=\""
				+ sUsers + "\">");
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_GOTO_ASSIGNXML
				+ "\" value=\"Assign to Galleries\">");
		sbHTML.append("</form>");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"2\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();

	}

	public String dspAccessAssignFilesToUsers(String sUsers) {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">File Assignment</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">" + sUsers+ "</td>\n");
		sbHTML.append("<td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");

		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"hidden\" name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_VALUE + "\" value=\""
				+ sUsers + "\">");
		sbHTML.append("<table>");

		if (!AccessMatrix.UsersContains(sUsers)) {
			AccessMatrix.addUsers(sUsers);
		}
		int iCol = AccessMatrix.getIndexUsers(sUsers);

		
		for (int i = 0; i < AccessMatrix.getXMLSize(); i++) {
			// don't show public guest files
			
				sbHTML.append("<tr><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
				sbHTML.append("<input type=\"checkbox\" name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_XMLVALUE + "_" + i
						+ "\" ");
				sbHTML.append("value=\"" + AccessMatrix.getXMLFile(i) + "\" ");
				
				//never show checked if PublicGuest as we are taking it over
				if (i !=0 ) {
				if (AccessMatrix.getCode(i, iCol)) {
					sbHTML.append("checked=\"check\"");
				}
				}
				
				sbHTML.append("/>");
				sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">"
						+ AccessMatrix.getXMLFile(i) + "</font>\n");
				sbHTML.append("		</td>\n");
				sbHTML.append("	</tr>\n");
			
		}
		sbHTML.append("</table>");

		sbHTML.append("</td><td rowspan=\"2\" valign=\"middle\" align=\"left\" bgcolor=\""
				+ Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ACCESS_USERS_XMLFILENEW
				+ "\" value=\"Update\">");
		sbHTML.append("</form>");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();

	}


}

