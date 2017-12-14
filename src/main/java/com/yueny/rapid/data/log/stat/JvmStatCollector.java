package com.yueny.rapid.data.log.stat;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JVM统计数据收集器
 *
 * @author yueny(yueny09@163.com)
 *
 */
public class JvmStatCollector extends AbstractStatCollector {
	private static final Logger logger = LoggerFactory
			.getLogger("JVM-STAT-LOGGER");

	/**
	 * 记录当前JVM情况（单位MB），格式如下： [(JVM堆内内存)(JVM堆外内存)(线程)]
	 * [(已用,初始分配,分配,最大)(已用,初始分配,分配,最大)(当前线程数,线程数峰值,启动过的线程总数)]
	 */
	@Override
	public void run() {
		final MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		logger.info("[({})({})({})]",
				getMemoryUsage(memBean.getHeapMemoryUsage()),
				getMemoryUsage(memBean.getNonHeapMemoryUsage()),
				getThreadStatistic(threadBean));
	}

	private String getMemoryUsage(final MemoryUsage usage) {
		return StringUtils.join(
				new String[] { Long.toString(usage.getUsed() / MB),
						Long.toString(usage.getInit() / MB),
						Long.toString(usage.getCommitted() / MB),
						Long.toString(usage.getMax() / MB) }, ",");
	}

	private String getThreadStatistic(final ThreadMXBean threadBean) {
		return StringUtils
				.join(new String[] {
						Long.toString(threadBean.getThreadCount()),
						Long.toString(threadBean.getPeakThreadCount()),
						Long.toString(threadBean.getTotalStartedThreadCount()) },
						",");
	}
}
