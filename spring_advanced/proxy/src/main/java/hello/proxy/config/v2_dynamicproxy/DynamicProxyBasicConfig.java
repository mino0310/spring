package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
@RequiredArgsConstructor
public class DynamicProxyBasicConfig {

    // LogTrace 는 빈으로 등록되어 있어서 주입받아서 사용
    private final LogTrace logTrace;

    @Bean
    public OrderControllerV1 orderControllerV1() {
        // 왜 필요한 객체들을 주입받지 않고 그때 그때 생성해서 사용하는 거지?
        // 어차피
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(
                orderServiceV1());

        OrderControllerV1 proxyInstance = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceBasicHandler(orderControllerV1, logTrace));
        return proxyInstance;
    }

    @Bean
    public OrderServiceV1 orderServiceV1() {
        OrderServiceV1Impl orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1());

        OrderServiceV1 proxyInstance = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceBasicHandler(orderServiceV1, logTrace));
        return proxyInstance;
    }

//    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        OrderRepositoryV1Impl orderRepository = new OrderRepositoryV1Impl();

        // 클래스 파일, 클래스 로더를 넘기고 핸들러까지 넘기면 된다
        // 그러면 내부에서 해당 클래스로더로 클래스를 받고 리플렉션을 통해 핸들러를 실행하는 프록시를 동적으로 생성해준다.
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceBasicHandler(orderRepository, logTrace));

        // Proxy를 스프링 빈으로 등록한다.
        // 빈으로 등록된 Proxy 를 사용해도 같은 인터페이스를 사용하므로 클라이언트에서 구분할 수는 없다.
        // 그저 같이 사용하면 Proxy 내부의 invoke 가 실행되고 등록했던 핸들러가 처리하고 싶은 과정을 처리한다.
        // 그다음 같이 넘겨줬던 target을 가지고 넘겨받았던 메서드 명 정보를 바탕으로 해당 target의 해당 메서드를 실행한다.
        // 실행이 끝나면 다시 프록시로 돌아오게 된다.
        // 클라이언트는 변경을 알아챌 수 없다.
        return proxy;
    }
}