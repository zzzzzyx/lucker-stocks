package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.MyStrategyService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.global.DataKey;
import cn.edu.nju.luckers.calculate_center.vo.StockInformVO;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyStrategyServer implements MyStrategyService {

	JSONArray array;
	JSONObject member;

	public MyStrategyServer() {
		array = new JSONArray();
	}

	/*
	 * 验证整个策略的合理性
	 */
	public boolean vertify(Strategy strategy) {

		boolean isOK = false;

		if (fourVertify(strategy.getPrice_in_low(), strategy.getPrice_in_high(), strategy.getPrice_out_low(),
				strategy.getPrice_out_high())) {
			isOK = isOK || true;
		}

		if (fourVertify(strategy.getVolume_in_low(), strategy.getVolume_in_high(), strategy.getVolume_out_low(),
				strategy.getVolume_out_high())) {
			isOK =  true;
		}

		if (fourVertify(strategy.getTurnover_in_low(), strategy.getTurnover_in_high(), strategy.getTurnover_out_low(),
				strategy.getTurnover_out_high())) {
			isOK = true;
		}

		if (fourVertify(strategy.getPe_in_low(), strategy.getPe_in_high(), strategy.getPe_out_low(),
				strategy.getPe_out_high())) {
			isOK =  true;
		}

		if (fourVertify(strategy.getPb_in_low(), strategy.getPb_in_high(), strategy.getPb_out_low(),
				strategy.getPb_out_high())) {
			isOK = true;
		}

		return isOK;
	}

	/*
	 * 验证一组属性值的合理性
	 */
	private boolean fourVertify(double in_low, double in_high, double out_low, double out_high) {

		// 组内比较
		if (in_low > in_high || out_low > out_high) {
			return false;
		}

		if ((out_low < in_high && out_low >= in_low) || (out_high < in_high && out_high >= in_low)
				|| (out_low <= in_low && out_high >= in_high)) {
			return false;
		}

		return true;
	}

	/*
	 * 策略应用在历史数据 策略各属性必须有下限(0)和上限(max) 初始本金为：100,000元
	 */
	public JSONArray applyOnHistory(Strategy strategy) {

		array.clear();
		StockInformService stockService = LogicController.getInformService();
		
		ArrayList<StockInformVO> stockInforList = stockService.getInform(strategy.getStockId(),
				strategy.getApply_start(), strategy.getApply_end());

		double money_start = 100000;
		double money_current = money_start;
		int stock_current = 0;
		boolean isPoor = false;
		double yesterday = 0;
		DecimalFormat df = new DecimalFormat("0.00");

		for (int i = 0; i < stockInforList.size(); i++) {
			StockInformVO vo = stockInforList.get(i);
			double price_today = Double.parseDouble(vo.getItem(DataKey.open));
			double volume_today = Double.parseDouble(vo.getItem(DataKey.volume));
			double turnover_today = Double.parseDouble(vo.getItem(DataKey.turnover));
			double pe_today = Double.parseDouble(vo.getItem(DataKey.pe_ttm));
			double pb_today = Double.parseDouble(vo.getItem(DataKey.pb));

			// 买入条件
			if ((strategy.getPrice_in_low() < price_today && price_today < strategy.getPrice_in_high())
					&& (strategy.getVolume_in_low() < volume_today && volume_today < strategy.getVolume_in_high())
					&& (strategy.getTurnover_in_low() < turnover_today
							&& turnover_today < strategy.getTurnover_in_high())
					&& (strategy.getPb_in_low() < pb_today && pb_today < strategy.getPb_in_high())
					&& (strategy.getPe_in_low() < pe_today && pe_today < strategy.getPe_in_high())) {
				if (!isPoor) {
					isPoor = true;
					stock_current = (int) (money_current
							/ Double.parseDouble(stockInforList.get(i).getItem(DataKey.open)));
					money_current = 0;
					System.out.println(
							"===========================买入：如今" + stock_current + "股" + " 金额:" + money_current + "元");
				}
			}

			// 卖出条件
			if ((strategy.getPrice_out_low() < price_today && price_today < strategy.getPrice_out_high())
					&& (strategy.getVolume_out_low() < volume_today && volume_today < strategy.getVolume_out_high())
					&& (strategy.getTurnover_out_low() < turnover_today
							&& turnover_today < strategy.getTurnover_out_high())
					&& (strategy.getPb_out_low() < pb_today && pb_today < strategy.getPb_out_high())
					&& (strategy.getPe_out_low() < pe_today && pe_today < strategy.getPe_out_high())) {
				if (isPoor) {
					isPoor = false;
					money_current = stock_current * Double.parseDouble(vo.getItem(DataKey.close));
					stock_current = 0;
					System.out.println("===========================卖出：如今" + stock_current + "股" + "   账面余额:"
							+ money_current + "元");

				}
			}

			member = new JSONObject();
			member.put("date", vo.getItem(DataKey.date));
			double ClosePrice = Double.parseDouble(vo.getItem(DataKey.close));
			double benfitAll = money_current + stock_current * ClosePrice - 100000;
			member.put("allBenefit", df.format(benfitAll));
			double benefitSingle = benfitAll - yesterday;
			yesterday = benfitAll;
			member.put("single", df.format(benefitSingle));
			array.add(member);
		}
 
		strategy.setBenefit(Double.parseDouble(df.format(yesterday)));
		
		return array;
	}

}
