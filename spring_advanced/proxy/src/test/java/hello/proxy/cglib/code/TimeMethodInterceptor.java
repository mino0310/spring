package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // obj 는 CGLIB에 의해 만들어진 것임, target을 상속해서 만들어진 프록시 클래스.
        log.info("TimeProxy call!");

        long startTime = System.currentTimeMillis();

        System.out.println("what is obj in args ? = " + obj.getClass());
        Object result = methodProxy.invoke(target, args);

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;

        log.info("TimeProxy 종료 resultTime = {}", resultTime);

        return result;

    }
}
