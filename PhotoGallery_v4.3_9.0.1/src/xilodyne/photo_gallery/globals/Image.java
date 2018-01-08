package xilodyne.photo_gallery.globals;

/**
 * @author aholiday
 *
 */
public class Image {
	

	private String Title = "";
	private String Date = "";
	private String Description = "";
	private String file_thumbnail = "";
	private String file_web = "";
	private String file_original = "";
	private String file_original_size = "";
	
	
	
	
	public void setTitle (String sTitle) {
		this.Title = sTitle;
	}
	public String getTitle() {
		return this.Title;
	}
	
	public void setDate (String sDate ) {
		this.Date = sDate;
	}
	public String getDate() {
		return this.Date;
	}
	
	public String getDateYYYYMMDD() {
		// 10/12/2015 = 2015-10-12
		// 0123456789
		String yyyy = "";
		String mm = "";
		String dd = "";

		try {
			mm = this.Date.substring(0, 2);
			yyyy = this.Date.substring(6, 10);
			dd = this.Date.substring(3, 5);
		} catch (java.lang.StringIndexOutOfBoundsException e) 
		{}

		return (yyyy + "-" + mm + "-" + dd);
	}

	public void setDescription (String sDescription) {
		this.Description = sDescription;
	}
	
	public String getDescription () {
		if (this.Description == null ) return "";
		return this.Description;
	}
	
	
	public void setFileThumbnail (String sThumbnailFile ) {
		this.file_thumbnail = sThumbnailFile;
	}
	
	public String getFileThumbnail () {
		return this.file_thumbnail;
	}
	
	public void setFileWeb (String sFileWeb) {
		this.file_web = sFileWeb;
	}
	
	public String getFileWeb () {
		return this.file_web;
	}

	public void setFileOriginal ( String sOriginal ){
		this.file_original = sOriginal;
	}
	
	public String getFileOriginal() {
		return this.file_original;
	}

	public void setFileOriginalSize ( String sOriginalSize ){
		this.file_original_size = sOriginalSize;
	}
	
	public String getFileOriginalSize() {
		if (this.file_original_size.isEmpty()) return "0";	
		return this.file_original_size;
	}
	
}
