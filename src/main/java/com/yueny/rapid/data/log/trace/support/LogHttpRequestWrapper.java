package com.yueny.rapid.data.log.trace.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月19日 下午4:43:07
 *
 */
public class LogHttpRequestWrapper extends HttpServletRequestWrapper {
	private final Map<String, String> logHeaders;

	public LogHttpRequestWrapper(final HttpServletRequest request) {
		super(request);
		this.logHeaders = new HashMap<String, String>();
	}

	@Override
	public String getHeader(final String name) {
		// check the custom headers first
		final String headerValue = logHeaders.get(name);

		if (headerValue != null) {
			return headerValue;
		}
		// else return from into the original wrapped object
		return ((HttpServletRequest) getRequest()).getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// create a set of the custom header names
		final Set<String> set = new HashSet<String>(logHeaders.keySet());

		// now add the headers from the wrapped request object
		final Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
		while (e.hasMoreElements()) {
			// add the names of the request headers into the list
			final String n = e.nextElement();
			set.add(n);
		}

		// create an enumeration from the set and return
		return Collections.enumeration(set);
	}

	public void putHeader(final String name, final String value) {
		this.logHeaders.put(name, value);
	}

}
