package com.hjf.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JLabel;

public class RegisterUI implements ActionListener {
	String name,id;
	String password;
	OutputStream out;
	InputStream in;
	Socket s;
	javax.swing.JTextField jtf;
	javax.swing.JTextField jtf2;
	javax.swing.JTextField jtf3;
	javax.swing.JTextField jtf4;
	javax.swing.JTextField jtf5;

	public RegisterUI(Socket s) {
		try {
			this.s = s;
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void showFrame() {
		// �����������
		javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.setSize(320, 260);
		jf.setTitle("ע�����");
		// �����˳�����
		jf.setDefaultCloseOperation(2);
		// ������ʾ
		jf.setLocationRelativeTo(null);
		// ��ʽ���ֹ�����
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// ���ô���Ϊ��ʽ����
		// ��ǩ
		JLabel jlb = new JLabel("  ����ID       ");
		jf.add(jlb);
		// �ı���
		jtf5 = new javax.swing.JTextField();
		java.awt.Dimension d1 = new java.awt.Dimension(200, 30);
		jtf5.setPreferredSize(d1);
		jf.add(jtf5);
		// ��ǩ
		JLabel jlb1 = new JLabel("�����û���");
		jf.add(jlb1);
		// �ı���
		jtf = new javax.swing.JTextField();
		java.awt.Dimension d = new java.awt.Dimension(200, 30);
		jtf.setPreferredSize(d);
		jf.add(jtf);
		// ��ǩ
		JLabel jlb2 = new JLabel("��������    ");
		jf.add(jlb2);
		// �ı���
		jtf2 = new javax.swing.JTextField();
		java.awt.Dimension d3 = new java.awt.Dimension(200, 30);
		jtf2.setPreferredSize(d3);
		jf.add(jtf2);
		// ��ǩ
		JLabel jlb4 = new JLabel("�����ܱ�    ");
		jf.add(jlb4);
		// �ı���
		jtf4 = new javax.swing.JTextField();
		jtf4.setPreferredSize(d3);
		jf.add(jtf4);
		// ��ť
		javax.swing.JButton jbu = new javax.swing.JButton("ע��");
		java.awt.Dimension d2 = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d2);
		jf.add(jbu);// �Ѱ�ť��ӵ�������
		// �ı���
		jtf3 = new javax.swing.JTextField();
		java.awt.Dimension d4 = new java.awt.Dimension(250, 30);
		jtf3.setPreferredSize(d4);
		jf.add(jtf3);
		jtf3.setEditable(false);
		jbu.addActionListener(this);
		// ���ô���ɼ�
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ע��")) {
			id=jtf5.getText();
			name = jtf.getText();
			password = jtf2.getText();
			// �ж�������û����������Ƿ�Ϊ��
			if (name == null || name.trim().equals("")) {
				jtf3.setText("�û�������Ϊ��\n");
			}
			if (password == null || password.trim().equals("")) {
				jtf3.setText("���벻��Ϊ��\n");
			}
			if (id == null || id.trim().equals("")) {
				jtf3.setText("ID����Ϊ��\n");
			}
			// ��������ǿգ��������������
			if (name != null && !name.trim().equals("") && password != null
					&& !password.trim().equals("")&&id != null && !id.trim().equals("")) {
				try {
					String strmz = name;
					String strmm = password;

					// �û��������봫��������
					out.write(("register" + "|"+id + "|" + strmz + "|" + strmm + "\r")
							.getBytes());
					out.flush();
					/*
					 * ����������������������Ӧ�ö��������ж��Ƿ�˽������
					 */
					String RegisterResult = "";
					byte[] b = new byte[1024];
					in.read(b);
					RegisterResult = new String(b).trim();
					System.out.println(RegisterResult);
					// ����õ��������ǳɹ�
					if (RegisterResult.equals("success")) {
						jtf3.setText("�����û��ɹ�");
					}
					// ����õ��ķ�����ʧ��
					if (RegisterResult.equals("fail")) {
						jtf3.setText("�����û�ʧ��");
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
}
