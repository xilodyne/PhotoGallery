package xilodyne.photo_gallery.display;

import xilodyne.photo_gallery.globals.ArchiveInfo;
import xilodyne.photo_gallery.http.ParameterPhoto;

public interface PhotoInterface {
	public String dspImage (ArchiveInfo archive, ParameterPhoto paramInfo);
	public String dspEXIFData (ArchiveInfo archive, ParameterPhoto paramInfo);
	public String dspDownload (ArchiveInfo archive, ParameterPhoto paramInfo);
//	public int getIndex (ParameterPhoto paramInfo);
//	public Image getImageAtIndex(ParameterPhoto paramInfo, int index);
	
}
