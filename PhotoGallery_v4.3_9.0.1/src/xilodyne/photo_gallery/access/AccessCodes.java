package xilodyne.photo_gallery.access;

import xilodyne.photo_gallery.globals.AccessMatrix;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;
import xilodyne.photo_gallery.http.AssembleHTTP;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

/**
 * @author aholiday
 *
 */
public class AccessCodes {

	AssembleHTTP assembleHTTP = new AssembleHTTP();
	private static String className = "";
	
	public AccessCodes () {
		className = this.getClass().getName();
	}
	

	public void loadAccessCodes(PrintWriter writer) {
		Globals.setDataLoaded();

		
	//	if (Globals.USER_DEBUG_ACCESS) {
	//		printAccessMatrix(writer);
	//	}
	}
	
	public void loadAccessCodes() {
		Globals.setDataLoaded();
	}

	
	public Vector<String> returnValidAchivesForUser(int location) {
		Vector<String> xmlFiles = new Vector<String>();

		
		Logging.info(this.getClass().getName(), "AccessMatrix size: " + AccessMatrix.getXMLSize());
		for (int i = 0; i < AccessMatrix.getXMLSize(); i++) {
			if (AccessMatrix.getCode(i, location)) {
				xmlFiles.add(AccessMatrix.getXMLFile(i));
			}
		}
		
		return xmlFiles;
	}

	
	public boolean validThumbnailFile(PrintWriter writer, String sCode, String sXMLFile) {
		boolean bFound = false;

		//loop through all access codes
		//1. verify access code is in master code list
		//2. if found, verify that xml file is associated to this access code

		int iXMLFileLocation = AccessMatrix.getIndexXMLFile(sXMLFile);
		int iUserLocation = AccessMatrix.getIndexUsers(sCode);

		if ((iXMLFileLocation >= 0) && (iUserLocation >= 0)) {
			bFound = AccessMatrix.getCode(iXMLFileLocation, iUserLocation);
		}

		writer.println(assembleHTTP.debugAccess(sCode, sXMLFile, iXMLFileLocation, iUserLocation, bFound));

		return bFound;
	}	
	
	public boolean validThumbnailFile(String sCode, String sXMLFile) {
		boolean bFound = false;

		//loop through all access codes
		//1. verify access code is in master code list
		//2. if found, verify that xml file is associated to this access code

		int iXMLFileLocation = AccessMatrix.getIndexXMLFile(sXMLFile);
		int iUserLocation = AccessMatrix.getIndexUsers(sCode);

		if ((iXMLFileLocation >= 0) && (iUserLocation >= 0)) {
			bFound = AccessMatrix.getCode(iXMLFileLocation, iUserLocation);
		}

		Logging.debug(this.getClass().getName(), assembleHTTP.debugAccess(sCode, sXMLFile, iXMLFileLocation, iUserLocation, bFound));

		return bFound;
	}

	
	public boolean validAccessCode(PrintWriter writer, String sCode) {
		boolean bFound = false;

		//loop through all access codes
		//1. verify access code is in master code list
		//2. if found, verify that xml file is associated to this access code

		Logging.info(this.getClass().getName(), "Access Codes, code: " + sCode);
		int iUserLocation = AccessMatrix.getIndexUsers(sCode);

		if (iUserLocation >= 0) {
			bFound = true;
		}

	//	printAccessMatrix(writer);
		assembleHTTP.debugValidAccessCode(sCode, iUserLocation, bFound);
		return bFound;
	}

	
	
	public String[] returnPermitedArchives(String sAccessCode) {
		Vector<String> xmlFiles = new Vector<String>();
		int location = AccessMatrix.getIndexUsers(sAccessCode);

		Logging.info(this.getClass().getName(), "Location: " + location + ", AccessCode: " + sAccessCode);

		//get the locationValue of the access code
		//from the AccessMatrix, where user is true, return the xmlfile		
		xmlFiles = this.returnValidAchivesForUser(location);

		Enumeration<String> loop = xmlFiles.elements();
		int increment = 0;
		String[] xml = new String[xmlFiles.size()];
		while (loop.hasMoreElements()) {
			xml[increment] = (String) loop.nextElement();
			increment++;
		}
	//	List<String> list= Collections.list(loop); // create list from enumeration 
	//	Collections.sort(list);
	//	loop = Collections.enumeration(list);
		
		//sort alphabetically
		Arrays.sort(xml);
		return xml;
	}
	/*
	 Enumeration<String> enumeration = dictionary.keys(); // unsorted enumeration        
	List list= Collections.list(enumeration); // create list from enumeration 
	Collections.sort(list);
	enumeration = Collections.enumeration(list);
	 */

	
	public static void printAccessMatrix(PrintWriter writer) {
		
	//	if (!Globals.USER_DEBUG_ACCESS) {
	//		return;
	//	}

	//	//don't show in production
	//	if (Globals.USER_DEBUG_ACCESS ) {
	//		return;
	//	}
		
		writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
		writer.println("<tr><td colspan=\"3\"><h2>XML Files</h2></td></tr>");
		for (int x = 0; x < AccessMatrix.getXMLSize(); x++) {
			writer.println("<td>");
			writer.println(AccessMatrix.getXMLFile(x));
			writer.println("</td>");
		}
		writer.println("</table>");

		writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
		writer.println("<tr><td colspan=\"3\"><h2>User Table</h2></td></tr>");
		for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
			writer.println("<td>");
			writer.println(AccessMatrix.getUser(y));
			writer.println("</td>");
		}
		writer.println("</table>");

		writer.println("<table border=\"1\" width=\"50%\" bgcolor=\"" + Globals.USER_HTML_FOREGROUND_COLOR + "\">");
		writer.println("<tr><td colspan=\"4\"><h2>Access Matrix Table</h2></td></tr>");

		//table heading
		writer.println("<tr><td></td>");
		for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
			writer.println("<td>");
			writer.println(AccessMatrix.getUser(y));
			writer.println("</td>");
		}

		writer.println("</tr>");
		int userLoop = 0;
		
		Logging.info(className, "linked hasset:" + AccessMatrix.getXMLSize());
		for (int x = 0; x < AccessMatrix.getXMLSize(); x++) {
			writer.println("<tr>");
			writer.println("<td>" + AccessMatrix.getXMLFile(userLoop) + "</td>");

			for (int y = 0; y < AccessMatrix.getUserSize(); y++) {
				writer.println("<td>");
				writer.println(AccessMatrix.getCode(x, y));
				writer.println("</td>");
			}
			writer.println("</tr>");
			userLoop++;
		}
		writer.println("</table>");
	}

}
