package cn.edu.nju.luckers.database_server.po;

public class Favorite {

	private Long id;
	
	private Long userid;

	private String favoriteStock;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserid() {
		return userid;
	}


	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public String getFavoriteStock() {
		return favoriteStock;
	}


	public void setFavoriteStock(String favoriteStock) {
		this.favoriteStock = favoriteStock;
	}


	
	public Favorite(Long userID, String favoriteStockList) {
		super();
		this.userid = userID;
		this.favoriteStock = favoriteStockList;
	}

	
	public Favorite(){}
	
	
	
}
