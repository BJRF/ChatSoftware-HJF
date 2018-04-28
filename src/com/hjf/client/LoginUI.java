package com.hjf.client;

import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LoginUI implements ActionListener {
	Graphics g;
	String id;
	String password;
	OutputStream out;
	InputStream in;
	Socket s;
	TextField tf;
	TextField tf2;
	Checkbox cb;
	Checkbox cb2;
	File f = new File("F:\\Java\\io\\client.txt");
	File f1 = new File("F:\\Java\\io\\client1.txt");
	int n;
	javax.swing.JFrame jf;
	List<String> liststr = new ArrayList<String>();

	public LoginUI() {
		try {
			s = new Socket("192.168.31.146", 8888);
			out = s.getOutputStream();
			System.out.println(out);
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public LoginUI(List<String> liststr) {
		this.liststr = liststr;
	}

	public void showFrame() {
		// �����������
		jf = new javax.swing.JFrame();
		jf.setSize(320, 390);
		jf.setTitle("��¼����");
		// �����˳�����
		jf.setDefaultCloseOperation(3);
		// ������ʾ
		jf.setLocationRelativeTo(null);
		// ��ʽ���ֹ�����
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// ���ô���Ϊ��ʽ����
		// ��ͼƬ
		javax.swing.ImageIcon im = new javax.swing.ImageIcon(this.getClass()
				.getResource("��¼ͼ.jpg"));
		// ��ǩ
		javax.swing.JLabel jla = new javax.swing.JLabel(im);
		java.awt.Dimension di = new java.awt.Dimension(300, 200);
		jla.setPreferredSize(di);
		jf.add(jla);
		// �������˺�����ǰ�ȶ�ȡ������������ݣ����Ƿ����ϴα�����˺ź�����
		try {
			fileInput(this.f);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// �ı���
		tf = new TextField(26);
		tf.setText(id);
		jf.add(tf);
		// ע���˺Ű�ť
		javax.swing.JButton jbu1 = new javax.swing.JButton("ע���˺�");
		jf.add(jbu1);// �Ѱ�ť��ӵ�������
		// �ı���
		tf2 = new TextField(26);
		tf2.setText(password);
		tf2.setEchoChar('*');
		jf.add(tf2);
		// �һ����밴ť
		javax.swing.JButton jbu2 = new javax.swing.JButton("�һ�����");
		jf.add(jbu2);
		// ��ѡ��
		cb = new Checkbox("��ס����");
		jf.add(cb);
		cb2 = new Checkbox("�Զ���¼");
		jf.add(cb2);
		// ��ȡio���б����n��ֵ
		try {
			fileInput1(f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ����nֵ�жϸ�ѡ��״̬
		 */
		// ���֮ǰ��������Զ���¼
		if (n == 1) {
			cb2.setState(true);
			id = tf.getText();
			password = tf2.getText();
			try {
				// �û��������봫��������
				String strzh = id;
				String strmm = password;
				out.write(("login|" + strzh + "|" + strmm + "\r").getBytes());
				System.out.println("name" + id);
				System.out.println("password" + password);
				out.flush();
				// ������������������
				String rdlongin = "";
				byte[] b = new byte[1024];
				in.read(b);
				rdlongin = new String(b).trim();
				System.out.println("rdlongin:" + rdlongin);
				if (rdlongin.equals("success")) {
					in.read(b);
					while (!new String(b).trim().equals("end")) {
						in.read(b);
						liststr.add(new String(b).trim());
					}
					for (int i = 0; i < liststr.size(); i++) {
						System.out.println(liststr.get(i));
					}
					new ChatUI(s, id, liststr).UI();
				} else {
					System.out.println("��¼ʧ��");
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		// ���֮ǰ����Ĳ����Զ���¼
		if (n == 0) {
			cb2.setState(false);
		}
		// ��ť
		javax.swing.JButton jbu = new javax.swing.JButton("��¼");
		java.awt.Dimension d2 = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d2);
		jf.add(jbu);// �Ѱ�ť��ӵ�������
		// ���ô���ɼ�
		jf.setVisible(true);
		g = jf.getGraphics();
		g.drawImage(im.getImage(), 0, 0, 200, 200, null);
		jbu.addActionListener(this);
		jbu1.addActionListener(this);
	}

	/*
	 * ��������ʱ�õ�io�������˺ź�����
	 */
	// io���������
	public void fileOutput(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);
		int length = id.length();
		dos.writeByte(length);
		for (int i = 0; i < length; i++) {
			dos.writeChar(id.charAt(i));
		}
		int length1 = password.length();
		dos.writeByte(length1);
		for (int i = 0; i < length1; i++) {
			dos.writeChar(password.charAt(i));
		}
		fos.flush();
		fos.close();
	}

	// io����������
	public void fileInput(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		int length = dis.readByte();
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < length; i++) {
			strB.append(dis.readChar());
		}
		String name = new String(strB);
		this.id = name;
		int length1 = dis.readByte();
		StringBuffer strB1 = new StringBuffer();
		for (int i = 0; i < length1; i++) {
			strB1.append(dis.readChar());
		}
		String password = new String(strB1);
		this.password = password;
		n = dis.readInt();
		fis.close();
	}

	/*
	 * �Զ���¼ʱ�õ�io��
	 */
	// io���������
	public void fileOutput1(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);
		dos.writeByte(n);
		fos.flush();
		fos.close();
	}

	// io����������
	public void fileInput1(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		int n = dis.readByte();
		this.n = n;
		fis.close();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("��¼")) {
			id = tf.getText();
			password = tf2.getText();
			try {
				// �û��������봫��������
				String strzh = id;
				String strmm = password;
				out.write(("login|" + strzh + "|" + strmm + "\r").getBytes());
				System.out.println("name" + id);
				System.out.println("password" + password);
				out.flush();
				/*
				 *  ������������������
				 *  ��ֻ��һ��
				 */
				String rdlongin = "";
				byte[] b = new byte[1024];
				in.read(b);
				rdlongin = new String(b).trim();
				BufferedReader read=new BufferedReader(new InputStreamReader(in));
				String p=null;
				if (rdlongin.equals("success")) {
					//��ȡ����
					p=read.readLine();
					System.out.println("��ȡ���ѵĵ�һ��"+p);
					//�����������һֱ��
					while (!p.trim().equals("end")) {
						liststr.add(p.trim());
						p=read.readLine();
						System.out.println("    "+p);
					}
//					System.out.println("ִ��");
					//��ӡ��ȡ���ĺ���
					for (int i = 0; i < liststr.size(); i++) {
						System.out.println(liststr.get(i));
					}
					new ChatUI(s, id, liststr).UI();
					jf.dispose();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		if (e.getActionCommand().equals("ע���˺�")) {
			new RegisterUI(s).showFrame();
		}
		// ��ѡ���ס����ʱ
		if (cb.getState()) {
			try {
				fileOutput(f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// ��ѡ���Զ���¼ʱ�Ͳ�ѡ���Զ���¼ʱ
		if (cb2.getState()) {
			n = 1;
			try {
				fileOutput1(f1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (!cb2.getState()) {
			n = 0;
			try {
				fileOutput1(f1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new LoginUI().showFrame();
	}
}
