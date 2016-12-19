package cn.edu.nju.luckers.calculate_center.po;
/**
 * 新闻的po对象
 * @author NjuMzc
 *
 */
public class NewsPO {

	private String title;
	private String url;
	private String date;
	
	public NewsPO(String title,String date,String url){
		this.title = title;
		this.date = date;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getDate() {
		return date;
	}
	
	
}
