package cn.edu.nju.luckers.calculate_center.businesslogic.test;

import java.io.IOException;

import com.sun.javafx.font.LogicalFont;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.KReportServer.KReportServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KReportService;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.server.KGraphDataProvider;

public class KReportTest {

	public static void main(String[] args) throws IOException, ConnectionFailure {
		// TODO Auto-generated method stub

		KReportService service = LogicController.getKReporter();
		
		KGraphDataProvider provider = new KGraphDataProvider();
		
		System.out.println(service.getReport(provider.getDayGraph("sh600000")).getReport());
	}

}
