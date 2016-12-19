package cn.edu.nju.luckers.calculate_center.businesslogic.impl.KReportServer;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.service.KReportService;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import cn.edu.nju.luckers.calculate_center.vo.KReportVO;

public class KReportServer implements KReportService {

	public KReportVO getReport(ArrayList<KGraphPO> list) {
		
		String result = reportOne(list.get(list.size()-1));
		
		KReportVO vo  = new KReportVO(result);
		
		return vo;
	}
	
	
	/**
	 * 该方法报告单独K线图的特殊情况，包括：
	 * 大阴阳线（x43）,十字线（x0x），十字星（x1x），小兵（x23），吊颈（x21）
	 * @param po
	 * @return
	 */
	private String reportOne(KGraphPO po){
		
		String result = KReportTable.search(JudegeOne(po));
		return result;
		
	}
	
	
	
	/**
	 * 该方法将一天的K线图数据数字化模糊表达
	 * 第一位表示涨跌，1涨 0平 -1跌
	 * 第二位表示中间柱状图的长度，0为线，1为短（饼），2为中（短柱），3为长（长柱），4为全（表示柱状图占全线）
	 * 第三位表示柱状图的位置，1为底部，2为下部，3为中部，4为上部，5位顶部，0表示图形为一字
	 * @param po
	 * @return
	 */
	private String JudegeOne(KGraphPO po){
		String r = "0";//标记涨跌情况
		String length = "0";//标记开收差和最值差的相对长度
		String lo = "0";//标记位置
			
		//开收差
		double height1 = Math.abs(Double.valueOf(po.getOpen())-Double.valueOf(po.getClose()));
		//最高最低差
		double height2 = Math.abs(Double.valueOf(po.getHigh())-Double.valueOf(po.getLow()));
		double cha = 0;
		if(height2!=0){
			cha = height1/height2;
		}	
		double location =0;
		if(height2 == 0){
			location = 1;
		}else{
			location = ((Double.valueOf(po.getOpen())+Double.valueOf(po.getClose()))/2 - Double.valueOf(po.getLow()))/height2;

		}
		
		if(height2 == 0){
			length = "0";
		}else if(cha<0.05){
			//相当于一条线
			length = "0";
		}else if(cha<0.2){
			//短线
			length = "1";
		}else if(cha<0.5){
			//中线
			length = "2";
		}else if(cha<1){
			//长线
			length = "3";
		}else {
			//全线
			length = "4";
		}
			
		//判断涨跌
		if(Double.valueOf(po.getOpen())<Double.valueOf(po.getClose())){
			//涨，设置首位为1
			r = "1";
		}else if(Double.valueOf(po.getOpen())==Double.valueOf(po.getClose())){
			//等，设置首位为0
			r = "0";
		}else {
			//跌，设置首位为-1
			r = "-1";
		}
		
		//接下来标记位置
		if(location<0.2){
			//底部位置，标记为1
			lo = "1";
		}else if(location<0.4){
			//低位
			lo = "2";
		}else if(location<0.6){
			//中位
			lo = "3";
		}else if(location < 0.8){
			//高位
			lo = "4";
		}else if(location <1){
			//顶位
			lo = "5";
		}
		else {
			lo = "0";
		}
		
		
		return r+length+lo;
	}

}
