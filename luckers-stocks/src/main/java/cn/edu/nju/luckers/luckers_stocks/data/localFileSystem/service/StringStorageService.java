package cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.service;

import java.io.IOException;
import java.util.ArrayList;

public interface StringStorageService {
	public final static String SPECIAL_SPLITER = " ---- ";
	public static final String FAVORITES_FILE_PATH = "local/favorites.sb";

	/**
	 * 基于此类的deleteAllAndWrite方法编写，并不会提高效率，增加大量内容请尽量使用deleteAllAndWrite方法以提高效率
	 * 注意：content的内容不得包含SPECIAL_SPLITER中的内容，否则会发生解析文件内容异常
	 * @param addContent
	 * @throws IOException
	 */
	public void addString(String addContent) throws IOException;
	
	/**
	 * 基于此类的deleteAllAndWrite方法编写，并不会提高效率
	 * @param deleteContent
	 * @throws IOException
	 */
	public void deleteString(String deleteContent) throws IOException;

	/**
	 * 获取存储在本地的内容
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> getContent() throws IOException;

	/**
	 * 此方法将存储所有的ArrayList中的String
	 * 注意：content的内容不得包含SPECIAL_SPLITER中的内容，否则会发生解析文件内容异常
	 * @param content
	 * @throws IOException
	 */
	public void deleteAllAndWrite(ArrayList<String> content) throws IOException;
}
