package xilodyne.photo_gallery.threads;

import java.util.ArrayList;
import java.util.Vector;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

public class Queue extends Thread {

	private final static boolean TRUE = true;
	//private final static int EMPTY = 0;
	private static boolean bQueueInited = false;
	// private static boolean ABORT=false;
	
	/*
	private static boolean ABORT_SITEMAP = false;
	private static boolean ABORT_CHECKDIR = false;
	private static boolean ABORT_XMLUPDATE = false;
	private static boolean ABORT_RASTERIZE = false;
	*/
	private static int ABORT_ID = -100;

	public static String SITEMAP_WORKING_ON = "";
	public static String CHECK_WORKING_ON = "";
	public static String XMLUPDATE_WORKING_ON = "";
	public static String RASTERIZE_WORKING_ON = "";

	public final static int SITEMAP = 1;
	public final static int CHECK_NEW_PHOTOS = 2;
	public final static int XMLUPDATE = 3;
	public final static int RASTERIZE = 4;

	private static boolean QueueRUNNING = false;

	//private static Date date;

	private static ArrayList<QueueObject> vQueue = new ArrayList<QueueObject>();
	private static int iJobID = 0;
	private static boolean bRunning = false;

	private static SiteMapThread siteMap = null;
//	private static CheckDirectoryStructure check = null;
//	private static WatchRastQueue rasterize = null;
//	private static WatchXMLQueue xmlUpdate = null;
	
	private static String className = "";

	public Queue() {

		className = this.getClass().getName();
		if (!bQueueInited) {
			bQueueInited = true;
			this.start();

		//	newjob(CHECK_NEW_PHOTOS);
		//	newjob(RASTERIZE);
		//	newjob(XMLUPDATE);

		}
	}

	public Queue(int iType) {

		if (!bQueueInited) {
			bQueueInited = true;
			this.start();
		}
		// queue.newjob( Queue.SITEMAP);

	}

	public synchronized static void newjob(int iType) {
		QueueObject qO = new QueueObject(iType, iJobID);
		push(qO);

	}

	public static void pause(int jobID) {

		ABORT_ID = jobID;
		Logging.info(className, "Setting abort id:" + jobID);
	}
	
	public synchronized static void restart(int jobID) {
		QueueObject qO = get(jobID);
		int id = qO.getID();
		qO.setStart();
		
		set(jobID, qO);
		if (id == SITEMAP) {
			bRunning = true;
			siteMap = new SiteMapThread(qO.getJobID());
			siteMap.start();
		}
//		if (id == CHECK_NEW_PHOTOS) {
//			check = new CheckDirectoryStructure(qO.getJobID());
//			check.start();
//
//		}
//		if (id == RASTERIZE) {
//			rasterize = new WatchRastQueue(qO.getJobID());
//			rasterize.start();
//		}

//		if (id == XMLUPDATE) {
//			xmlUpdate = new WatchXMLQueue(qO.getJobID());
//			xmlUpdate.start();
//		}

	}

	public synchronized void jobFinished(int iJobID, int iStatus) {
		QueueObject qO = get(iJobID);
		if (iStatus == QueueObject.ABORTED) {
			qO.setAbort();
		} else {
			qO.setEnd();
		}
		set(iJobID, qO);
		bRunning = false;
	}

	public static String Running() {

		return " ";
	}

	public static String Waiting() {

		return " ";
	}

	public static Vector<QueueObject> getQueue() {
		Vector<QueueObject> tempVector = new Vector<QueueObject>();
		// for (int i = 0; i< vQueue.size(); i++) {
		for (int i = vQueue.size() - 1; i >= 0; i--) {
			// tempVector.add((QueueObject) vQueue.get(i));
			QueueObject qO = (QueueObject) vQueue.get(i);
			if (qO.getStatus() != QueueObject.DELETED) {
				tempVector.add((QueueObject) vQueue.get(i));
			}
		}
		return tempVector;
	}

	public static String getJobType(int ID) {
		String sType = "";
		switch (ID) {
		case 1:
			sType = "Sitemap";
			break;
		case 2:
			sType = "Check New Photo";
			break;
		case 3:
			sType = "XMLFileUpdate";
			break;
		case 4:
			sType = "RasterizePhoto";
			break;
		default:
			sType = "ERROR";
			break;

		}

		return sType;
	}

