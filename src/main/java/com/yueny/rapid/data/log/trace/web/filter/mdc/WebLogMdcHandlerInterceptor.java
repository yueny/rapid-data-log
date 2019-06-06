/**
 *
 */
package com.yueny.rapid.data.log.trace.web.filter.mdc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yueny.rapid.data.log.trace.support.LogHttpRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yueny.rapid.data.log.trace.util.MDCUtil;
import com.yueny.superclub.api.constant.ConventionsX;

/**
 * 异步/网络请求<br>
 * 使用方式<br>
 * <code>
 * <mvc:interceptor>
 <mvc:mapping path="/**"/>
 <mvc:exclude-mapping path="/assets/**" />
 <bean class="com.yueny.rapid.data.log.core.web.filter.mdc.WebLogMdcHandlerInterceptor"/>
 </mvc:interceptor>
 </mvc:interceptors>
 *</code>
 *
 * <pre>
 * 示例使用:
 * log4j.appender.stdout.layout.conversionPattern=%d [%X{loginUserId}/%X{req.remoteAddr}/%X{req.id} - %X{req.requestURI}?%X{req.queryString}] %-5p %c{2} - %m%n
 * </pre>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月7日 上午9:49:30
 *
 */
public class WebLogMdcHandlerInterceptor extends HandlerInterceptorAdapter {
	/**
	 * LogId一般有前端的负载生成，比如Nginx或Lighttpd
	 */
	private boolean generateLogId = false;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * afterCompletion(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
								final Object handler, final Exception ex) throws Exception {
		MDC.clear();
	}

	@Override
	public void afterConcurrentHandlingStarted(final HttpServletRequest request, final HttpServletResponse response,
											   final Object handler) throws Exception {
		preHandle(request, response, handler);
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
						   final ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	/*
	 * 存放在MDC中的数据，log4j可以直接引用并作为日志信息打印出来.
	 *
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * preHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		/*
		 * 为每一个请求创建一个ID，方便查找日志时可以根据ID查找出一个http请求所有相关日志.<br>
		 * 如果request中不存在reqId,则自动生成
		 */
		final LogHttpRequestWrapper r = new LogHttpRequestWrapper(request);
		final String traceId = r.getHeader(ConventionsX.X_TRACE_ID_HEADER);

		final String eventId = MDCUtil.injectLogId(traceId);

		if (StringUtils.isBlank(traceId)) {
			r.putHeader(ConventionsX.X_TRACE_ID_HEADER, eventId);
			response.setHeader(ConventionsX.X_TRACE_ID_HEADER, eventId);
		}

		// if (generateLogId) {
		// // TODO 来自外部， 取外部值
		// traceId = StringUtils.remove(UUID.randomUUID().toString(), "-");
		// } else {
		// traceId = StringUtils.remove(UUID.randomUUID().toString(), "-");
		// }

		// if
		// (StringUtils.isNotBlank(request.getHeader(ConventionsX.X_SRC_SYS_HEADER)))
		// {
		// MDC.put(ConventionsX.CTX_SRC_SYS_MDC,
		// request.getHeader(ConventionsX.X_SRC_SYS_HEADER));
		// }

		return true;
	}

	public void setGenerateLogId(final boolean generateLogId) {
		this.generateLogId = generateLogId;
	}
}
