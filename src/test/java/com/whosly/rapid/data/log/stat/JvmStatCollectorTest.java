package com.whosly.rapid.data.log.stat;

import org.junit.Test;

/**
 * JVM统计数据收集器测试用例
 *
 * @author yueny(yueny09@163.com)
 *
 */
public class JvmStatCollectorTest {

	// @Test
	// public void testMod() {
	// final String tradeNo = "20170824000001082471083273721200";
	// System.out.println(tradeNo % 32);
	// }

	@Test
	public void testRun() throws InterruptedException {
		final JvmStatCollector c = new JvmStatCollector();
		c.setInterval(5);
		c.start();

		Thread.sleep(10000);
		c.stop();
	}

}
