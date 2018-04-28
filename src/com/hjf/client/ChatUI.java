package com.hjf.client;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatUI extends JFrame implements ActionListener {
	// ��������
	JTextField jtf1;
	JTextField jtf2;
	// �ı���
	JTextArea jta;
	Socket s;
	OutputStream out;
	InputStream in;
	// ����Ϣ�ߵ�����
	String name;
	String info;
	// �Լ�������
	String myname;
	//ѡ���
	Choice c,c1;
	// �������
	JScrollPane jsp;
	// ���
	JPanel j;
	//���ֵĶ���
	List<String> liststr;
	//�ļ�
//	File f =new File("D:\\3.png");
	/*
	 * ��Ϊ���еĽ�����Ϣ��Ҫ��ͬһ���̣߳�����Ҫ�ȴ�����Ӻ��ѽ������
	 */
	AddFriends adf;

	public ChatUI() {
	}
	public ChatUI(Socket s, String name,List<String> liststr) {
		try {
			this.liststr=liststr;
			this.s = s;
			this.myname = name;
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void UI() {
		this.setLayout(new BorderLayout());
		// �ı���
		jta = new JTextArea();
		// �������
		jsp = new JScrollPane(jta);
		// �ı����򲻿ɱ༭
		jta.setEditable(false);
		j = new JPanel();
		// ��������
		jtf1 = new JTextField(30);
		JButton jb;
		jb = new JButton("����");
		// Ϊ��ť����¼�
		jb.addActionListener(this);
		// ����������Ϣ���ı�
		Label la1 = new Label("������Ϣ");
		j.add(la1);
		j.add(jtf1);
		j.add(jb);
		// �ļ�ѡ���İ�ť
		JButton jb1;
		jb1 = new JButton("ѡ���ļ�");
		j.add(jb1);
		jb1.addActionListener(this);
		JButton jb2;
		jb2 = new JButton("��Ӻ���");
		j.add(jb2);
		jb2.addActionListener(this);
		// �������������ı�
		Label la2 = new Label("�������");
		j.add(la2);
		//������ʾ���ѵ�ѡ���
		c1=new Choice();
		for(int i=0;i<liststr.size();i++){
			c1.add(liststr.get(i));
		}
		j.add(c1);
		// ����������������
		jtf2 = new JTextField(6);
		j.add(jtf2);
		//����Ⱥ��˽�ĵ�ѡ���
		c = new Choice();
		c.add("Ⱥ��");
		c.add("˽��");
		j.add(c);
		setSize(10, 50);
		this.add(jsp, BorderLayout.CENTER);
		this.add(j, BorderLayout.SOUTH);
		this.setTitle("���촰��");
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		adf = new AddFriends(s);
		InfoThread inth = new InfoThread(s, jta, adf, this,myname,name);
		inth.start();
	}

	public void actionPerformed(ActionEvent e) {
		// �������Ӻ��ѵ�ʱ��
		if (e.getActionCommand().equals("��Ӻ���")) {
			adf.AddFriendsUI();
		}
		// ������ļ���ʱ��
		if (e.getActionCommand().equals("ѡ���ļ�")) {
			String filename = null;
			String FILE;
			JFileChooser jfc = new JFileChooser();
			/*
			 * ѡ���ļ���ģʽ
			 * ֱѡ�ļ����߿���ѡ��Ŀ¼�����ļ�
			 */
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
			//���ļ�ѡ���һ�����沢����
		    jfc.showDialog(new JLabel(), "ѡ��");  
		    File file=jfc.getSelectedFile();  
//		    if(file.isDirectory()){  
//		    System.out.println("�ļ���:"+file.getAbsolutePath()); 
//		    }
		    if(file.isFile()){  
		       FILE=file.getAbsolutePath(); 
		       System.out.println(FILE);
		       filename=jfc.getSelectedFile().getName();
		       System.out.println(filename);
		    }    
		    //���ļ������id
		    String strid = jtf2.getText();
		    /*
		     * �ȸ���������һ�����б�ʶ���������ļ����ֵ���Ϣ
		     * �÷��������µĶ˿ڲ����淢��ȥ���ļ���
		     */
		    try {
				out.write(("sendfile" + "|" + strid + "|"+filename+"\r\n")
						.getBytes());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Socket s1 = null;
			/*
			 * ֮��ͻ��˴�һ���µĶ˿�
			 * ����˿������ȷ���Ϣ���������Ľ����ļ��Ķ˿�
			 * ����������˿ڵ��߳�
			 * ��ʼ�����ļ���������
			 */
			try {
				s1 = new Socket("192.168.31.146", 8889);
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			InfoThread2 inth2=new InfoThread2(s1,strid,myname,filename);
			new Thread(inth2).start();
		    
			
			//��
//			name = jtf2.getText();
//			String strmz = name;
//			try {
//				out.write(("sendfile" + "|" + strmz + "\r\n")
//						.getBytes());
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			
//			Socket s1 = null;
//			try {
//				s1 = new Socket("192.168.31.182", 8889);
//			} catch (UnknownHostException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			InfoThread2 inth2=new InfoThread2(s1,strmz,myname);
//			new Thread(inth2).start();
			
			
			
			
//				InputStream fim=null;
//			try {
//				fim=new FileInputStream(f);
//				byte[] k=new byte[100*1024];
//				int l=fim.read(k);
//				while(l!=-1){
//				out.write(k,0,l);
//				l=fim.read(k);
//				System.out.println("...............................");
//				}
//				System.out.println("............");
//				fim.close();
//				s.shutdownOutput();	
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			
			
			
		}
		// ��������͵�ʱ��
		if (e.getActionCommand().equals("����")) {
			info = jtf1.getText();
			name = jtf2.getText();
			// Ⱥ��
			if (c.getSelectedIndex() == 0) {
				// �ж��������Ϣ�Ƿ�Ϊ��
				if (info == null || info.trim().equals("")) {
					jta.append("������Ϣ����Ϊ��" + "\n");
				}
				// ��������ı���������Ϊ��
				jtf1.setText("");
				// ���������Ϣ���ǿգ�������������������
				if (info != null && !info.trim().equals("")) {
					try {
						// �Լ������ֺͷ��Ͷ�������ֺ���Ϣ����������
						String strxx = info;
						out.write(("all" + "|" + strxx + "\r").getBytes());
						out.flush();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
			// ˽��
			if (c.getSelectedIndex() == 1) {
				// �ж��������Ϣ�Ƿ�Ϊ��
				if (info == null || info.trim().equals("")) {
					jta.append("������Ϣ����Ϊ��" + "\n");
				}
				// �ж��������������Ƿ�Ϊ��
				if (name == null || name.trim().equals("")) {
					jta.append("���������Ϊ��" + "\n");
				}
				jtf1.setText("");
				// �������������Ϣ����Ϊ��
				if (info != null && !info.trim().equals("") && name != null
						&& !name.trim().equals("")) {
					try {
						// �Լ������ֺͷ��Ͷ�������ֺ���Ϣ����������
						name = jtf2.getText();
						String strmmz = myname;
						String strmz = name;
						String strxx = info;
						out.write(("single" + "|" + strmz + "|" + strxx + "\r")
								.getBytes());
						System.out.println("�ҵ�����" + strmmz + "����");
						out.flush();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		}
	}
//	 public static void main(String[] args) {
//	 new ChatUI().UI();
//	 }
}