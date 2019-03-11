import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.junit.Test;

/**
 * @author Jerry Chin
 */
public class DemoTest {

	@Test
	public void doTest() {
		ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();

		// 配置应用设置
		serviceConfig.setApplication(DubboUtil.applyDefaultValues(new ApplicationConfig(), DemoService.class));

		// 配置协议设置
		ProtocolConfig protocol = DubboUtil.useDubboProtocolAndSetPort(new ProtocolConfig(), 9999);

		// 配置业务处理线程池
		protocol = DubboUtil.useCachedThreadPool(protocol, DemoService.class.getSimpleName(),
				Runtime.getRuntime().availableProcessors(), 20, 500);

		protocol.setHost("0.0.0.0");
		serviceConfig.setProtocol(protocol);

		// 尽管我们不使用注册中心，dubbo 还是强制要求至少指定一个
		serviceConfig.setRegistry(DubboUtil.createMulticastRegistryForFun());

		serviceConfig.setInterface(DemoService.class);
		serviceConfig.setVersion("1.0.0");
		serviceConfig.setRef(new DemoService() {
			@Override
			public void doNothing() {

			}
		});

		serviceConfig.export();
		serviceConfig.export();
	}
}
