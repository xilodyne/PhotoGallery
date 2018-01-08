package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.http.ParameterAdmin;

/**
 * @author aholiday
 *
 */
public class MaintenanceConfiguration {

	public String dspConfigurationSettings() {
		StringBuffer sbHTML = new StringBuffer();
		
	
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"large\" colspan=\"3\">Configuration</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Configuration File</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">File Location</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.GALLERY_USER_PROP_LOCATION +"</td>\n");
		sbHTML.append("		<td class=\"button\" rowspan=\"2\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_GOTO_FILE + "\" value=\"Change\">\n");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">HTTP Refer</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">HTTP Refer</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.HTTP_Refer +"</td>\n");	
		sbHTML.append("		<td class=\"button\" rowspan=\"3\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_HTTP_GOTO_REFER + "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Enable SSL</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.ENABLE_SSL +"</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">HTTPS Refer</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.HTTPS_Refer +"</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Settings</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Admin Password</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.USER_ADMIN_PASSWORD +"</td>\n");
		sbHTML.append("		<td class=\"button\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_GOTO_ADMINPASS + "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Debug</td>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">" + Globals.USER_DEBUG +"</td>\n");
		sbHTML.append("		<td class=\"button\" rowspan=\"2\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_GOTO_DEBUG + "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">User Access Debug</td>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">" + Globals.USER_DEBUG_ACCESS +"</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Title</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.USER_WEB_TITLE +"</td>\n");
		sbHTML.append("		<td class=\"button\">\n");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_GOTO_HTML_TITLE + "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Webpage Background</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.USER_HTML_BACKGROUND_COLOR +"</td>\n");
		sbHTML.append("		<td class=\"button\" rowspan=\"3\">\n");
		sbHTML.append("<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_GOTO_HTML_COLOR + "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Webpage Foreground</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.USER_HTML_FOREGROUND_COLOR +"</td>\n");
		sbHTML.append("	</tr>\n");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Webpage Foreground Reduced</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.USER_HTML_FOREGROUND_LESS +"</td>\n");
		sbHTML.append("</td></tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Galleries Tomcat Root Directories</td>\n");
		sbHTML.append("	</tr>\n");
		
	
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Originals Root Directory (Read Only OK)</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.HTTP_ROOT_ORIGINALS_DIR +"</td>\n");
		sbHTML.append("		<td class=\"button\" rowspan=\"3\">\n");
		sbHTML.append("			<form action=\"");
		
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ROOT_GOTO_ASSIGNDIR + "\" value=\"Change\">");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");
		
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Web Directory (read/write)</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.HTTP_ROOT_WEB_DIR +"</td>\n");
		sbHTML.append("	</tr>\n");

		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Thumbnail Directory (read/write)</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">" + Globals.HTTP_ROOT_THUMBNAIL_DIR +"</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		
		return sbHTML.toString();
	}

	
	public String dspConfigFileChange() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Gallery Configuration</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">File Location</td>\n");
		sbHTML.append("		<td class=\"button\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
		sbHTML.append("				<input size=\"100\" type=\"text\"  name=\"" + ParameterAdmin.PARAM_CONFIG_FILE_VALUE+ "\" value=\"" + Globals.GALLERY_USER_PROP_LOCATION +"\"></td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");		
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_FILE_NEW + "\" value=\"Update\">\n");
		sbHTML.append("			</form>\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		
		return sbHTML.toString();
	}
	
	
	
	
	public String dspHTTPRefer() {
		StringBuffer sbHTML = new StringBuffer();
		
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">HTTP Refer</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">HTTP Refer</td>\n");
		sbHTML.append("		<td class=\"button\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("				<input type=\"text\"  maxlength=\"120\" length=\"80\" name=\"" + ParameterAdmin.PARAM_HTTP_REFER_VALUE+ "\" value=\"" + Globals.HTTP_Refer +"\"/>");
		sbHTML.append("</td><td rowspan=\"3\" valign=\"middle\" align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");		
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_HTTP_NEW + "\" value=\"Update\">");
		sbHTML.append("</td></tr>\n");


		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Enable SSL</font></bold>");
		
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<select name=\"" + ParameterAdmin.PARAM_HTTP_ENABLESSL_VALUE + "\">");
		sbHTML.append("<option value=\"false\" ");
		if (String.valueOf( Globals.ENABLE_SSL).compareTo("false") == 0 ) sbHTML.append (" selected=\"selected\" ");
		sbHTML.append(">False</option>");
		sbHTML.append("<option value=\"true\" ");
		if (String.valueOf( Globals.ENABLE_SSL).compareTo("true") == 0 ) sbHTML.append (" selected=\"selected\" ");		
		sbHTML.append(">True</option>");
		sbHTML.append("</select>");


		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">HTTPS Refer</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<input type=\"text\"  name=\"" + ParameterAdmin.PARAM_HTTPS_REFER_VALUE + "\" value=\"" + Globals.HTTPS_Refer +"\"/>");
		sbHTML.append("			</form>");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}

	
	public String dspConfAdminPassword() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Gallery Configuration</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Admin Password</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"text\"  name=\"" + ParameterAdmin.PARAM_CONFIG_ADMINPASS_VALUE+ "\" />");
		sbHTML.append("</td><td valign=\"middle\" align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");		
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_ADMINPASS_NEW + "\" value=\"Update\">");
		sbHTML.append("</form>");
		sbHTML.append("</td></tr>\n");


		sbHTML.append("</td></tr>\n");
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
		
	}

	
	public String dspConfDebug() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td height=\"20\" colspan=\"3\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" >\n");
		sbHTML.append("<bold><font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"+2\" face=\"arial\">Settings</font></bold>");
		sbHTML.append("</td></tr>\n");
		

		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Debug</font></bold>");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<select name=\"" + ParameterAdmin.PARAM_CONFIG_DEBUG_VALUE + "\">");
		sbHTML.append("<option value=\"false\" ");
		if (String.valueOf( Globals.USER_DEBUG).compareTo("false") == 0 ) sbHTML.append (" selected=\"selected\" ");
		sbHTML.append(">False</option>");
		sbHTML.append("<option value=\"true\" ");
		if (String.valueOf( Globals.USER_DEBUG).compareTo("true") == 0 ) sbHTML.append (" selected=\"selected\" ");		
		sbHTML.append(">True</option>");
		sbHTML.append("</select>");

		sbHTML.append("</td><td rowspan=\"2\" valign=\"middle\" align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");		
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_DEBUG_NEW + "\" value=\"Update\">");
		sbHTML.append("</td></tr>\n");


		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">User Access Debug</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<select name=\"" + ParameterAdmin.PARAM_CONFIG_DEBUGACCESS_VALUE + "\">");
		sbHTML.append("<option value=\"false\" ");
		if (String.valueOf( Globals.USER_DEBUG_ACCESS).compareTo("false") == 0 ) sbHTML.append (" selected=\"selected\" ");
		sbHTML.append(">False</option>");
		sbHTML.append("<option value=\"true\" ");
		if (String.valueOf( Globals.USER_DEBUG_ACCESS).compareTo("true") == 0 ) sbHTML.append (" selected=\"selected\" ");		
		sbHTML.append(">True</option>");
		sbHTML.append("</select>");

		sbHTML.append("</form>");
		sbHTML.append("</td></tr>\n");


		sbHTML.append("</td></tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
		
	}
	
	
	public String dspConfHTMLTitle() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td height=\"20\" colspan=\"3\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" >\n");
		sbHTML.append("<bold><font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"+2\" face=\"arial\">Gallery Configuration</font></bold>");
		sbHTML.append("</td></tr>\n");
		
		sbHTML.append("<tr valign=\"middle\" align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Title</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"text\"  name=\"" + ParameterAdmin.PARAM_CONFIG_HTMLTITLE_Value+ "\" value=\"" + Globals.USER_WEB_TITLE +"\">");
		sbHTML.append("</td><td valign=\"middle\" align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");		
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_HTMLTITLE_NEW + "\" value=\"Update\">");
		sbHTML.append("</form>");
		sbHTML.append("</td></tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
		
	}	
	
	
	public String dspConfHTMLColors() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td height=\"20\" colspan=\"3\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" >\n");
		sbHTML.append("<bold><font color=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\" size=\"+2\" face=\"arial\">Gallery Configuration</font></bold>");
		sbHTML.append("</td></tr>\n");
		

		sbHTML.append("<tr valign=\"bottom\" align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Webpage Background</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"text\"  name=\"" + ParameterAdmin.PARAM_CONFIG_HTMLBG_VALUE + "\" value=\"" + Globals.USER_HTML_BACKGROUND_COLOR +"\">");
		sbHTML.append("</td><td rowspan=\"3\" valign=\"middle\" align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");		
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_CONFIG_HTMLCOLOR_NEW + "\" value=\"Update\">");
		sbHTML.append("</td></tr>\n");


		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Webpage Foreground</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<input type=\"text\"  name=\"" + ParameterAdmin.PARAM_CONFIG_HTMLFG_VALUE + "\" value=\"" + Globals.USER_HTML_FOREGROUND_COLOR +"\">");
		sbHTML.append("</td></tr>\n");


		sbHTML.append("<tr align=\"center\">\n");
		sbHTML.append("<td align=\"left\"  bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<font color=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\" size=\"+1\" face=\"arial\">Webpage Foreground Reduced</font></bold>");
		sbHTML.append("</td><td align=\"left\" bgcolor=\"" + Globals.USER_HTML_BACKGROUND_COLOR + "\">\n");
		sbHTML.append("<input type=\"text\"  name=\"" + ParameterAdmin.PARAM_CONFIG_HTMLFGLS_VALUE + "\" value=\"" + Globals.USER_HTML_FOREGROUND_LESS +"\">");
		sbHTML.append("</td></tr>\n");
		sbHTML.append("</form>");


		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
		
	}
	
	
	public String dspRootDir() {
		StringBuffer sbHTML = new StringBuffer();
		
		sbHTML.append("<div class=\"galleries-title\">\n");
		sbHTML.append("<table>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"3\">Galleries Root Directories</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Originals Root Directory (Read Only OK)</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append("			<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer );
		}
	
		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
		sbHTML.append("				<input size=\"100\" type=\"text\"  name=\"" + ParameterAdmin.PARAM_ROOT_ORIGINALS_DIR_VALUE + "\" value=\"" + Globals.HTTP_ROOT_ORIGINALS_DIR +"\">");
		sbHTML.append("		</td>\n");
		sbHTML.append("		<td class=\"exifhtml_name\" rowspan=\"3\">\n");		
		sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_ROOT_NEW + "\" value=\"Update\">");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Web Directory (read/write)</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append("				<input size=\"100\" type=\"text\"  name=\"" + ParameterAdmin.PARAM_ROOT_WEB_DIR_VALUE + "\" value=\"" + Globals.HTTP_ROOT_WEB_DIR +"\">\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"exifhtml_name\">Thumbnail Directory (read/write)</td>\n");
		sbHTML.append("		<td class=\"exifhtml_value\">\n");
		sbHTML.append("				<input size=\"100\" type=\"text\"  name=\"" + ParameterAdmin.PARAM_ROOT_THUMBNAIL_DIR_VALUE + "\" value=\"" + Globals.HTTP_ROOT_THUMBNAIL_DIR +"\">\n");
		sbHTML.append("		</td>\n");
		sbHTML.append("	</tr>\n");
		sbHTML.append("			</form>\n");


	//	sbHTML.append("</td></tr>\n");
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td align=\"center\" class=\"conftitle\" colspan=\"3\">&nbsp;</td>\n");
		sbHTML.append("	</tr>\n");

		sbHTML.append("</table>\n");
		sbHTML.append("</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
		
	}
	


}
