package com.whosly.rapid.data.log.trace.filter.mdc.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whosly.api.constant.ConventionsX;
import com.whosly.rapid.lang.thread.support.InheritableThreadHolder;
import org.apache.commons.lang3.StringUtils;

import com.whosly.rapid.data.log.trace.support.LogHttpRequestWrapper;
import com.whosly.rapid.data.log.trace.util.MDCUtil;

/**
 * web。xml中配置， 用于替代WebLogMdcHandlerInterceptor
 *
 * <pre>
 * <filter>
 <filter-name>mdcFilter</filter-name>
 <filter-class>com.yueny.rapid.data.log.web.filter.mdc.MdcFilter</filter-class>
 </filter>
 <filter-mapping>
 <filter-name>mdcFilter</filter-name>
 <url-pattern>/*</url-pattern>
 </filter-mapping>
 * </pre>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月19日 下午9:43:39
 *
 */
public class MdcFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final LogHttpRequestWrapper r = new LogHttpRequestWrapper((HttpServletRequest) request);
		final String traceId = r.getHeader(ConventionsX.X_TRACE_ID_HEADER);
		final String eventId = MDCUtil.injectLogId(traceId);

		if (StringUtils.isBlank(traceId)) {
			r.putHeader(ConventionsX.X_TRACE_ID_HEADER, eventId);
			((HttpServletResponse) response).setHeader(ConventionsX.X_TRACE_ID_HEADER, eventId);
		}

		chain.doFilter(r, response);

		// InheritableThreadHolder.remove();
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		// .
	}

}
