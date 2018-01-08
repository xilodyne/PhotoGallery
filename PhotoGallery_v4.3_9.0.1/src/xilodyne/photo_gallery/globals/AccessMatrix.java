package xilodyne.photo_gallery.globals;

import java.util.ArrayList;

/**
 * @author aholiday
 * 
 */
public class AccessMatrix {

	private static boolean[][] AccessMatrix;
	private static boolean bInited = false;
	private static ArrayList<String> XMLFiles;
	private static ArrayList<String> Users;
	private static String className = "";
	
	public AccessMatrix() {
		className = this.getClass().getName();
	}
	

	public static void createAccessMatrix(int iXML, int iUser) {
		if (!Globals.checkDataLoaded()) {
			createXMLFiles();
			createUsers();
			bInited = true;

			AccessMatrix = new boolean[iXML][iUser];

			// initialize array
			for (int x = 0; x < iXML; x++) {
				for (int y = 0; y < iUser; y++) {
					AccessMatrix[x][y] = false;
				}
			}
		}
	}

	public static boolean initedAccessMatrix() {
	//	className = this.getClass().getName();
		return bInited;
	}

	private static void createXMLFiles() {
		XMLFiles = new ArrayList<String>();
		for (int i = 0; i < Globals.propertiesAccessFiles.size(); i++) {
			XMLFiles.add(Globals.propertiesAccessFiles.getProperty(String
					.valueOf(i)));
		}
	}

	private static void createUsers() {
		Users = new ArrayList<String>();
		Logging.info(className, "Access Matrix, create usrs, # of users: "
				+ Globals.propertiesAccessUsers.size());
		for (int i = 0; i < Globals.propertiesAccessUsers.size(); i++) {
			Users.add(Globals.propertiesAccessUsers.getProperty(String
					.valueOf(i)));
		}
	}

	public static void updateAccessMatrix(int iXML, int iUser) {
		AccessMatrix[iXML][iUser] = true;
	}

	private static void updateAccessMatrixFalse(int iXML, int iUser) {
		AccessMatrix[iXML][iUser] = false;
	}

	public static void addXMLFiles(String sXML) {
		XMLFiles.add(sXML);
		appendToAccessMatrix();

		// set last row
		for (int y = 0; y < Users.size(); y++) {
			AccessMatrix[XMLFiles.size() - 1][y] = false;
		}
	}

	public static void addUsers(String sUsers) {
		Logging.info(className, "Adding users: " + sUsers);
		Users.add(sUsers);

		appendToAccessMatrix();

		// set last row
		for (int y = 0; y < Users.size(); y++) {
			AccessMatrix[XMLFiles.size() - 1][y] = false;
		}
		// set last col
		for (int y = 0; y < XMLFiles.size(); y++) {
			AccessMatrix[y][Users.size() - 1] = false;
		}
	}

	public static String getXMLFile(int iLocation) {
		return XMLFiles.get(iLocation);

	}

	public static String getUser(int iLocation) {
		return Users.get(iLocation);
	}

	public static boolean getCode(int iXML, int iUser) {
		return AccessMatrix[iXML][iUser];
	}

	public static int getUserSize() {
		int value = 0;
		try {
			value = Users.size();
		} catch (NullPointerException e) {
			Logging.error(e.getLocalizedMessage());
		}
		return value;
	}

	public static int getXMLSize() {
		int value = 0;
		try {
			value = XMLFiles.size();
		} catch (NullPointerException e) {
			Logging.error(e.getLocalizedMessage());
		}
		return value;
	}

	// find location of file in xmlfiles
	// remove xmlfiles
	// remove row from access matrix
	public static void deleteXMLFile(String sXMLFile) {
		boolean bExists = XMLFiles.contains(sXMLFile);

		if (bExists) {
			int iIndex = getIndexXMLFile(sXMLFile);
			removeXMLFromAccessMatrix(iIndex);
			XMLFiles.remove(iIndex);
		}
	}

	public static void deleteUsers(String sUsers) {
		boolean bExists = Users.contains(sUsers);

		if (bExists) {
			int iIndex = getIndexUsers(sUsers);
			Users.remove(iIndex);
			removeUsersFromAccessMatrix(iIndex);
		}
	}

