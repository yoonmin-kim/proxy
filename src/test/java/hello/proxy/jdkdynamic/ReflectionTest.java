package hello.proxy.jdkdynamic;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {

	@Test
	void reflection0() {
		Hello target = new Hello();

		//공통 로직1 시작
		log.info("start");
		String result = target.callA();
		log.info("result={}", result);
		//공통 로직1 종료

		//공통 로직2 시작
		log.info("start");
		String result2 = target.callB();
		log.info("result2={}", result2);
		//공통 로직2 종료
	}

	@Test
	void reflection1() throws Exception {
		Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

		Hello target = new Hello();
		Method callA = classHello.getMethod("callA");
		Object result = callA.invoke(target);
		log.info("result={}", result);

		Method callB = classHello.getMethod("callB");
		Object result2 = callB.invoke(target);
		log.info("result2={}", result2);
	}

	@Test
	void reflection2() throws Exception {
		Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

		Hello target = new Hello();
		Method callA = classHello.getMethod("callA");
		dynamicCall(callA, target);

		Method callB = classHello.getMethod("callB");
		dynamicCall(callB, target);
	}

	private void dynamicCall(Method method, Object target) throws Exception {
		log.info("start");
		Object result = method.invoke(target);
		log.info("result={}", result);
	}

	static class Hello {
		public String callA() {
			log.info("callA");
			return "A";
		}
		public String callB() {
			log.info("callB");
			return "B";
		}
	}
}
