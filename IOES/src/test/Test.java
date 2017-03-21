package test;

import java.io.File;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//M6Listener m6Listener = new M6Listener();
		//m6Listener.saveFile("testxml", "");
		
		File file = new File("C:\\Users\\IBM_ADMIN\\Desktop\\New folder\\IB2B_JARS");
		for(File file1 : file.listFiles())
			System.out.println(file1.getAbsolutePath());
			//Amit Test
		 //File file = new File("C:\\IB2B_Lib");
		//for(File file1 : file.listFiles())
			//System.out.println(file1.getAbsolutePath());

	}

}
