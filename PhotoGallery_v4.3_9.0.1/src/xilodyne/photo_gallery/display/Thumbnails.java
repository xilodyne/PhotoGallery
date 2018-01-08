package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.http.ParameterPhoto;

import java.util.Enumeration;

public class Thumbnails implements ThumbnailsInterface {
	// based upon the xml file
	// show all thumbnails


	boolean openImageVector = false;
	Enumeration<?> loop;


	@Override
	public String dspThumbnails(ArchiveInfo archive, ParameterPhoto paramInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public Image getNextImage(ParameterPhoto paramInfo) {
		Image tempImage = null;

		if (!this.openImageVector) {
			this.openImageVector = true;
			loop = paramInfo.getImageList().elements();
		}

		if (loop.hasMoreElements()) {
			tempImage = ((Image) loop.nextElement());
		}

		return tempImage;
	}

	
	


}
