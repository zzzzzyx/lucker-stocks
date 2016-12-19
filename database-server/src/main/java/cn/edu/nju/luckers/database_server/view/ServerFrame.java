package cn.edu.nju.luckers.database_server.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 547984238582820324L;
	
	JLabel successHint;
	protected String centralHint;

	public ServerFrame(){
		setLayout(new BorderLayout());
		
		setTitle("Luckers Database Server");
		
		setCentralHint();
		
		successHint = new JLabel(centralHint,JLabel.CENTER);
		successHint.setFont(new Font("楷体",Font.BOLD,20));
		add(successHint,BorderLayout.CENTER);
		
		String ip = "本地ip地址：未知unknown！";
		try {
			ip = "本机ip地址：" + InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		JLabel ipHint = new JLabel(ip,JLabel.CENTER);
		add(ipHint,BorderLayout.SOUTH);
		
		int width = 270;
		this.setSize(width,(int)(width*0.618));
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
	}

	protected void setCentralHint() {
		centralHint = "<服务器启动成功！>";
	}

	public void print(String string) {
		successHint.setText(string);
		successHint.repaint();
		repaint();
	}
}
