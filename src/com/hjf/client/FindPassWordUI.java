package com.hjf.client;

import javax.swing.JLabel;

public class FindPassWordUI {
	public void showFrame() {
		// �����������
		javax.swing.JFrame jf = new javax.swing.JFrame();
		jf.setSize(320, 200);
		jf.setTitle("�һ�����");
		// �����˳�����
		jf.setDefaultCloseOperation(2);
		// ������ʾ
		jf.setLocationRelativeTo(null);
		// ��ʽ���ֹ�����
		java.awt.FlowLayout f = new java.awt.FlowLayout();
		jf.setLayout(f);// ���ô���Ϊ��ʽ����
		// ��ǩ
		JLabel jlb1 = new JLabel("�����û���");
		jf.add(jlb1);
		// �ı���
		javax.swing.JTextField jtf = new javax.swing.JTextField();
		java.awt.Dimension d = new java.awt.Dimension(200, 30);
		jtf.setPreferredSize(d);
		jf.add(jtf);
		// ��ǩ
		JLabel jlb2 = new JLabel("�����ܱ�    ");
		jf.add(jlb2);
		// �ı���
		javax.swing.JTextField jtf2 = new javax.swing.JTextField();
		java.awt.Dimension d3 = new java.awt.Dimension(200, 30);
		jtf2.setPreferredSize(d3);
		jf.add(jtf2);
		// ��ť
		javax.swing.JButton jbu = new javax.swing.JButton("�һ�");
		java.awt.Dimension d2 = new java.awt.Dimension(200, 30);
		jbu.setPreferredSize(d2);
		jf.add(jbu);// �Ѱ�ť��ӵ�������
		// �ı���
		javax.swing.JTextField jtf3 = new javax.swing.JTextField();
		java.awt.Dimension d4 = new java.awt.Dimension(250, 30);
		jtf3.setPreferredSize(d4);
		jf.add(jtf3);
		jtf3.setEditable(false);
		// ���ô���ɼ�
		jf.setVisible(true);
	}
	// public static void main(String[] args) {
	// new FindPassWordUI().showFrame();
	// }
}
