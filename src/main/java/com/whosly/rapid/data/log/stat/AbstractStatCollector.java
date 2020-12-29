package com.whosly.rapid.data.log.stat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 抽象统计数据收集器
 *
 * @author yueny(yueny09@163.com)
 *
 */
public abstract class AbstractStatCollector extends TimerTask {
	/**
	 * 1MB的字节数
	 */
	public static final int MB = 1024 * 1024;
	/**
	 * 输出间隔秒数，默认5分钟
	 */
	private int interval = 5 * 60;

	private Timer timer;

	/**
	 * 设置打印间隔，必须大于0
	 */
	public void setInterval(final int interval) {
		if (interval > 0) {
			this.interval = interval;
		}
	}

	/**
	 * 开始定时打印摘要日志
	 */
	public void start() {
		if (timer != null) {
			stop();
		}
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, interval * 1000);
	}

	/**
	 * 停止任务打印
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
		}
	}
}
