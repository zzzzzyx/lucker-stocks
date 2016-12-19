package cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.service.StringStorageService;

public class StringStorageManager implements StringStorageService{

	private String filePath;

	/**
	 * @param filePath 无论读写都针对这个filePath操作，可以使用StringStorageService中已经写定的常量，也可以自己定义一个文件路径对去操作
	 * @throws IOException
	 */
	public StringStorageManager(String filePath) throws IOException {
		this.filePath = filePath;
		verifyFileExists(filePath);
	}

	@Override
	public void addString(String addContent) throws IOException {
		ArrayList<String> a = getContent();
		a.add(addContent);
		deleteAllAndWrite(a);
	}
	@Override
	public void deleteString(String deleteContent) throws IOException {
		ArrayList<String> s = getContent();
		s.remove(deleteContent);
		deleteAllAndWrite(s);
	}

	@Override
	public ArrayList<String> getContent() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(filePath));
        String temp=br.readLine();
        br.close();
        if(temp == null)
        	return new ArrayList<>();
		return ListToArrayList(temp.split(SPECIAL_SPLITER));
	}

	@Override
	public void deleteAllAndWrite(ArrayList<String> content) throws IOException {
		FileOutputStream out = new FileOutputStream(filePath);
		PrintStream p = new PrintStream(out);
		String input = String.join(SPECIAL_SPLITER, content);
		p.print(input);
		p.close();
	}
	
	private void verifyFileExists(String ff) throws IOException{
		File f = new File(ff);
		if (!f.exists()) {
			f.createNewFile();
		}
	}
	private ArrayList<String> ListToArrayList(String[] list){
		ArrayList<String> result = new ArrayList<String>();
		for(String s : list){
			result.add(s);
		}
		return result;
	}
}
