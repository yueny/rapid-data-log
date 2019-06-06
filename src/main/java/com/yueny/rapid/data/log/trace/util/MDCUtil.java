package com.yueny.rapid.data.log.trace.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.yueny.superclub.api.constant.Constants;
import com.yueny.superclub.api.constant.ConventionsX;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月23日 下午1:13:36
 *
 */
@Slf4j
public class MDCUtil {
	public final static String DEFAULT_MARK = "-";

	/**
	 * 向MDC中插入traceId和logId<br>
	 * 同时放入当前线程的上下文
	 *
	 * @param traceId
	 *            传入的上游业务信息
	 * @return 新生成的事件信息logId
	 */
	public static String injectLogId(final String traceId) {
		return injectLogId(traceId, "");
	}

	/**
	 * 向MDC中插入traceId和logId<br>
	 * 同时放入当前线程的上下文
	 *
	 * @param traceId
	 *            传入的上游业务信息
	 * @param logIdPrefix
	 *            logId前缀标识
	 * @return 新生成的事件信息logId
	 */
	public static String injectLogId(String traceId, final String logIdPrefix) {
		if (log.isTraceEnabled()) {
			log.trace("{}/拦截器~", Thread.currentThread().getId());
		}

		if (StringUtils.isBlank(traceId)) {
			traceId = DEFAULT_MARK;
		}
		// 注入新的mdc上下文, as traceId
		MDC.put(ConventionsX.CTX_TRACE_ID_MDC, traceId);

		String prefix = logIdPrefix;
		if (StringUtils.startsWith(traceId, Constants.DOT)) {
			final String traceDot = StringUtils.substringBeforeLast(traceId, Constants.DOT) + Constants.DOT;
			prefix += traceDot;
		}

		// 生成当前线程的logId
		final String logId = prefix + StringUtils.remove(UUID.randomUUID().toString(), "-");
		MDC.put(ConventionsX.CTX_LOG_ID_MDC, logId);

		// InheritableThreadHolder.get().setLogId(logId);
		// InheritableThreadHolder.get().setParentThreadId(Thread.currentThread().getId());

		return logId;
	}

	/**
	 * 移除MDC的 logId 信息
	 *
	 * @return 移除的事件信息 logId
	 */
	public static boolean removeMdcLogId() {
		// 移除当前线程的logId，但不动父线程信息
		MDC.remove(ConventionsX.CTX_TRACE_ID_MDC);
		MDC.remove(ConventionsX.CTX_LOG_ID_MDC);
		return true;
	}

}
