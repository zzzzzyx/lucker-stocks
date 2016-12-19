package cn.edu.nju.luckers.calculate_center.ml;

/*
 * @auther:LiuXing
 * @date:2016.5.17
 * @apply:拉普拉斯修正的朴素贝叶斯修正应用在股票数据
 * @limit:股票的数据项有10项，且全部为连续值属性,包括开盘价、收盘价、最高价、最低价、涨跌幅、成交量、
 * 换手量、市盈率、市净率,第10项为类别：0为看跌，1为看涨
 */
public class Bayes {

	private double data[][];
	private int numOfClass0;
	private int numOfClass1;
	private double dataOfClass0[][];
	private double dataOfClass1[][];
	private double testData[];
 
	public Bayes(double stockData[][], double testStockData[]) {
		// 初始化数据
		data = new double[stockData.length][10];
		dataOfClass0 = new double[stockData.length][10];
		dataOfClass1 = new double[stockData.length][10];
		testData = new double[9];
		numOfClass0 = 0;
		numOfClass1 = 0;

		for (int i = 0; i < stockData.length; i++) {
			for (int j = 0; j < stockData[0].length; j++) {
				data[i][j] = stockData[i][j];
			}
		}

		for (int i = 0; i < testStockData.length; i++) {
			testData[i] = testStockData[i];
		}

		// 对数据分类
		for (int i = 0; i < data.length; i++) {
			if (data[i][9] == 0) {
				// 负类，表示为跌
				for (int j = 0; j < data[i].length; j++) {
					dataOfClass0[numOfClass0][j] = data[i][j];
				}
				numOfClass0++;
			} else {
				// 正类，表示为涨
				for (int j = 0; j < data[i].length; j++) {
					dataOfClass1[numOfClass1][j] = data[i][j];
				}
				numOfClass1++;
			}
		}
	}

	public double BayesClassify() {

		// 拉普拉斯修正的先验概率
		double pc0 = 1.0*(numOfClass0 + 1) / (numOfClass0 + numOfClass1 + 2);
		double pc1 = 1.0*(numOfClass1 + 1) / (numOfClass0 + numOfClass1 + 2);

		// 基于属性独立假设，计算类属性条件概率
		double con_class0[] = new double[9];
		double con_class1[] = new double[9];

		// 负类
		for (int i = 0; i < 9; i++) {
			double att[] = new double[dataOfClass0.length];
			for (int j = 0; j < dataOfClass0.length; j++) {
				att[j] = dataOfClass0[j][i];
			}
			double attMean = mean(att);
			double attVar = variance(att);
			con_class0[i] = computeContinousAttr(testData[i], attMean, attVar);
		}

		// 正类
		for (int i = 0; i < 9; i++) {
			double att[] = new double[dataOfClass1.length];
			for (int j = 0; j < dataOfClass1.length; j++) {
				att[j] = dataOfClass1[j][i];
			}
			double attMean = mean(att);
			double attVar = variance(att);
			con_class1[i] = computeContinousAttr(testData[i], attMean, attVar);
		}

		double result0 = pc0;
		double result1 = pc1;
		for (int i = 0; i < 9; i++) {
			result0 += (Math.log(con_class0[i]));
			result1 += (Math.log(con_class1[i]));
		}
		
		if (result0 > result1) {
			return -1;
		} else {
			return 1;
		}

	}

	private double computeContinousAttr(double attribute, double mean, double var) {
		double exp = -(attribute - mean) * (attribute - mean) / (2 * var);
		return 1.0 / (Math.sqrt(2 * Math.PI * var)) * Math.pow(Math.E, exp);
	}

	/*
	 * 均值
	 */
	private static double mean(double input[]) {
		double sum = 0;
		for (int i = 0; i < input.length; i++) {
			sum += input[i];
		}
		return sum / input.length;
	}

	/*
	 * 方差
	 */
	private static double variance(double input[]) {
		double meanOfInput = mean(input);
		double sum = 0;
		for (int i = 0; i < input.length; i++) {
			sum += ((input[i] - meanOfInput) * (input[i] - meanOfInput));
		}
		return sum / input.length;
	}
}
