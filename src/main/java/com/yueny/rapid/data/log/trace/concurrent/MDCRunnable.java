/**
 *
 */
package com.yueny.rapid.data.log.trace.concurrent;

import com.yueny.rapid.data.log.trace.util.MDCUtil;
import com.yueny.superclub.api.constant.Constants;
import com.yueny.superclub.api.constant.ConventionsX;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 带有 mdc 上下文信息传递的Runnable
 *
 * @author yueny09 <yueny09@163.com>
 *
 * @DATE 2018年3月1日 下午7:39:41
 *
 */
public abstract class MDCRunnable implements Runnable {
	/**
	 * 记录提交runnable的thread 所拥有的的mdc
	 */
	private final String preLogId;

	public MDCRunnable() {
		final String logId = MDC.get(ConventionsX.CTX_LOG_ID_MDC);

		if (StringUtils.isBlank(logId)) {
			preLogId = MDCUtil.DEFAULT_MARK;
		} else {
			preLogId = logId;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		MDCUtil.injectLogId(preLogId, Constants.DOT);

		try {
			runner();
		} finally {
			if (StringUtils.equals(MDCUtil.DEFAULT_MARK, preLogId)) {
				MDC.clear();
			} else {
				MDCUtil.removeMdcLogId();
			}
		}
	}

	public abstract void runner();

}
