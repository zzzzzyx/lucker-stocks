package cn.edu.nju.luckers.calculate_center;

import junit.framework.Assert;
import junit.framework.TestCase;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.MyStrategyServer;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;

public class MyStrategyTest extends TestCase {

	MyStrategyServer service;
	Strategy s;
	boolean expectedTure;
	boolean expectedFalse;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = new MyStrategyServer();
		s = new Strategy();
		s.setPrice_in_low(5);
		s.setPrice_in_high(10);
		s.setPrice_out_low(12);
		s.setPrice_out_high(15);
		expectedTure = true;
		expectedFalse = false;
	}

	public void testVertify_1() {
		Assert.assertEquals(expectedTure, service.vertify(s));
	}

	public void testVertify_2() {
		s.setPrice_out_low(9);
		Assert.assertEquals(expectedFalse, service.vertify(s));
	}

	public void testVertify_3() {
		s.setPrice_out_low(3);
		Assert.assertEquals(expectedFalse, service.vertify(s));
	}

	public void testVertify_4() {
		s.setPrice_out_low( 3);
		s.setPrice_out_high(4);
		Assert.assertEquals(expectedTure, service.vertify(s));
	}

	public void testVertify_5() {
		s.setPrice_out_low( 3);
		s.setPrice_out_high(6);
		Assert.assertEquals(expectedFalse, service.vertify(s));
	}

	public void testVertify_6() {
		s.setPrice_out_low( 3);
		s.setPrice_out_high(16);
		Assert.assertEquals(expectedFalse, service.vertify(s));
	}

}
