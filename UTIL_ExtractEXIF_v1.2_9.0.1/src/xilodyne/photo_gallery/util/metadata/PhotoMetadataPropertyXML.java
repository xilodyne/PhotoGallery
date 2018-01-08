package xilodyne.photo_gallery.util.metadata;

import java.util.Date;
import java.io.File;
import java.text.DateFormat;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class PhotoMetadataPropertyXML {
	
	/* create a property file
	 * <properties>
<comment>Wed Aug 13 18:50:08 CEST 2008</comment>
<entry key="ADMIN_PASSWORD">changeme</entry>
<entry key="HTML_FOREGROUND_COLOR">white</entry>
</properties>

	 */
	
	public String getXMLMetadata(String sTempFileName) {
		  Metadata metadata = null;
		  Date today = new Date();
		  String sDate = DateFormat.getDateTimeInstance().format(today);
		  
	        try {
	            metadata = ImageMetadataReader.readMetadata(new File(sTempFileName));
	        } catch (Exception e) {
	            e.printStackTrace(System.err);
	            System.exit(1);
	        }

	        // iterate over the exif data and print to System.out
	        Iterator<?> directories = metadata.getDirectories().iterator();
        	StringBuffer sbTemp = new StringBuffer();
        	
        	sbTemp.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        	sbTemp.append("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n");
        	sbTemp.append("<properties>\n");
        	sbTemp.append("<comment>" + sDate + "</comment>\n");
    
 	        while (directories.hasNext()) {
	            Directory directory = (Directory)directories.next();
	            Iterator<?> tags = directory.getTags().iterator();
	            while (tags.hasNext()) {
	                Tag tag = (Tag)tags.next();
	                try {
	                	//System.out.println(directory.getName() +":"+tag.getTagName());
	                    if (directory.getName().toLowerCase().startsWith("exif")) {	                  	
		                	if ((!directory.getName().equalsIgnoreCase("exif thumbnail")) &&
		                		(!directory.getName().equalsIgnoreCase("exif unknown")) &&
	                			(!directory.getName().equalsIgnoreCase("exif date/time digitized"))){
	                    	sbTemp.append("<entry key=\"" + tag.getTagName().trim() + "\">");
		                	sbTemp.append(tag.getDescription().trim());
		                	sbTemp.append("</entry>\n");
	                		}
	                	}
	                	
	                } catch (Exception e) {
	                    System.err.println(e.getMessage());
	                    System.err.println(tag.getDirectoryName() + " " + tag.getTagName() + " (error)");
	                }
	            }
	            if (directory.hasErrors()) {
	            	Iterator<?> errors = directory.getErrors().iterator();
	                while (errors.hasNext()) {
	                	errors.next();
	                //    System.out.println("ERROR: " + errors.next());
	                }
	            }
	        }
	        
	        sbTemp.append("</properties>\n");
	        
	        return sbTemp.toString();
	    }
	}


