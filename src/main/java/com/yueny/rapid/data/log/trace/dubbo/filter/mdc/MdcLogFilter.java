/**
 *
 */
package com.yueny.rapid.data.log.trace.dubbo.filter.mdc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.yueny.rapid.data.log.trace.util.MDCUtil;
import com.yueny.superclub.api.constant.ConventionsX;

import lombok.Setter;

/**
 * 同步MDC拦截器<br>
 * 使用方式<br>
 * 1:在resources下创建META-INF/dubbo目录, jar包已提供<br>
 * 2:创建com.alibaba.dubbo.rpc.Filter文件<br>
 * 3:在文件中添加 mdcLogFilter=com.yueny.rapid.data.log.dubbo.filter.mdc.MdcLogFilter
 * <br>
 *
 * 4:在dubbo配置中添加<dubbo:provider filter="mdcLogFilter"></dubbo:provider><br>
 * 或者 <dubbo:consumer filter="mdcLogFilter"></dubbo:consumer><br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月7日 上午9:53:16
 *
 */
@Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class MdcLogFilter implements Filter {
	@Setter
	private boolean generateLogId = false;
	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * consumer在请求上下文中添加MDC中持有的CTX_EVENT_ID_MDC，命名为CTX_LOG_ID_MDC
	 *
	 * @param invoker
	 * @param invocation
	 */
	private void handleConsumerLog(final Invoker<?> invoker, final Invocation invocation) {
		Map<String, String> attachments = invocation.getAttachments();
		if (attachments != null) {
			attachments = new HashMap<String, String>(attachments);
			attachments.put(ConventionsX.CTX_TRACE_ID_MDC, MDC.get(ConventionsX.CTX_LOG_ID_MDC));
		}
		RpcContext.getContext().setInvoker(invoker).setInvocation(invocation);

		if (attachments != null) {
			if (RpcContext.getContext().getAttachments() != null) {
				RpcContext.getContext().getAttachments().putAll(attachments);
			} else {
				RpcContext.getContext().setAttachments(attachments);
			}
		}
	}

	/**
	 * provider从rpcContext获取CTX_LOG_ID_MDC，并生成CTX_EVENT_ID_MDC，保存到MDC中
	 */
	private void handleProviderLog() {
		final String traceId = RpcContext.getContext().getAttachment(ConventionsX.CTX_TRACE_ID_MDC);

		MDCUtil.injectLogId(traceId);
	}

	@Override
	public Result invoke(final Invoker<?> invoker, final Invocation invocation) throws RpcException {
		// RPC调用的是消费方
		if (RpcContext.getContext().isConsumerSide()) {
			handleConsumerLog(invoker, invocation);
		} else if (RpcContext.getContext().isProviderSide()) {
			handleProviderLog();
		}

		if (invocation instanceof RpcInvocation) {
			((RpcInvocation) invocation).setInvoker(invoker);
		}

		try {
			return invoker.invoke(invocation);
		} finally {
			// 通常RPC之间的通信可能存在多次，故每次不会清空当前的上下文
			// MDC.clear();
			// RpcContext.removeContext();
		}
	}

}
