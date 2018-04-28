package com.hjf.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JLabel;

public class AddFriends implements ActionListener {
	Socket s;
	OutputStream out;
	InputStream in;
	javax.swing.JTextField jtf1;
	javax.swing.JTextField jtf2;
	String type;

	public AddFriends(Socket s) {
		this.s = s;
		try {
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void AddFriendsUI() {
		// �����������
		javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.setSize(320, 200);
		jf.setTitle("��Ӻ���");
		// �����˳�����
		jf.setDefaultCloseOperation(2);
		// ������ʾ
		jf.setLocationRelativeTo(null);
		// ��ʽ���ֹ�����
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// ���ô���Ϊ��ʽ����
		// ��ǩ
		JLabel jlb1 = new JLabel("��������Ҫ��ӵ��û�ID �� ");
		jf.add(jlb1);
		// �ı���
		jtf1 = new javax.swing.JTextField();
		java.awt.Dimension d1 = new java.awt.Dimension(200, 30);
		jtf1.setPreferredSize(d1);
		jf.add(jtf1);
		// ��ť
		javax.swing.JButton jbu = new javax.swing.JButton("���");
		java.awt.Dimension d = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d);
		jf.add(jbu);// �Ѱ�ť��ӵ�������
		jbu.addActionListener(this);
		// �ı���
		jtf2 = new javax.swing.JTextField();
		java.awt.Dimension d2 = new java.awt.Dimension(250, 30);
		jtf2.setPreferredSize(d2);
		jf.add(jtf2);
		jtf2.setEditable(false);
		// ���ô���ɼ�
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// ��������Ӻ���
		if (e.getActionCommand().equals("���")) {
			String id;
			id = jtf1.getText();
			try {
				// �ȴ�����������������Ϣ
				// ��Ҫ�ӵĶ����ID����������
				out.write(("addfriend|" + id + "\r").getBytes());
				out.flush();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	// public static void main(String[] args) {
	// new AddFriends().AddFriendsUI();
	// }
}
