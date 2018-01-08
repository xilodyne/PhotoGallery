package xilodyne.photo_gallery.util.marquee;

import java.io.File;
import java.util.Random;

public class GetRandomMarqueePhoto {
	/*
	 * Receive:  source folder, marquee folder, xml file name
	 * 
	 * Do the following:  in source folder, get list of all .jpg files
	 * 			count number .jpg files
	 * 			generate a random number from 1 to total_count_jpg files
	 * 			copy file associated to random number to 
	 * 				marquee folder with filename marquee_xml file name.jpg
	 */

	//getDirlist
	//count dir list
	//get random number
	//copy file to folder

	private String sourceFolder;
	//private String destFolder;
	//private String xmlFileName;
	//private int randomNumber;
	private File filePath;
	private File[] files;


	public GetRandomMarqueePhoto (String source) {
		this.sourceFolder = source;
		filePath = new File(sourceFolder);
		files = filePath.listFiles();


	}


	private int getDirCount() {
		int count=0;

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File fileList = files[i];

				if (!(fileList.isDirectory())) {  
					//check if ".jpg" in filename
					if (fileList.getName().toLowerCase().lastIndexOf(".jpg") > 0) {
						count++;
					} 
				}
			}
		}

		return count;
	}
	
	private String getFile(int fileNumber) {
		int loop=0;
		int count=0;
		boolean bContinue = true;
		
		String fileName = "";
		if (files != null) {
			while ((loop < files.length) && bContinue ) {
				File fileList = files[loop];

				if (!(fileList.isDirectory())) {  
					//check if ".jpg" in filename
					if (fileList.getName().toLowerCase().lastIndexOf(".jpg") > 0) {
						count++;
						if (count == fileNumber) {
						//	filePath = fileName.getPath();
							fileName = fileList.getName();
						//	System.out.println("File path: " + fileName.getPath());
						//	System.out.println("File name: " + fileName.getName());
							
							bContinue = false;
						}
					} 
				}
				loop++;
			}
		}
		return fileName;
	}
	
	private int getRandomNumber (int fileCount) {
	    Random randomGenerator = new Random();
	    //gets a number between 0..N, = 0..fileCount-1, return 1..FileCount
	    return (randomGenerator.nextInt(fileCount -1)) + 1;

	}



	public String copyMarqueePhoto() {
		//System.out.println("Count of " + this.sourceFolder + " is ");
		int fileNumber = this.getDirCount();
		int random = this.getRandomNumber(fileNumber);

		//System.out.println("Random number : " + random + " 1.." + fileNumber);
		//String foundFile = this.getFile(random);
		
		//System.out.println("file found: " + foundFile );
		
		return this.getFile(random);
	}

}
