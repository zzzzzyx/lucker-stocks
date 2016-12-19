package cn.edu.nju.luckers.luckers_stocks.data.network.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;

public class JavaUrlConnecter{

	private static final String open_code_name = "X-Auth-Code";
	private static final String open_code_content = "7cabe81e5db8bbde1cfc683065099bf2";
	public static final String anyquant_url = "http://121.41.106.89:8010/api/";
	public static final String stockIndustryURL_head = "http://quotes.money.163.com/f10/hydb_";
	public static final String stockIndustryURL_tail = ".html#12a01";
	public static final String stock = "stock/";
	public static final String stockList = "stocks/";
	public static final String benchmark = "benchmark/";
	
	/**
	 * 程序中访问http数据接口
	 * @throws IOException 这个异常代表服务器无法读取此网页
	 * @throws ConnectionFailure 
	 */
	public static String getURLContent(String urlstr,String encoding) throws IOException, ConnectionFailure {
		/** 网络的url地址 */////
		URL url = null;
		/** http连接 */
		URLConnection httpConn = null;
		/** 输入流 */
		BufferedReader in = null;
		String result = null;
		StringBuilder r = new StringBuilder();
		try {
			url = new URL(urlstr);
//			System.out.println("connecting, info: " + urlstr);
			httpConn = url.openConnection();
			httpConn.setRequestProperty(open_code_name, open_code_content);
			InputStream is = httpConn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is,encoding));
			while ((result = in.readLine()) != null) {
				r.append(result);
			}
		}catch(Exception e){
			if(e.getMessage().startsWith("Server returned HTTP response code: 500 for URL")){
				System.out.println("Special error code: 500!!!");
			}
			throw new ConnectionFailure();
		}
		
		
		
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return r.toString();
	}

}
