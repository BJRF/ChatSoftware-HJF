package com.hjf.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		File f1=new File("D:\\1.jpg");
		File f2=new File("D:\\2.jpg");
         InputStream in=new FileInputStream(f1);
         OutputStream out=new FileOutputStream(f2);
		
		
		int t=0;
		while((t=in.read())!=-1){
			out.write(t);
		}
		out.flush();
		out.close();
		in.close();
		
	}

}
