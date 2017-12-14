package com.yueny.rapid.data.log.stat;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.management.UnixOperatingSystemMXBean;

/**
 * 操作系统统计数据收集器
 *
 * @author yueny(yueny09@163.com)
 *
 */
public class OsStatCollector extends AbstractStatCollector {
	private static final Logger logger = LoggerFactory
			.getLogger("OS-STAT-LOGGER");

	/**
	 * 记录当前系统情况（单位MB），格式如下： [(CPU)(内存)]
	 * [(Load)(空余物理内存,总物理内存,空余交换空间,总共交换空间,分配虚拟内存)]
	 */
	@Override
	public void run() {
		final OperatingSystemMXBean osBean = ManagementFactory
				.getOperatingSystemMXBean();
		logger.info("[({})({})]",
				Double.toString(osBean.getSystemLoadAverage()),
				getSystemMemory(osBean));
	}

	private String getSystemMemory(final OperatingSystemMXBean osBean) {
		if (osBean instanceof UnixOperatingSystemMXBean) {
			final UnixOperatingSystemMXBean uosBean = (UnixOperatingSystemMXBean) osBean;
			return StringUtils
					.join(new String[] {
							Long.toString(uosBean.getFreePhysicalMemorySize()
									/ MB),
							Long.toString(uosBean.getTotalPhysicalMemorySize()
									/ MB),
							Long.toString(uosBean.getFreeSwapSpaceSize() / MB),
							Long.toString(uosBean.getTotalSwapSpaceSize() / MB),
							Long.toString(uosBean
									.getCommittedVirtualMemorySize() / MB) },
							",");
		}
		return "-";
	}
}
