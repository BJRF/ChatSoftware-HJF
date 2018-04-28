package com.hjf.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Test {

	public static void main(String[] args) throws Exception {
	File f1=new File ("D:\\À¶½Ü\\1.jpg");
	File f2=new File ("D:\\À¶½Ü\\2.jpg");
	
	InputStream in=new FileInputStream (f1 );
	OutputStream out=new FileOutputStream (f2);
	int t;
	while((t=in.read())!=-1) {
         out.write(t);	
	}
	out.flush();
	in.close();
	out.close();
	

	}

}
