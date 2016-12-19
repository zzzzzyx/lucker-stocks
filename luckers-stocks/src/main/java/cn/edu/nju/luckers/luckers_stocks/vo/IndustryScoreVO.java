package cn.edu.nju.luckers.luckers_stocks.vo;

public class IndustryScoreVO {

	private double riskScore;//风险性得分
	private double benScore;//盈利性得分
	private double marketScore;//市场性得分
	private double proScore;//前景行得分
	private double conScore;//稳定性得分
	
	private double finalScore;//综合得分

	public IndustryScoreVO(double[] scores) {
		riskScore = scores[0];
		benScore = scores[1];
		marketScore = scores[2];
		proScore = scores[3];
		conScore = scores[4];
		
		finalScore = scores[5];
	}
	
	
	public double getRiskScore() {
		return riskScore;
	}

	public double getBenScore() {
		return benScore;
	}

	public double getMarketScore() {
		return marketScore;
	}

	public double getProScore() {
		return proScore;
	}

	public double getConScore() {
		return conScore;
	}

	public double getFinalScore() {
		return finalScore;
	}
	
	
}
