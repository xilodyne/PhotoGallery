package xilodyne.photo_gallery.fileio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import xilodyne.photo_gallery.globals.Globals;
import xilodyne.photo_gallery.globals.Logging;

public class HSQLDB {
	
	//only instantiated by Globals class and called this time
//	public HSQLDB() {
//		makeDBConnection();
//	}
	// Connection c = DriverManager.getConnection("jdbc:hsqldb:res:org.my.path.resdb", "SA", "");
	//C:\Backup\JavaCode\Java8-Luna\Projects\Web\PhotoGalleryProjects\PhotoGallery-v4.1(hashDB)\database\dbfilesProd

	static Connection con = null;
	
	public static void makeDBConnection()  {
		 try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		     return;
		 }
		 
		 try {
			Logging.info("HSQLDB", "Opening DB: " + Globals.dbPath);

			//if already have a connection then do ignore
			if (con == null) {
	//			con = DriverManager.getConnection("jdbc:hsqldb:file:" + Globals.dbPath , "SA", "");
				con = DriverManager.getConnection("jdbc:hsqldb:file:" + Globals.dbPath  + ";user=SA;ifexists=true");
			System.out.println("Init DB connection");
			}
			System.out.println("DB Connection made: " + con.getCatalog());

		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
//			Connection c = DriverManager.getConnection("jdbc:hsqldb:res:PhotoGallery-v4.1(hashDB)/database/dbfilesProd/gallery", "SA", "");


//		 Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
	}
	
	public static synchronized String getXMLFile(String sPhoto) {
		PreparedStatement getXMLFile = null;

		String sqlQuery = "select xml_file from gallery where image='" +
				sPhoto +"'";

		String sResult = "9999";

	    try {
	    	
	    	if (con == null) {
	    		System.out.println("Con is null");
	    		makeDBConnection();
	    	}

	        getXMLFile = con.prepareStatement(sqlQuery);

	        ResultSet result = getXMLFile.executeQuery();
	        if (result.next()) {
	        sResult = result.getString("xml_file");
	        } else {
	        	System.out.println("nothing found in SQL");
	        }
	        
	    } catch (SQLException e ) {
	    	e.printStackTrace();
	    }
	    return sResult;
	}
}