	public static String getWorkingOn(int ID) {
		String sWorkingOn = "";
		switch (ID) {
		case 1:
			sWorkingOn = SITEMAP_WORKING_ON;
			break;
		case 2:
			sWorkingOn = CHECK_WORKING_ON;
			break;
		case 3:
			sWorkingOn = XMLUPDATE_WORKING_ON;
			break;
		case 4:
			sWorkingOn = RASTERIZE_WORKING_ON;
			break;
		default:
			sWorkingOn = "ERROR";
			break;

		}

		return sWorkingOn;
	}

	private synchronized static void push(QueueObject o) {
		vQueue.add(iJobID, o);
		iJobID = iJobID + 1; // increase index by one
	}

	private synchronized static QueueObject get(int iJobID) {
		return (QueueObject) vQueue.get(iJobID);
	}

	/*
	private synchronized static void remove(int iJobID) {
		// vQueue.remove(iJobID);
		QueueObject qO = get(iJobID);
		qO.setDelete();
		set(iJobID, qO);

	} */

	private synchronized static int getNextJobToRun() {
		int iFound = Globals.NOT_FOUND;
		for (int i = 0; i < vQueue.size(); i++) {
			QueueObject qO = (QueueObject) vQueue.get(i);
			if (qO.getStatus() == QueueObject.QUEUED) {
				iFound = i;
				break;
			}
		}

		return iFound;
	}

	private synchronized static void set(int iJobID, QueueObject qO) {
		vQueue.set(iJobID, qO);
	}

	public static boolean isQueueRunning() {
		return QueueRUNNING;
	}

	public synchronized static void setQueueRunningTRUE() {
		QueueRUNNING = true;
	}

	public synchronized static void setQueueRunningFALSE() {
		QueueRUNNING = false;
	}

	// thread that looks at queue
	// if not running, check if
	public void run() {

		QueueObject qO = null;
		QueueRUNNING = true;
		int iNext = -1;
		// if not RUNNING, then check queue
		// get next object
		// set to running

		while (TRUE) {

			if (QueueRUNNING) {
				//date = new Date();

				// don't get next job until free in run queue, except for
				// standard jobs
				if (!bRunning)
					iNext = Queue.getNextJobToRun();

				if ((!bRunning) && (iNext != Globals.NOT_FOUND)) {
					qO = Queue.get(iNext);

					int id = qO.getID();
					qO.setStart(); // update the object
					Queue.set(iNext, qO); // update the object in the queue

					if (id == SITEMAP) {
						bRunning = true;
						siteMap = new SiteMapThread(qO.getJobID());
						siteMap.start();
					}
//					if (id == CHECK_NEW_PHOTOS) {
//						check = new CheckDirectoryStructure(qO.getJobID());
//						check.start();
//					}
//					if (id == RASTERIZE) {
//						rasterize = new WatchRastQueue(qO.getJobID());
//						rasterize.start();
//					}

//					if (id == XMLUPDATE) {
//						xmlUpdate = new WatchXMLQueue(qO.getJobID());
//						xmlUpdate.start();
//					}

				}
			} else {
				Logging.info(this.getClass().getName(), "Queue is not running.");
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				Logging.error(e);
			}

			if (ABORT_ID != -100) {
				QueueObject qTemp = Queue.get(ABORT_ID);
				if (qTemp.getJobID() == ABORT_ID) {
					Logging.info(this.getClass().getName(), "Aborting: " + ABORT_ID);
					Logging.info(this.getClass().getName(), "Type:  " + qO.getID());
					switch (qTemp.getID()) {
					case 1:
						siteMap.Abort();
						break;
					case 2:
//						check.interrupt();
//						check.Abort();
						break;
					case 3:
//						rasterize.interrupt();
//						rasterize.Abort();
						break;
					case 4:
//						xmlUpdate.interrupt();
//						xmlUpdate.Abort();
						break;
					default:

						break;

					}
				}
				ABORT_ID = -100; // reset ID
			}

		}

	}
}
