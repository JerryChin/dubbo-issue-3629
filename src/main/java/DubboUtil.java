
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Dubbo 工具类，主要帮助开发者简化服务提供者初始化工作。
 *
 * @author Jerry Chin
 */
public class DubboUtil {

	public static final String FIXED_SIZE_POOL = "fixed";
	public static final String CACHED_SIZE_POOL = "cached";

	/**
	 * 应用以下默认值：
	 *
	 * <ul>
	 *   <li>使用 <code>Log4j2</code>作为日志框架</li>
	 *   <li>禁用在线运维工具</li>
	 * </ul>
	 *
	 */
	public static ApplicationConfig applyDefaultValues(ApplicationConfig config, Class<?> providerClass) {

		// 使用提供者类的全限定名作为名称
		config.setName(providerClass.getCanonicalName());

		// 缺省 slf4j
		config.setLogger("log4j2");

		// 禁用在线运维模块
		config.setQosEnable(false);

		return config;
	}


	public static ProtocolConfig useDubboProtocolAndSetPort(ProtocolConfig config, int port) {
		config.setName("dubbo");
		config.setPort(port);
		return config;
	}

	public static ProtocolConfig useCachedThreadPool(ProtocolConfig config, String threadNamePrefix, int core, int max, int queue) {
		config.setThreadpool(CACHED_SIZE_POOL);
		config.setThreads(max);
		config.setCorethreads(core);
		config.setQueues(queue);
		Map<String, String> additionalParams = new HashMap<>();
		additionalParams.put("threadname", threadNamePrefix);

		config.setParameters(additionalParams);


		return config;
	}

	public static ProtocolConfig useFixedThreadPool(ProtocolConfig config, String threadNamePrefix, int threads, int queue) {
		config.setThreadpool(FIXED_SIZE_POOL);
		config.setThreads(threads);
		config.setQueues(queue);
		Map<String, String> additionalParams = new HashMap<>();
		additionalParams.put("threadname", threadNamePrefix);
		config.setParameters(additionalParams);
		return config;
	}

	/**
	 * 返回一个使用组播技术的注册中心配置，组播地址：multicast://224.5.6.7:1234
	 */
	public static RegistryConfig createMulticastRegistryForFun() {

		RegistryConfig registryConfig = new RegistryConfig();

		// 使用组播地址
		registryConfig.setAddress("multicast://224.5.6.7:1234");

		return registryConfig;
	}
}
