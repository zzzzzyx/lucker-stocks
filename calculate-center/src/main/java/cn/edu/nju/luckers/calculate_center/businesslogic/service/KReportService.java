package cn.edu.nju.luckers.calculate_center.businesslogic.service;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import cn.edu.nju.luckers.calculate_center.vo.KReportVO;

public interface KReportService {

	/**
	 * K线图分析函数
	 * 详情参见文档
	 * @param list 即用getKGraph获得的链表，放进去即可
	 * @return  KReportVO 调用getReport 即可获得String类分析结果
	 */
	public KReportVO getReport(ArrayList<KGraphPO> list);
}
