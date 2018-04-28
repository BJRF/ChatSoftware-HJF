package com.hjf.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class File implements Runnable {
   
	public ArrayList<File> filelist ;
	Socket socket=null;
	int id=0;
	OutputStream out = null;
	BufferedReader read = null;
	InputStream in =null;
	int targetid=0;
	String filename;


	public File(ArrayList<File> filelist, Socket socket) {
		super();
		this.filelist = filelist;
		this.socket = socket;
		try {
			this.out =  socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in=socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.read = new BufferedReader(new InputStreamReader(in));
	}
	public void run() {
		String s=null;
		try {
			 s=read.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		char[] c = s.toCharArray();
		int one = -1;
//		int two = -1;
		// 找到第一个标识符
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '|') {
				one = i;
				break;
			}
		}
		//找到第二个标识符
//		for (int i = one + 1; i < c.length; i++)
//			if (c[i] == '|') {
//				two = i;
//		}
		
		id=Integer.parseInt(new String(c,0,one));
		targetid=Integer.parseInt(new String(c, one + 1, c.length - one - 1));
//		filename=new String(c, two + 1, c.length - two - 1);	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("test1");
		System.out.println("id:  "+id +"    targetid   "+targetid);
		//如果目标id存在而且在线
		if(targetid!=0) {
		try {
		for(int i=0;i<filelist.size();i++) {
			System.out.println(i);
		if(targetid==filelist.get(i).id) {
        	 System.out.println("开始发信息");
        	 System.out.println("test2");
//        	 filelist.get(i).out.write((filename+"|"+"/r/n").getBytes());
        	 byte[] b=new byte[1000*1024];
        	 int l = in.read(b);
        	 while(l!=-1) {
        		 filelist.get(i).out.write(b,0,l);
//        		 out.write(b,0,l);
        		 l = in.read(b);
        		 System.out.println("test3");
        	 }
        	  socket.shutdownOutput();        	        	 
        	 
        	break; 
        }
	
		}
		}catch(Exception e) {
			e.printStackTrace();	
			for(int i=0;i<filelist.size();i++) {
				if(targetid==filelist.get(i).id||id==filelist.get(i).id) {
					filelist.remove(i);
					i--;
				}
			}
			
		}
		for(int i=0;i<filelist.size();i++) {
			if(targetid==filelist.get(i).id||id==filelist.get(i).id) {
				filelist.remove(i);
				i--;
			}
		}
		}
	}
}