/**
 *
 */
package com.whosly.rapid.data.log.trace.concurrent;

import com.whosly.api.constant.Constants;
import com.whosly.api.constant.ConventionsX;
import com.whosly.rapid.data.log.trace.util.MDCUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.concurrent.Callable;

/**
 * 带有 mdc 上下文信息传递的Callable
 *
 * @author yueny09 <yueny09@163.com>
 *
 * @DATE 2018年3月1日 下午7:49:54
 *
 */
public abstract class MDCCallable<V> implements Callable<V> {
	/**
	 * 记录提交runnable的thread 所拥有的的mdc
	 */
	private final String preLogId;

	public MDCCallable() {
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
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public V call() throws Exception {
		MDCUtil.injectLogId(preLogId, Constants.DOT);

		try {
			return runner();
		} finally {
			if (StringUtils.equals(MDCUtil.DEFAULT_MARK, preLogId)) {
				MDC.clear();
			} else {
				MDCUtil.removeMdcLogId();
			}
		}
	}

	public abstract V runner() throws Exception;

}
