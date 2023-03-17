package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 메서드 이름 필터링
        // 메서드 이름 가져오기
        String methodName = method.getName();

        // 패턴 매칭해서 검사하기
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            System.out.println("yes! pattern not match!");
            method.invoke(target, args);
        }

        TraceStatus status = null;

        try {
            // 공통처리 - 로그 남기기
            // 메시지 만들기
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";

            status = logTrace.begin(message);

            // 개별처리 - target 의 method call
            Object result = method.invoke(target, args);

            // 상태 객체를 넘기면서 종료하기.
            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }




    }
}
