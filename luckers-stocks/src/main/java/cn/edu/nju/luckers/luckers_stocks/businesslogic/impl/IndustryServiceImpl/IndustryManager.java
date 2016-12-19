package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.IndustryServiceImpl;

import java.util.HashMap;

import cn.edu.nju.luckers.luckers_stocks.vo.IndustryVO;

/**
 * 该类负责管理行业记录
 * 提供静态方法
 * 这是一个单例
 * @author NjuMzc
 *
 */
public class IndustryManager {
	
	private static HashMap<String,IndustryVO> recorder;
	
	private static IndustryManager Singlation = null;
	
	private IndustryManager(){
		recorder = new HashMap<>();
	}
	
	public static IndustryManager getInstence(){
		if(Singlation == null){
			Singlation = new IndustryManager();
		}
		
		return Singlation;
	}
	
	/**
	 * 添加新的行业
	 * 如果已经有了，将返回false，添加成功则返回true
	 * @param vo
	 * @return
	 */
	public boolean addIndustry(IndustryVO vo){
		
		//如果已经存在，不再添加
		if(recorder.get(vo.getName())!=null){
			return false;
		}else{
			recorder.put(vo.getName(), vo);
			return true;
		}
	}
	
	/**
	 * 更新一个行业的信息
	 * 如果行业不存在，则返回false
	 * @param vo
	 * @return
	 */
	public boolean updateIndustry(IndustryVO vo){
		
		if(recorder.get(vo.getName())==null){
			return false;
		}else{
			recorder.remove(vo.getName());
			recorder.put(vo.getName(),vo);
			return true;
		}
		
	}
	
	/**
	 * 获得现在已有的行业列表
	 * @return
	 */
	public HashMap<String,IndustryVO> getIndustryList(){
		return recorder;
	}
	
	/**
	 * 检查一个行业是否已经在列表之中
	 * true则有
	 * @param name
	 * @return
	 */
	public boolean contains(String name){
		return recorder.containsKey(name);
	}
	
	/**
	 * 通过名字获得该行业的vo对象
	 * 如果不存在，则返回null
	 * @param name
	 * @return
	 */
	public IndustryVO getIndustry(String name){
		return recorder.get(name);
	}
	

}
