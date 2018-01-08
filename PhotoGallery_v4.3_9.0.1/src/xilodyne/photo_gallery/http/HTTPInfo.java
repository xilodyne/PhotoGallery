package xilodyne.photo_gallery.http;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import xilodyne.photo_gallery.globals.Globals;


/**
 * @author aholiday
 *
 */
public class HTTPInfo {
	
	public void showAssignedValuesForGallery(PrintWriter writer, ParameterPhoto paramInfo ){
			
			
	    	writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
	    	writer.println("<tr><td colspan=\"2\"><h2>Internal Assigned Values for Gallery</h2></td></tr>");

		    writer.println("<tr><th align=\"right\">Do Thumbnails:</th>");
		    writer.println( "<td>" + paramInfo.getDoThumbnails() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Do Image:</th>");
		    writer.println( "<td>" + paramInfo.getDoImage() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Do Login:");
		    writer.println( "<td>" + paramInfo.getDoLogin() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">XML File:</th>");
		    writer.println( "<td>" + paramInfo.getXMLFile() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Image File:</th>");
		    writer.println( "<td>" + paramInfo.getImageFile() + "</td>");
		    writer.println("</tr>");		    
		    
		    writer.println("<tr><th align=\"right\">Found cookie:</th>");
		    writer.println( "<td>" + paramInfo.getFoundCookie() + "</td>");
		    writer.println("</tr>");
		    
		    writer.println("<tr><th align=\"right\">Show all archives:</th>");
		    writer.println( "<td>" + paramInfo.getShowAll() + "</td>");
		    writer.println("</tr>");
		    
		    writer.println("</table>");   
			
		}

	
	
	public void showAssignedValuesForAdmin(PrintWriter writer, ParameterAdmin paramInfo ){
		
		
    	writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
    	writer.println("<tr><td colspan=\"2\"><h2>Parameter Values for Admin</h2></td></tr>");

	    writer.println("<tr><th align=\"right\">Do Access Check:</th>");
	    writer.println( "<td>" + paramInfo.getDoAccessCheck() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Do Admin Menu</th>");
	    writer.println( "<td>" + paramInfo.getDoAdminMenu() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Do Login</th>");
	    writer.println( "<td>" + paramInfo.getDoLogin() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Found Cookie</th>");
	    writer.println( "<td>" + paramInfo.getFoundCookie() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Valid Admin Access</th>");
	    writer.println( "<td>" + paramInfo.getValidAdminAccess() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Goto Change File</th>");
	    writer.println( "<td>" + paramInfo.getConfigGotoChangeFile() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Config File New</th>");
	    writer.println( "<td>" + paramInfo.getConfigFileChangeNew() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Config File Change Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigFileChangeValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Goto Change Admin Password</th>");
	    writer.println( "<td>" + paramInfo.getConfigGogoAdminPassword() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Admin Password New</th>");
	    writer.println( "<td>" + paramInfo.getConfigAdminPasswordNew() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Admin Password Change Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigAdminPasswordValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Goto Change Debug</th>");
	    writer.println( "<td>" + paramInfo.getConfigGotoDebug() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Debug New</th>");
	    writer.println( "<td>" + paramInfo.getConfigDebugNew() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Debug Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigDebugValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">DebugAccess New</th>");
	    writer.println( "<td>" + paramInfo.getConfigDebugAccessNew() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">DebugAccess Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigDebugAccessValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Goto HTML Title</th>");
	    writer.println( "<td>" + paramInfo.getConfigGotoHTMLTitle() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">HTML Title New</th>");
	    writer.println( "<td>" + paramInfo.getConfigHTMLTitleNew() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">HTML Title Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigHTMLTitleValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Goto HTML Color</th>");
	    writer.println( "<td>" + paramInfo.getConfigGotoHTMLColor() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">HTML Color New</th>");
	    writer.println( "<td>" + paramInfo.getConfigHTMLColorNew() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">HTML Foreground Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigHTMLFGValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">HTML ForegroundLess Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigHTMLFGLSValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">HTML Background Value</th>");
	    writer.println( "<td>" + paramInfo.getConfigHTMLBGValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("</table>");   

	}

	public void showAssignedValuesForLogin(PrintWriter writer, ParameterPhoto paramInfo) {		
			
	    	writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
	    	writer.println("<tr><td colspan=\"2\"><h2>Internal Assigned Values for Login</h2></td></tr>");

		    writer.println("<tr><th align=\"right\">Do Access Check:</th>");
		    writer.println( "<td>" + paramInfo.getDoAccessCheck() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Show all:</th>");
		    writer.println( "<td>" + paramInfo.getShowAll() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Do Login:");
		    writer.println( "<td>" + paramInfo.getArchiveLogin() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Do Image:");
		    writer.println( "<td>" + paramInfo.getDoImage() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Do Thumbnails:");
		    writer.println( "<td>" + paramInfo.getDoThumbnails() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">XML File:</th>");
		    writer.println( "<td>" + paramInfo.getXMLFile() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Image File:</th>");
		    writer.println( "<td>" + paramInfo.getImageFile() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Access Code:</th>");
		    writer.println( "<td>" + paramInfo.getAccessCode() + "</td>");
		    writer.println("</tr>");

		    writer.println("<tr><th align=\"right\">Force New Login:</th>");
		    writer.println( "<td>" + paramInfo.getNewLogin() + "</td>");
		    writer.println("</tr>");
		    
		    writer.println("</table>");   
			
		}

	
	public void showRequest(PrintWriter writer, HttpServletRequest request) {
    	writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
    	writer.println("<tr><td colspan=\"2\"><h2>Unique Values</h2></td></tr>");

	    writer.println("<tr><th align=\"right\">Auth Type:</th>");
	    writer.println( "<td>" + request.getAuthType() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Character Encoding:</th>");
	    writer.println( "<td>" + request.getCharacterEncoding() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Content Length:</th>");
	    writer.println( "<td>" + request.getContentLength() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Content Type:</th>");
	    writer.println( "<td>" + request.getContentType() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Context Path:</th>");
	    writer.println( "<td>" + request.getContextPath() + "</td>");
	    writer.println("</tr>");
	    
//	    writer.println("<tr><th align=\"right\">Date Header:</th>");
//	    writer.println( "<td>" + request.getDateHeader( "test") + "</td>");
//	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Local Addr:</th>");
	    writer.println( "<td>" + request.getLocalAddr() + "</td>");
	    writer.println("</tr>");
	        
	    writer.println("<tr><th align=\"right\">Local Name:</th>");
	    writer.println( "<td>" + request.getLocalName() + "</td>");
	    writer.println("</tr>");
	        
	    writer.println("<tr><th align=\"right\">Local Port:</th>");
	    writer.println( "<td>" + request.getLocalPort() + "</td>");
	    writer.println("</tr>");
	        
	    writer.println("<tr><th align=\"right\">Get Method:</th>");
	    writer.println( "<td>" + request.getMethod() + "</td>");
	    writer.println("</tr>");
	        
	    writer.println("<tr><th align=\"right\">Path Info:</th>");
	    writer.println( "<td>" + request.getPathInfo() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Path Translated:</th>");
	    writer.println( "<td>" + request.getPathTranslated() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Protocol:</th>");
	    writer.println( "<td>" + request.getProtocol() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Query String:</th>");
	    writer.println( "<td>" + request.getQueryString() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Remote Address:</th>");
	    writer.println( "<td>" + request.getRemoteAddr() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Remote Host:</th>");
	    writer.println( "<td>" + request.getRemoteHost() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Remote Port:</th>");
	    writer.println( "<td>" + request.getRemotePort() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Remote User:</th>");
	    writer.println( "<td>" + request.getRemoteUser() + "</td>");
	    writer.println("</tr>");
	    
	    writer.println("<tr><th align=\"right\">Requeste Session ID:</th>");
	    writer.println( "<td>" + request.getRequestedSessionId() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Request URI:</th>");
	    writer.println( "<td>" + request.getRequestURI() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Scheme:</th>");
	    writer.println( "<td>" + request.getScheme() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Server Name:</th>");
	    writer.println( "<td>" + request.getServerName() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Servlet Path:</th>");
	    writer.println( "<td>" + request.getServletPath() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Is Requested Session ID from Cookie:</th>");
	    writer.println( "<td>" + request.isRequestedSessionIdFromCookie() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Is Requested Session ID from URL:</th>");
	    writer.println( "<td>" + request.isRequestedSessionIdFromURL() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Is Requested Session ID Valid:</th>");
	    writer.println( "<td>" + request.isRequestedSessionIdValid() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Is Secure:</th>");
	    writer.println( "<td>" + request.isSecure() + "</td>");
	    writer.println("</tr>");
	    
		writer.println("</table>");	
		
		
    	writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
    	writer.println("<tr><td colspan=\"2\"><h2>Request Paramter Names</h2></td></tr>");
		Enumeration<?> param = request.getParameterNames();
		
		while (param.hasMoreElements()) {
			String name = (String) param.nextElement();
			    writer.println("<tr>");
			    writer.println("  <th align=\"right\">" + name + ":</th>");
			    writer.println("  <td>" + request.getParameter(name) + "</td>");
			    writer.println("</tr>");
		    }
		writer.println("</table>");
		
		
		
		writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
		writer.println("<tr><td colspan=\"2\"><h2>Request Header Names</h2></td></tr> ");
		Enumeration<?> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
		    String name = (String) names.nextElement();
		    writer.println("<tr>");
		    writer.println("  <th align=\"right\">" + name + ":</th>");
		    writer.println("  <td>" + request.getHeader(name) + "</td>");
		    writer.println("</tr>");
		}
		writer.println("</table>");

		
		writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
		writer.println("<tr><td colspan=\"2\"><h2>Request Attribute Names</h2></td></tr> ");
		Enumeration<?> attributes = request.getAttributeNames();
		while (names.hasMoreElements()) {
		    String name = (String) attributes.nextElement();
		    writer.println("<tr>");
		    writer.println("  <th align=\"right\">" + name + ":</th>"); 
		    this.showAttributes( name, writer, request );
//		    writer.println("  <td>" + request.getAttribute(name) + "</td>");
		    writer.println("</tr>");
		}
		writer.println("</table>");		
		
		
	
		
		
		
	}
	private void showAttributes(String sName, PrintWriter writer, HttpServletRequest request) {
		writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
		writer.println("<tr><td colspan=\"2\"><h2>Request Attribute/h2></td></tr> ");
		Object obj = request.getAttribute(sName);
		
		    writer.println("<tr>");
		    writer.println("  <th align=\"right\">" + obj.toString() + ":</th>"); 
		    writer.println("  <td></td>");
		    writer.println("</tr>");
		
		writer.println("</table>");		
	}
	
	public void showCookie (Cookie cookie, PrintWriter writer) {
    	writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
    	writer.println("<tr><td colspan=\"2\"><h2>Cookie Values</h2></td></tr>");

	    writer.println("<tr><th align=\"right\">Name:</th>");
	    writer.println( "<td>" + cookie.getName() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Value:</th>");
	    writer.println( "<td>" + cookie.getValue() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Comment:</th>");
	    writer.println( "<td>" + cookie.getComment() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Domain:</th>");
	    writer.println( "<td>" + cookie.getDomain() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Max Age:</th>");
	    writer.println( "<td>" + cookie.getMaxAge() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Path:</th>");
	    writer.println( "<td>" + cookie.getPath() + "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Secure:</th>");
	    writer.println( "<td>" + cookie.getSecure()+ "</td>");
	    writer.println("</tr>");

	    writer.println("<tr><th align=\"right\">Version:</th>");
	    writer.println( "<td>" + cookie.getVersion()+ "</td>");
	    writer.println("</tr>");
	    
	    writer.println("</table>");
		}
}
