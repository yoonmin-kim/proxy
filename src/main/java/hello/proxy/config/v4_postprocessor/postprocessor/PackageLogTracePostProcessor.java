package hello.proxy.config.v4_postprocessor.postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

	private final String basePackage;
	private final Advisor advisor;

	public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
		this.basePackage = basePackage;
		this.advisor = advisor;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("beanName={}, bean.getClass()={}", beanName, bean.getClass());

		String beanPackage = bean.getClass().getPackageName();
		if (!beanPackage.startsWith(basePackage)) {
			return bean;
		}

		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.addAdvisor(advisor);
		Object proxy = proxyFactory.getProxy();
		log.info("create proxy target={}, proxy={}", bean.getClass(), proxy.getClass());
		return proxy;
	}
}
