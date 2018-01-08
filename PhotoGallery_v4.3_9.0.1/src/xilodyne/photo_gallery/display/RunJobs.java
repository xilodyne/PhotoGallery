package xilodyne.photo_gallery.display;

import java.util.Vector;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import xilodyne.photo_gallery.access.AccessCodes;
import xilodyne.photo_gallery.fileio.ReadGalleryXML;
import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.http.AssembleHTTP;
import xilodyne.photo_gallery.http.ParameterAdmin;
import xilodyne.photo_gallery.threads.Queue;
import xilodyne.photo_gallery.threads.QueueObject;

public class RunJobs {

	AssembleHTTP assembleHTTP = new AssembleHTTP();
	AccessCodes accessMatrix = new AccessCodes();
	ReadGalleryXML readXML = new ReadGalleryXML();
	Queue queue = new Queue();

	public String dspJobsMenu() {
		StringBuffer sbHTML = new StringBuffer();

		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">Job Choices</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");

		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"normal\">Generate SiteMap.xml");

		
		sbHTML.append("<form action=\"");
		if (Globals.ENABLE_SSL) {
			sbHTML.append(Globals.HTTPS_Refer);
		} else {
			sbHTML.append(Globals.HTTP_Refer);
		}

		sbHTML.append("/" + Globals.SERVLET_ADMIN + "\" method=\"get\">");
		sbHTML.append("<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_JOBS_RUN_SITEMAP
				+ "\" value=\"Run\">");
		sbHTML.append("</form>");
		sbHTML.append("		</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");
		
		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"normal\">Verify that the HTTP_Refer value in the Configuration settings matches your public domain name.</p>\n");
		sbHTML.append("		<p class=\"normal\">Current HTTP Refer setting: </p>");
		sbHTML.append("		<p class=\"large\">" + Globals.HTTP_Refer + "</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}
	
	public String dspRunSitemap() {
		StringBuffer sbHTML = new StringBuffer();

		Queue.newjob( Queue.SITEMAP);
		
		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">Sitemap Job added to queue</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");
		
		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">File is located at: " + Globals.GALLERY_METADATA_FILE_LOCATION + "</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}
	
	
	public String dspPauseJob(int jobID) {
		StringBuffer sbHTML = new StringBuffer();
		Queue.pause(jobID);
		
		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">Pause Submitted.  Job: " + jobID + "</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}
	
	
	public String dspRestartJob(int jobID) {
		StringBuffer sbHTML = new StringBuffer();
		Queue.restart(jobID);
		
		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">Restart Submitted.  Job: " + jobID + "</p>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");

		return sbHTML.toString();
	}
	
	
	

	
	public String dspQueues() {
		StringBuffer sbHTML = new StringBuffer();	
		boolean bQueueRunning = Queue.isQueueRunning();
		
		sbHTML.append("	<div class=\"galleries-title\">\n");
		sbHTML.append("		<p class=\"large\">Queue is ");
		
		if (bQueueRunning) {
			sbHTML.append( "Running" );
		} else {
			sbHTML.append( "Stopped");
		}
		
		sbHTML.append("</p>\n");
		

		
		sbHTML.append("    <form action=\"");
		sbHTML.append(Globals.HTTP_Refer);
		sbHTML.append("/");
		sbHTML.append(Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
		sbHTML.append("      <input type=\"hidden\"  name=\"" + ParameterAdmin.PARAM_JOBS_QUEUE_CHANGE_VALUE + "\" value=\"");
		
		//set queue to what end result should be, so if running is true, set to running is false
		if  (bQueueRunning) {
			sbHTML.append("false");
		} else {
			sbHTML.append("true");
		}
		
		sbHTML.append("\">\n");
		sbHTML.append("      <input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_JOBS_QUEUE_CHANGE_STATUS + "\" value=\"");
		
		if  (bQueueRunning) {
			sbHTML.append("Stop Queue");
		} else {
			sbHTML.append("Start Queue");
		}
		
		
		sbHTML.append("\">\n");
		sbHTML.append("    </form>\n");
		sbHTML.append("	</div> <!-- galleries-title -->\n");


		
		
		sbHTML.append("<table>\n");

		
		Vector<?> runQueue = Queue.getQueue();
		QueueObject qO;
		
		sbHTML.append("	<tr>\n");
		sbHTML.append("		<td class=\"conftitle\" colspan=\"8\">Jobs</td>\n");
		sbHTML.append("	</tr>\n");
		
		

		
		
		sbHTML.append("	<tr>\n");
		
		sbHTML.append("		<th class=\"exifhtml\">Action</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Status</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Function</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Job ID</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Sent to Queue</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Job Started</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Job Finished</th>\n");
		sbHTML.append("		<th class=\"exifhtml\">Working On</th>\n");
		sbHTML.append("	</tr>\n");
		

		
		Enumeration<?> runLoop = runQueue.elements();
		while (runLoop.hasMoreElements() ) {
			qO = (QueueObject) runLoop.nextElement();
			
			sbHTML.append("<tr>\n");
			
			sbHTML.append("		<td class=\"exifhtml_value\">\n");
			sbHTML.append("			<form action=\"");
			sbHTML.append(Globals.HTTP_Refer);
			sbHTML.append("/");
			sbHTML.append(Globals.SERVLET_ADMIN + "\" method=\"get\">\n");
			sbHTML.append("				<input type=\"hidden\"  name=\"" + ParameterAdmin.PARAM_JOBS_ID_VALUE+ "\" value=\"" + qO.getJobID() + "\">\n");

			if (qO.getStatus() == QueueObject.RUNNING ||
					qO.getStatus() == QueueObject.SLEEPING ||
					qO.getStatus() == QueueObject.QUEUED ) {
				sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_JOBS_PAUSE_JOB + "\" value=\"Pause\">\n");
			} else if (qO.getStatus() == QueueObject.ABORTED ) {
				sbHTML.append("				<input type=\"submit\"  name=\"" + ParameterAdmin.PARAM_JOBS_RESTART_JOB + "\" value=\"Restart\">\n");				
			}
			
			sbHTML.append("			</form>\n");
			sbHTML.append("		</td>\n");
			
			sbHTML.append("		<td class=\"exifhtml_value\">" + qO.getStatusName() + "</td>\n");
			sbHTML.append("		<td class=\"exifhtml_value\">" + Queue.getJobType( qO.getID() ) + "</td>\n");
			sbHTML.append("		<td class=\"exifhtml_value\">" + qO.getJobID()  + "</td>\n");
			sbHTML.append("		<td class=\"exifhtml_value\">" + this.dateFormat( qO.getQueued() ) + "</td>\n");
			sbHTML.append("		<td class=\"exifhtml_value\">" +  this.dateFormat( qO.getStarted() )  + "</td>\n");
			sbHTML.append("		<td class=\"exifhtml_value\">" +  this.dateFormat( qO.getFinished() ) + "</td>\n");
			sbHTML.append("		<td class=\"exifhtml_value\">" +  Queue.getWorkingOn(qO.getID() ) + "</td>\n");			
			sbHTML.append("	</tr>\n");
		}
		
		sbHTML.append("</table>\n");

		return sbHTML.toString();
		
	
	}
	
	private String dateFormat(Date date) {
		String sDate = " -- ";
		
		if (date == null) return sDate;
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sDate = dateFormat.format(date);

		return sDate;
		
		
	}
	
	public static boolean checkExists(String sDirName) {
		boolean bExists = false;
		File dir = new File(sDirName);
		if (dir.exists() ) {
			bExists = true;
		}
		return bExists;
	}
	
	public static boolean checkWritable(String sDirName) {
		boolean bWritable = false;
		File dirTest = new File(sDirName+"/testwrite.txt");
		
		try {
		BufferedWriter writeFile = new BufferedWriter(new FileWriter(dirTest));
		writeFile.append("test");
		writeFile.newLine();
		writeFile.close();
		
		dirTest.delete();
		
		bWritable = true;
		} catch (IOException e ) {
			System.err.println(e);
		}
		
		return bWritable;
	}
	
}