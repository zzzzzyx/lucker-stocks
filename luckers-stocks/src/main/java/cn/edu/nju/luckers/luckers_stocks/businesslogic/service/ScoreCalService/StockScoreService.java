package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ScoreCalService;
/**
 * 该接口提供对股票得分的计算
 * 提供的数据包括：
 * 风险性、收益性、市场性、前景性
 * 所有的结果都使用5分制并且都是整数
 * @author NjuMzc
 *
 */
public interface StockScoreService {
	
	/**
	 * 风险性计算
	 * 该属性描述现在对该股票投资收益的可能性
	 * 计算公式:以最近的收盘价为基点，计算最近的40天数据（30天容易出现奇怪的bug）
	 * 在该收盘价以下的数据比例；
	 * 结果越高，风险性越大，评分越低
	 * 最终返回值是5-X*5取整，如果为0则加1
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public int calRisk(String id,String start,String end);
	
    /**
     * 盈利性计算
     * 该属性描述该该股票一个月内的增长额度
     * 基础分为3，涨幅10%以内加1，10%以上加2，跌则相反，最低分为1
     * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
     */
	public int calBenfit(String id,String start,String end);
	
	/**
	 * 市场性计算
	 * 该属性描述该股票在市场上交易的火热程度
	 * 计算方式为：换手率0-1.0之间为1分，1.0-1.8之间为2分，1.8到3.5为3分
	 *           3.5-5.8之间为4分，5.8以上为5分
	 *  换手率计算取40天内所有数据的平均值
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public int calMarket(String id,String start,String end);
	
	/**
	 * 投资价值计算
	 * 
	 * 该述性描述该股票的投资价值，主要依赖市盈率
	 * <0 ：指该公司盈利为负（因盈利为负，计算市盈率没有意义，所以一般软件显示为“—”） 1分
		0-13 ：即价值被低估 5分
		14-20：即正常水平 4分
		21-28：即价值被高估 3分
		28+ ：反映股市出现投机性泡沫  2分
		来自百度百科
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public int calProspect(String id,String start,String end);
	
	/**
	 * 稳定性计算
	 * 该属性描述股票的波动程度，计算方式为每10天取股价最大值最小值，（Xh-Xl）/Xl 求得波动程度，再取几何平均数
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public int calConsistency(String id,String start,String end);
}
