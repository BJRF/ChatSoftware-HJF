package com.hjf.Server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class Link implements Runnable {
	Socket socket = null;
	String name = null;
	int id;
	ArrayList<Link> list = null;
	OutputStream out = null;
	BufferedReader read = null;
	InputStream in =null;
	int targetid;
	
	//���췽��
	public Link(Socket socket, ArrayList<Link> list) {
		this.socket = socket;
		try {
			this.out =  socket.getOutputStream();
			in=socket.getInputStream();
			this.read = new BufferedReader(new InputStreamReader(in));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.list = list;
	}
	//�������
	public void chatprocess() {
		 // ���ѹ���
		 try {
			 //��ȡ����
		 readFriend();
		 } catch (Exception e1) {
		 e1.printStackTrace();
		 }
		// ���д����쳣��Ϊ������ǰѭ��
		loop: while (true) {
			targetid = 0;
			try {
				System.out.println("�ȴ�" + name + "��Ϣ������");

				String s = null;
					// ��ȡ��Ϣ
				//	s = Read(in);
				s=read.readLine();
				
				System.out.println(s);
				if (s.equals("bye"))
					return;
				char[] c = s.toCharArray();
				int one = -1;
				// �ҵ���һ����ʶ��
				for (int i = 0; i < c.length; i++) {
					if (c[i] == '|') {
						one = i;
						break;
					}
				}

				// if (one < 1||one==c.length) {
				// try {
				// out.write("fail\r".getBytes());
				// continue loop;
				// } catch (IOException e) {
				// e.printStackTrace();
				// continue loop;
				// }
				// }

				String type = new String(c, 0, one);
				System.out.println(type);
				// Ⱥ��
				if (type.equals("all")) {
					String msg = new String(c, one + 1, c.length - one - 1);

					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).name != name) {
							list.get(i).out.write(("receive|" + name + '|' + msg + '\r').getBytes());
						}

					}
					continue loop;

				}
				// ˽��
				else if (type.equals("single")) {
					int two = -1;
					for (int i = one + 1; i < c.length; i++)
						if (c[i] == '|') {
							two = i;
						}
					if (two == one + 1 || two < 0 || two == c.length) {
						continue loop;
					}
					String target = new String(c, one + 1, two - one - 1);
					String msg = new String(c, two + 1, c.length - two - 1);
					System.out.println(target);
					System.out.println(msg);
					// ������Ϣ
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).name.equals(target)) {
							list.get(i).out.write(("receive|" + name + '|' + msg + '\r').getBytes());
							out.write(("send|" + name + "|" + "success\r").getBytes());
							continue loop;

						}
					}
					try {
						out.write(("send|" + target + "|" + "fail\r").getBytes());
						continue loop;
					} catch (IOException e) {
						e.printStackTrace();
						continue loop;
					}

				}
				
				
				// ��Ӻ���
				else if (type.equals("addfriend")) {
					String friendid = new String(c, one + 1, c.length - one - 1);
					addFriend(Integer.parseInt(friendid));
				}
				//�����ļ�
				else if(type.equals("sendfile")) {
					int two = -1;
					for (int i = one + 1; i < c.length; i++)
						if (c[i] == '|') {
							two = i;
					}
					if (two == one + 1 || two < 0 || two == c.length) {
						continue loop;
					}
					targetid = Integer.parseInt(new String(c, one + 1, two - one - 1));
					System.out.println(targetid);
					String filename = new String(c, two + 1, c.length - two - 1);
					System.out.println(filename);
					//��Ŀ��id�����ļ�
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).id == targetid) {
					    list.get(i).out.write(("receivefile"+'|'+Integer.toString(id)+"|"+filename+'\r').getBytes());
						System.out.println("����");
						}
					}
				}
			}
			catch (SocketException  e) {
				e.printStackTrace();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).name == name) {
						list.remove(i);
					}
				}

				System.out.println(name + "�����˳�");
				return;
			} catch (IOException  e) {
				e.printStackTrace();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).name == name) {
						list.remove(i);
					}
				}

				System.out.println(name + "�����˳�");
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	final String d = "com.mysql.jdbc.Driver";
	final String url = "jdbc:mysql://localhost/csserver";
	final String user = "root";
	final String password = "123456";
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet rs = null;

	// ��¼����ע��
	public void before() throws Exception {

		String s = null;
		try {
			//����
			s = read.readLine();
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//��ʼ���ݱ�ʶ�����ַ���������Ϣ
		char[] c = s.toCharArray();
		int one = -1;
		int two = -1;
		int three = -1;
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '|' && one == -1)
				one = i;
			else if (c[i] == '|' && two == -1) {
				two = i;

			} else if (c[i] == '|') {
				three = i;
				break;
			}
		}
		if (two == -1 || one < 2 || two - one < 2 || c.length == two) {
			try {
				out.write("fail\r".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			//���ʧ�ܾ����¿�ʼ�����������
			before();
		}
		System.out.println(one + ".." + two);
		String first = new String(c, 0, one);
		System.out.println(first);
		String second = new String(c, one + 1, two - one - 1);
		System.out.println(second);
		String third = new String(c, two + 1, c.length - two - 1);
		System.out.println(third);

		//�����һ���ؼ����ǵ�¼
		if (first.equals("login")){
			System.out.println("�յ���¼����");
			System.out.println(second);
			login(Integer.parseInt(second), third);
		}
		//�����һ���ؼ�����ע��
		else if (first.equals("register")) {
//			register(Integer.parseInt(second), new String(c, two + 1, three - two - 1),
//					new String(c, three + 1, c.length - three - 1));
			register(new String(second), new String(c, two + 1, three - two - 1),
					new String(c, three + 1, c.length - three - 1));
		} else {
			//����Ȳ��ǵ�¼Ҳ����ע��˵����Ϣ�����������¿�ʼ�η���������Ϣ
			try {
				out.write("fail\r".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			before();
		}
	}

	// ��¼
	public void login(int id, String pass) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			/*
			 *���������һ��id�����Ѿ����������е�id
			 *�������ظ���¼
			 *�������½��ܲ�return
			 */
			if (list.get(i).id == id) {
				before();
				return;
			}
		}
		
		try {
			Class.forName(d);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			//�ҵ���id��Ӧ��password��name
			System.out.println("���ݿ�");
//			System.out.println(id);
			state = conn.prepareStatement("select password,name from userinfo where id=" + id);
			rs = state.executeQuery("select password,name from userinfo where id=" + id);
		} catch (Exception e) {
			e.printStackTrace();
			out.write("fail\r".getBytes());
			before();
			return;

		}
		try {

			//���rs��password�����û�����password
//			System.out.println(rs.getString("password"));
			if (rs.next() && pass.equals(rs.getString("password"))) {
				System.out.println(id+"��¼success");
				//��ͻ��˷��ͳɹ�
				out.write("success\r".getBytes());
				//���ݿ��id��name��������������id��name
				this.id = id;
				this.name = rs.getString(2);
				return;

			} else {
				System.out.print("fail");
				out.write("fail\r".getBytes());
				before();
				return;
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();

		}

		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).name == name) {
					list.remove(i);
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ע��
	public void register(String id, String name, String pass) throws Exception {
//		String friend=null;
		try {
			Class.forName(d);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			state = conn.prepareStatement("insert into userinfo(id,password,name) values('" + id + "','" + pass + "','" + name + "')");
			int result = state.executeUpdate();
			if (result == 1) {
				out.write("success\r".getBytes());
				before();
				return;
			} else {
				out.write("fail\r".getBytes());
				before();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.write("fail\r".getBytes());
			before();
			return;
		}
	}

	// ��ȡ����
	public void readFriend() throws Exception {
		try {
			Class.forName(d);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			state = conn.prepareStatement("select friendid,id from friend,userinfo where friend.myid=" + id+" and friendid=userinfo.id");
			rs = state.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("end".getBytes());
		}

		while (rs.next()) {
//			out.write(( rs.getString("name")+"("+Integer.toString(rs.getInt("friendid"))+ ')' + "\r").getBytes());
			out.write((rs.getString("friendid")+ "\r").getBytes());

		}
		out.write("end\r".getBytes());
	}
    //�Ӻ���
	public void addFriend(int friendid) throws Exception {
//		System.out.println(friendid);
		if (friendid != id) {
			state = conn.prepareStatement("select friendid  from friend where myid=" +id+" and friendid="+friendid);
			rs=state.executeQuery();
			state = conn.prepareStatement("select id  from userinfo where id=" +friendid);
			boolean flag1=state.execute();
//			System.out.println("flag1"+flag1);
			
			if(!rs.next()&&flag1) {
				System.out.println("���ԼӺ���");
			try {
				Class.forName(d);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				state = conn.prepareStatement(
						"insert into friend(myid,friendid) values("+id+","+friendid+")");
				state.execute();
				state = conn.prepareStatement(
						"insert into friend(myid,friendid) values("+friendid+","+id+")");
				state.execute();
				rs = state.executeQuery("select name from userinfo where id=" + friendid);
				rs.next();
				out.write(("addfriendsuccess" + "|" + rs.getString("name")).getBytes());
				conn.close();
				state.close();
				rs.close();

			} catch (Exception e) {
				state = conn.prepareStatement("rollback");
				state.execute();
				e.printStackTrace();
				out.write("addfriendfail".getBytes());
				return;
			}
		}
		} else {
			out.write("addfriendfail".getBytes());
		}

	}

	// �߳�
	public void run() {

		try {
			// ��¼����ע��

			before();

			// ����
			chatprocess();
		} catch (Throwable e) {
			// δ֪����ֱ���˳�
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).name == name) {

					list.remove(i);
				}
			}
			try {
				socket.close();
				out.close();
				read.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			System.out.println(name + "�����˳�");
			e.printStackTrace();
			return;
		}

	}
}
