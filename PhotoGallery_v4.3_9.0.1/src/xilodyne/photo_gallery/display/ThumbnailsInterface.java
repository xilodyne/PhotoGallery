package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.globals.Image;
import xilodyne.photo_gallery.http.ParameterPhoto;


public interface ThumbnailsInterface {
	public String dspThumbnails(ArchiveInfo archive, ParameterPhoto paramInfo);
	public Image getNextImage(ParameterPhoto paramInfo);
}
