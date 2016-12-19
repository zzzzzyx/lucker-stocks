package cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.impl.StringStorageManager;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;

public abstract class LocalBufferHandler {
	protected static final String specialSpliter = StockBufferReader.specialSpliter;
	/**
	 * 
	 * @param filePath 缓存文件存储的路径
	 * @param identifier 所希望获取信息的标识符，比如股票代码“sh600763”
	 * @return 在路径下的缓存文件如果有此标识符对应的String，那么直接读取，否则调用readRemoteString方法返回String并将此标识符和返回值相对应地存储在本地
	 * @throws IOException
	 * @throws ConnectionFailure
	 */
	public String rawRead(String filePath, String identifier) throws IOException, ConnectionFailure {
		StringStorageManager manager = new StringStorageManager(filePath);
		ArrayList<String> s = manager.getContent();
		String info = readStockInfo(s, identifier);//在本地根据标识符搜索信息
		
		//如果找到了，就直接返回
		if(info != null){
			return info;
		}else{//如果没有找到，就连接到网络并搜索
			
			String result = readRemoteString();//用户定义的方法
			if(result == null || result.length() == 0){
				return null;
			}
			
			s.add(identifier + specialSpliter + result);
			manager.deleteAllAndWrite(s);
			return result;
		}
	}
	
	/**
	 * 告诉这个类该如何在系统没有找到对应的标识符的情况下如何在网络上找到信息
	 * @return
	 * @throws IOException
	 * @throws ConnectionFailure
	 */
	protected abstract String readRemoteString() throws IOException, ConnectionFailure;
	
	protected String readStockInfo(ArrayList<String> infoList, String identifier){
		for(String info : infoList){
			if(info.contains(identifier)){
				return info.split(specialSpliter)[1];
			}
		}
		return null;
	}
}
