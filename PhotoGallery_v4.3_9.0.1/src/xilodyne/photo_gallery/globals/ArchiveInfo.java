package xilodyne.photo_gallery.globals;

/**
 * @author aholiday
 *
 */
public class ArchiveInfo {
	  private String Title = "";
	  
	  private String dir_thumbnail = "";
	  private String dir_web = "";
	  private String dir_originals="";

	  private int ThumbnailsPerRow = 4;
	  private int ThumbnailColumnWidth;
	  
	  private String XMLFilename = "";

	  public ArchiveInfo() {
		  
	  }
	  
	  public void setTitle (String sTitle ) {
		  this.Title = sTitle;
	  }
	  
	  public String getTitle() { 
		  return this.Title; 
	  }

	  
	  
	  public void setDirThumbnail ( String sThumbnailDir ){
		  this.dir_thumbnail = sThumbnailDir;
	  }
	  
	  public String getDirThumbnail() { 
		  return this.dir_thumbnail; 
	  }
	  
	  public void setDirWeb( String sDirWeb ) {
		  this.dir_web = sDirWeb;
	  }
	  
	  public String getDirWeb() { 
		  return this.dir_web;
	  }
	  
	  public void setDirOriginals (String sOriginals ){
		  this.dir_originals = sOriginals;
	  }
	  public String getDirOriginals () {
		  return this.dir_originals;
	  }
	  

	  
	  public void setThumbnailsPerRow ( int iThumbnailsPerRow ) {
		  this.ThumbnailsPerRow = iThumbnailsPerRow;
	  }
	  
	  public int getThumbnailsPerRow() { 
		  return this.ThumbnailsPerRow; 
	  }
	  
	  public void setThumbnailColumnWidth (int iThumbnailColumnWidth ){
		  this.ThumbnailColumnWidth = iThumbnailColumnWidth;
	  }
	  
	  public int getThumbnailColumnWidth() { 
		  return this.ThumbnailColumnWidth; 
	  }
	  

	  

	  public void setXMLFilename (String sXML) {
		  this.XMLFilename = sXML;
	  }
	  
	  public String getXMLFilename (){
		  return this.XMLFilename;
	  }
	  
	  public String getXMLFilenameTitle () {
		  String sTemp = this.XMLFilename;
		
		  int iLocation = sTemp.indexOf(".xml");
		  sTemp = sTemp.substring(0, iLocation);

		  sTemp = sTemp.replace('_',' ');
		  sTemp = sTemp.replace('-', ' ');
		  return sTemp;
	  }
	  
	  public void dumpData () {
		  System.out.println("Archive dump.");
		  System.out.println("Dir Originals: " + this.getDirOriginals());
		  System.out.println("Dir thumbnails :" + this.getDirThumbnail());
		  System.out.println("Dir web: " + this.getDirWeb());
		  System.out.println("Thumbnail column width: " + this.getThumbnailColumnWidth());
		  System.out.println("Thumails per row: " + this.getThumbnailsPerRow());
		  System.out.println("Title " + this.getTitle());
		  System.out.println("XML Filename: " + this.getXMLFilename());
		  System.out.println("XML Filename Title " + this.getXMLFilenameTitle());	  
	  }
}