	private static void removeXMLFromAccessMatrix(int iIndex) {
		int iXML = XMLFiles.size();
		int iUsers = Users.size();
//		int iCount = -1;  //array begins with zero
		int iCount = 0;  //array begins with zero
		Logging.info(className,"Remove: " + iIndex + ", XML size: " + iXML + ", # of Users: " + iUsers);
//		boolean[][] tempMatrix = new boolean[iXML][iUsers];
		boolean[][] tempMatrix = new boolean[iXML-1][iUsers];
		// initialize array
		for (int x = 0; x < iXML; x++) {

			Logging.info(className, "x: " + x +", iCount: " + iCount + ", remove: " + iIndex + ", != " + (x != iIndex));
			if (x != iIndex) {
			
				for (int y = 0; y < iUsers; y++) {
					tempMatrix[iCount][y] = AccessMatrix[x][y];
					Logging.info(className, iCount +":" + y+"  pull: " + x + ":"+y +" -" + AccessMatrix[x][y]);
				}
				
				iCount++;
			}
		}
		AccessMatrix = tempMatrix;
	}

	private static void removeUsersFromAccessMatrix(int iIndex) {
		int iXML = XMLFiles.size();
		int iUsers = Users.size();
		int iCount = -1;
		boolean[][] tempMatrix = new boolean[iXML][iUsers];
		// initialize array
		for (int x = 0; x < iXML; x++) {

			for (int y = 0; y < iUsers; y++) {

				if (iCount != iIndex)
					iCount++;

				tempMatrix[x][iCount] = AccessMatrix[x][y];
			}
		}
		AccessMatrix = tempMatrix;
	}

	public static int getIndexXMLFile(String sXMLFile) {
		int iCount = 0;

		iCount = XMLFiles.indexOf(sXMLFile);

		// if (iCount > 0)
		// iCount--;
		return iCount;
	}

	public static int getIndexUsers(String sUsers) {
		int iCount = 0;

		try {
			iCount = Users.indexOf(sUsers);
		} catch (NullPointerException e) {
			Logging.error(e.getLocalizedMessage());
		}

		// if (iCount > 0)
		// iCount--;
		return iCount;
	}

	public static boolean XMLFilesContains(String sXMLFile) {
		return XMLFiles.contains(sXMLFile);
	}

	public static boolean UsersContains(String sUsers) {
		return Users.contains(sUsers);
	}

	// if file doesn't exist, add it to both arrays
	// set row to false except for first column
	public static void makeXMLFilePublic(String sXMLFile) {
		boolean bExists = XMLFiles.contains(sXMLFile);

		if (bExists) {
			int iIndex = getIndexXMLFile(sXMLFile);
		//	removeXMLFromAccessMatrix(iIndex);  //not sure why I was doing this
			setRowToFalse(iIndex);
			updateFileAccess(Globals.access_PUBLIC, true, iIndex);
		} else {
			XMLFiles.add(sXMLFile);
			appendToAccessMatrix();
			updateFileAccess(Globals.access_PUBLIC, true, XMLFiles.size() - 1);
		}
	}

	// append row to end
	private static void appendToAccessMatrix() {
		int iXML = XMLFiles.size();
		int iUsers = Users.size();
		boolean[][] tempMatrix = new boolean[iXML][iUsers];
		// initialize array
		for (int x = 0; x < iXML - 1; x++) {
			for (int y = 0; y < iUsers; y++) {
				try {
					tempMatrix[x][y] = AccessMatrix[x][y];
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}
		}
		AccessMatrix = tempMatrix;
	}

	private static void setRowToFalse(int iRow) {
		for (int i = 0; i < Users.size(); i++) {
			AccessMatrix[iRow][i] = false;
		}
	}

	private static void setColToFalse(int iCol) {
		for (int i = 0; i < XMLFiles.size(); i++) {
			AccessMatrix[i][iCol] = false;
		}
	}

	// lookup user, get index, update index in accessmatrix
	private static void updateFileAccess(String sUsers, boolean bValue,
			int iXMLFile) {
		int iIndex = getIndexUsers(sUsers);
		AccessMatrix[iXMLFile][iIndex] = bValue;
	}

	// change all assigments to xmlfile
	public static void changeXMLFileAccessCodes(String sXMLFile,
			ArrayList<String> list) {
		int iRow = getIndexXMLFile(sXMLFile);
		setRowToFalse(iRow);
		for (int i = 0; i < list.size(); i++) {
			int iIndex = getIndexUsers((String) list.get(i));
			updateAccessMatrix(iRow, iIndex);
		}
	}

	// change which files code can access
	public static void changeAccessCodesToXMLFiles(String sCode,
			ArrayList<String> XMLFileList) {
		int iCol = getIndexUsers(sCode);

		setColToFalse(iCol);
		for (int i = 0; i < XMLFileList.size(); i++) {
			int iIndex = getIndexXMLFile((String) XMLFileList.get(i));
			updateAccessMatrix(iIndex, iCol);

			// User is taking over a public guest file so assign public guest =
			// false
			if (iCol != 0) {
				updateAccessMatrixFalse(iIndex, 0);
			}

		}
	}

}
