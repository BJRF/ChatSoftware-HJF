package com.hjf.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class netlink extends Thread{
	//创建两个类的队列
	public ArrayList<Link> list = new ArrayList<Link>();
	public ArrayList<File> filelist = new ArrayList<File>();

	public  void link(int port) throws IOException {
		System.out.println("进入");
		ServerSocket server = new ServerSocket(port);
		 while (true) {
			Socket socket = server.accept();		
			Link link = new Link(socket, list);
			list.add(link);
			new Thread(link).start();
		}
	}
	
	public void run() {
		ServerSocket server=null;
		try {
			server = new ServerSocket(8889);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			Socket socket=null;
			try {
				socket = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
				File w=new File(filelist,socket);
				new Thread(w).start();
				filelist.add(w);			
		}
	}
	

	public static void main(String[] args) {
		try {
			netlink link=new netlink();
			link.start();
			link.link(8888);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
