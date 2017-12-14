package com.yueny.rapid.data.log.trace.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.yueny.rapid.lang.thread.support.InheritableThreadHolder;
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
	private final static String DEFAULT_MARK = "-";

	/**
	 * 向MDC中插入traceId和logId<br>
	 * 同时放入当前线程的上下文
	 *
	 * @param traceId
	 *            传入的上游业务信息
	 * @return 新生成的事件信息logId
	 */
	public static String injectTraceAndLogId(String traceId) {
		if (StringUtils.isBlank(traceId)) {
			traceId = DEFAULT_MARK;
		}

		log.info("{}/拦截器~", Thread.currentThread().getId());

		// as traceId
		MDC.put(ConventionsX.CTX_TRACE_ID_MDC, traceId);

		// 生成当前线程的logId
		final String logId = StringUtils.remove(UUID.randomUUID().toString(), "-");
		MDC.put(ConventionsX.CTX_LOG_ID_MDC, logId);

		InheritableThreadHolder.get().setLogId(logId);
		InheritableThreadHolder.get().setParentThreadId(Thread.currentThread().getId());

		return logId;
	}

}
