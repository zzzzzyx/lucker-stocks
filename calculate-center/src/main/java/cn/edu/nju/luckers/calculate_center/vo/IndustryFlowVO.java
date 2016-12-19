package cn.edu.nju.luckers.calculate_center.vo;

public class IndustryFlowVO {
	
	private String name;
	private String flow;
	boolean io;
	
	public IndustryFlowVO(String name, String flow, boolean io) {
		super();
		this.name = name;
		this.flow = flow;
		this.io = io;
	}

	public String getName() {
		return name;
	}

	public String getFlow() {
		return flow;
	}

	public boolean isIo() {
		return io;
	}

}
