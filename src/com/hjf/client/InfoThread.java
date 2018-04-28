package com.hjf.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

//����������Ϣ���߳�
public class InfoThread extends Thread {
	Socket s;
	InputStream in;
	OutputStream out;
	JTextArea jta;
	ChatUI chatu;
//	String type;
	AddFriends adf;
	String myname;
	String strmz;

	// ���췽���ش����߳�������Ҫ������
	public InfoThread(Socket s, JTextArea jta, AddFriends adf, ChatUI chatu,String myname,String strmz) {
		this.s = s;
		this.jta = jta;
		this.adf = adf;
		this.chatu = chatu;
		this.myname=myname;
		this.strmz=strmz;
		try {
			in = s.getInputStream();
			out = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �̵߳ķ�����
	public void run() {
		// read����������������ʹ��while��true��
		while (true) {
			try {
				// �ȴ�������������Ϣ
				byte[] b = new byte[1024];
				in.read(b);
				String s = new String(b);
				char[] c = s.toCharArray();
				int one = -1;
				String type=null;
				/*
				 * ��ȡ�������Ϣ֮��ʼ�����ж� ���������| |ǰ�����Ϊtype����
				 */
				for (int i = 0; i < c.length; i++) {
					if (c[i] == '|') {
						// one�ǵ�һ��|��λ��
						one = i;
						type = new String(c, 0, i);
						break;
					}
				}	
				// ���������շ���Ϣ��ʱ��
				if (type.equals("send") || type.equals("receive")) {
					int two = -1;
					for (int i = one + 1; i < c.length; i++) {
						if (c[i] == '|' && two == -1) {
							// two�ǵڶ���|��λ��
							two = i;
							break;
						}
					}
					/*
					 * name�ǵڶ����ַ��� Ҳ���ǵ�һ��|�͵ڶ���|֮����ַ��� info�ǵ������ַ���
					 * Ҳ���ǵڶ���|�͵�����|֮����ַ���
					 */
					String name = new String(c, one + 1, two - one - 1);
					String info = new String(c, two + 1, c.length - two - 1);

					// ���Ƿ���Ϣ��ʱ��
					if (type.equals("send")) {
						// ���ı��������������Ϣ
						jta.append(name + ": " + chatu.info + "\n");
						// ��ʱ����Ϣ��ʱ��
					} else if (type.equals("receive")) {
						// ���ı��������������Ϣ
						jta.append(name + ": " + info + "\n");
					}
				}
				// ��������������ѵ�ʱ��
				if (type.equals("addfriendsuccess")) {
					// ����Ҫ�ӵ�һ��|��ʼѭ��
					for (int i = one + 1; i < c.length; i++) {
						if (c[i] == '|') {
							break;
						}
					}
					// ������ѳ���typeʱֻ��nameһ���ַ�������
					String name = new String(c, one + 1, c.length - one - 1);
					adf.jtf2.setText("���"+name.trim()+"�ɹ�");
					// ���������addfriendfail
				} else 
					if (type.equals("addfriendfail")) {
					adf.jtf2.setText("���ʧ��");
				}
				
				
				/*
				 * ����յ��ļ��Ľ�����Ϣ
				 * �Ϳ�ʼ�򿪽����ļ��Ķ˿�
				 * ���ҿ����˶˿ڵ��߳�
				 * ���������ļ�
				 */
				if(type.equals("receivefile")){
				int two = -1;
				for (int i = one + 1; i < c.length; i++) {
					if (c[i] == '|' && two == -1) {
						// two�ǵڶ���|��λ��
						two = i;
						break;
					}
				}
				/*
				 * name�ǵڶ����ַ��� Ҳ���ǵ�һ��|�͵ڶ���|֮����ַ��� info�ǵ������ַ���
				 * Ҳ���ǵڶ���|�͵�����|֮����ַ���
				 */
				String id = new String(c, one + 1, two - one - 1);
				String filename = new String(c, two + 1, c.length - two - 1);
//				OutputStream fom=null;
				Socket s2 = null;
				//��һ���¶˿�
				try {
					s2 = new Socket("192.168.31.146", 8889);
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				InfoThreadrecive2 inthre2=new InfoThreadrecive2(s2,myname,id,jta,filename);
				new Thread(inthre2).start();
				
				
//				try {
//					fom=new FileOutputStream(new File("D:\\4.png"));
////					int t=0;
////					while((t=in.read())!=-1){
////						fom.write(t);
////						System.out.println("t:  "+t);
////						fom.flush();
////					}	
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					byte[] k=new byte[100*1024];
//					int l=in.read(k);
//					while(l!=-1){
//						System.out.println("qeqwe");
//						fom.write(k, 0, l);
//						l=in.read(k);
//					}
//					fom.flush();
//					System.out.println("test");
//					fom.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
