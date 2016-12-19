package cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.driver;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.impl.StringStorageManager;
import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.service.StringStorageService;

public class StringReader_Driver {

	public static void main(String[] args) {
		try {
			StringStorageService manager = new StringStorageManager(StringStorageService.FAVORITES_FILE_PATH);
			manager.addString("hahaha");
			manager.addString("fucker");
			manager.addString("Mr.bitch");
			System.out.println(manager.getContent());
			manager.deleteString("fucker");
			System.out.println(manager.getContent());
			manager.addString("Michaerrrrr Jackson");
			System.out.println(manager.getContent());
			manager.deleteAllAndWrite(new ArrayList<>());
			manager.addString("Licolnnnn");
			System.out.println(manager.getContent());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
