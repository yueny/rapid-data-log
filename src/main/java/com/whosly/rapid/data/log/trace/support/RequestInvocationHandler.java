package com.whosly.rapid.data.log.trace.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.whosly.api.constant.ConventionsX;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;


/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月19日 下午4:42:50
 *
 */
public class RequestInvocationHandler implements InvocationHandler {
	public static HttpServletRequest createRequestWapper(final HttpServletRequest r) {
		return (HttpServletRequest) (Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(),
				new Class[] { HttpServletRequest.class }, new RequestInvocationHandler(r)));
	}

	private final HttpServletRequest wrappedRequest;

	public RequestInvocationHandler(final HttpServletRequest r) {
		wrappedRequest = r;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		if ("getHeader".equals(method.getName()) && args.length == 1
				&& ConventionsX.X_TRACE_ID_HEADER.equals(args[0])) {
			String traceId = (String) method.invoke(wrappedRequest, args);
			if (StringUtils.isBlank(traceId)) {
				traceId = MDC.get(ConventionsX.CTX_LOG_ID_MDC);
			}

			if (StringUtils.isBlank(traceId)) {
				traceId = StringUtils.remove(UUID.randomUUID().toString(), "-");
			}

			return traceId;
		}
		return method.invoke(wrappedRequest, args);
	}
}