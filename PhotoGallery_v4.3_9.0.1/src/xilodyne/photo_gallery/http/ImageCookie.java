package xilodyne.photo_gallery.http;

import xilodyne.photo_gallery.access.Encrypt;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aholiday
 * 
 */
public class ImageCookie {
	Encrypt encrypt = new Encrypt();
	

	public void createCookie(HttpServletResponse response, String sCode,
			String sCookieType) {
		String sEncrypt = encrypt.encrypt(sCode);
		Cookie cookie = new Cookie(sCookieType, sEncrypt);
		cookie.setPath(Globals.COOKIE_Path);
		cookie.setMaxAge(914496000);
		response.addCookie(cookie);

		Logging.info(this.getClass().getName(), "Access: " + sCode + ", encrypt: " + sEncrypt);
	}

	public String getCookieValue(PrintWriter writer,
			HttpServletRequest request, String sName) {
		boolean found = false;
		String result = "";
		Cookie[] cookies = request.getCookies();


		if (Globals.USER_DEBUG) {
			writer.println("<table border=\"1\" width=\"50%\" bgcolor=\""
					+ Globals.USER_HTML_FOREGROUND_COLOR + "\">");
			writer.println("<tr><td colspan=\"2\"><h2>Cookie " + sName
					+ ":  getting Value</h2></td></tr>");
		}

		if (cookies != null) {
			if (Globals.USER_DEBUG) {
				writer.println("<tr><th align=\"right\">(Cookie) length</th>");
				writer.println("<td>" + cookies.length + "</td>");
				writer.println("</tr>");
			}



			int i = 0;
			while (!found && i < cookies.length) {
				if (Globals.USER_DEBUG) {
					writer.println("<tr><th align=\"right\">(Cookie) Looking at:</th>");
					writer.println("<td>" + cookies[i].getName() + "</td>");
					writer.println("</tr>");

				}
				Logging.info(this.getClass().getName(), "Cookie result: " + cookies[i].getName() + ", value: " + cookies[i].getValue());

				if (cookies[i].getName().equals(sName)) {
					found = true;
					result = cookies[i].getValue();
	
				}
				i++;
			}
		}

		if (Globals.USER_DEBUG) {
			writer.println("</td></tr></table>");
		}

		return encrypt.decrypt(result);

	}
}
