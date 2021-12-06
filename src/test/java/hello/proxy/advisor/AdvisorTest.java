package hello.proxy.advisor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
		proxyFactory.addAdvisors(advisor);
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();

	}

	@Test
	@DisplayName("직접 만드는 포인트컷")
	void advisorTest2() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
		proxyFactory.addAdvisors(advisor);
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();

	}

	@Test
	@DisplayName("스프링이 제공하는 포인트컷")
	void advisorTest3() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("save");
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
		proxyFactory.addAdvisors(advisor);
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();

	}

	static class MyPointCut implements Pointcut {
		@Override
		public ClassFilter getClassFilter() {
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() {
			return new MyMethodMatcher();
		}
	}

	static class MyMethodMatcher implements MethodMatcher {

		public String matchName = "save";

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			boolean result = matchName.equals(method.getName());
			log.info("포인트컷 호출 method={} target={}", method.getName(), targetClass);
			log.info("포인트컷 결과 result={}", result);
			return false;
		}

		@Override
		public boolean isRuntime() {
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			return false;
		}
	}

}
