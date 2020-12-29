package com.whosly.rapid.data.log.trace.support;

import com.whosly.api.constant.Constants;
import com.whosly.api.constant.ConventionsX;
import com.whosly.rapid.data.log.trace.util.MDCUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.concurrent.Executor;

/**
 * @author jimi
 * @DATE 2018/2/11 下午5:10
 * @since 0.0.1
 *
 *        保障 MDC 信息在线程池
 */
public class ExecutorDelegate implements Executor {

	private final Executor executor;

	public ExecutorDelegate(final Executor executor) {
		this.executor = executor;
	}

	@Override
	public void execute(final Runnable command) {
		String preLogId = MDC.get(ConventionsX.CTX_LOG_ID_MDC);
		if (StringUtils.isBlank(preLogId)) {
			preLogId = MDCUtil.DEFAULT_MARK;
		}
		final String previousLogId = preLogId;

		executor.execute(new Runnable() {
			@Override
			public void run() {
				MDCUtil.injectLogId(previousLogId, Constants.DOT);

				try {
					command.run();
				} finally {
					if (StringUtils.equals(MDCUtil.DEFAULT_MARK, previousLogId)) {
						MDC.clear();
					} else {
						MDCUtil.removeMdcLogId();
					}
				}
			}
		});
	}
}
