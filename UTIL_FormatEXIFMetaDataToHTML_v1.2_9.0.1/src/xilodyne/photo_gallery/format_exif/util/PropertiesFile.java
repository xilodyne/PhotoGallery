package xilodyne.photo_gallery.format_exif.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
	
	private FileInputStream userStream = null;
	private Properties propertiesUser = new Properties();

	
	public Properties readUserPropertyFile(String sPropFile) {
//		Logging.info(this.getClass().getName(), "Reading " + sPropFile);
		try {
			this.userStream = new FileInputStream(sPropFile);
			this.propertiesUser.loadFromXML(this.userStream);

//			Logging.info(this.getClass().getName(), "Size: " + propertiesUser.size());
			this.userStream.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return this.propertiesUser;
	}

}
