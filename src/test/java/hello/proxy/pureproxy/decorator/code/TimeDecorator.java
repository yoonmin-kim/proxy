package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component {

	private Component component;

	public TimeDecorator(Component component) {
		this.component = component;
	}

	@Override
	public String operation() {
		log.info("TimeDecorator 실행");
		long startTime = System.currentTimeMillis();
		String result = component.operation();
		long endTime = System.currentTimeMillis() - startTime;
		log.info("TimeDecorator 종료, 걸린시간 = {}ms", endTime);
		return result;
	}
}
