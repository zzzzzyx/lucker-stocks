package cn.edu.nju.luckers.database_server.main;

import cn.edu.nju.luckers.database_server.view.*;

public class ServerLauncher {
	public static void main(String[] args) {
		try {
			RMIBinding.bindAll();

			System.out.println("连接成功！");

//			server_frame = new ServerFrame();

		} catch (Exception e) {
			e.printStackTrace();
			server_frame = new ErrorFrame();
		}
	}

	public static ServerFrame server_frame;

}
