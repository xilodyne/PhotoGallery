package xilodyne.photo_gallery.threads;

import java.util.Date;

public class QueueObject {
	
	
	public final static int QUEUED=0;
	public final static int RUNNING=1;
	public final static int COMPLETE=2;
	public final static int ABORTED=3;
	public final static int DELETED=4;
	public final static int SLEEPING=5;
	
	private int ID;
	private int jobID;
	private Date queued, start, end;
	private int status;
	
	
	public QueueObject(int tempID, int tempJobID) {
		this.ID = tempID;
		this.jobID = tempJobID;
		queued = new Date();
		this.status = QUEUED;
	}
	
	public void setStart() {
		this.start = new Date();
		this.status = RUNNING;
	}
	
	public void setRestart() {
		this.setStart();
		this.end = null;
	}
	
	public void setEnd() {
		this.end = new Date();
		this.status = COMPLETE;
	}
	
	public void setAbort() {
		this.end = new Date();
		this.status = ABORTED;
	}
	
	public void setDelete() {
		this.status = DELETED;
	}
	
	
	public int getID() {
		return this.ID;
	}
	
	public int getJobID() {
		return this.jobID;
	}
	
	public Date getQueued() {
		return queued;
	}
	public Date getStarted() {
		return start;
	}
	public Date getFinished() {
		return end;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public String getStatusName() {
		String sTemp = "";
		
		switch (this.status) {
		case 0:
			sTemp="QUEUED";
			break;
		case 1:
			sTemp="RUNNING";
			break;
		case 2:
			sTemp="COMPLETE";
			break;
		case 3:
			sTemp="ABORTED";
			break;
		case 4:
			sTemp="DELETED";
			break;
		case 5:
			sTemp="SLEEPING";
			break;
		default:
			sTemp="ERROR";
			break;
			
		}
		
		return sTemp;
	}	
	



	
	

}
