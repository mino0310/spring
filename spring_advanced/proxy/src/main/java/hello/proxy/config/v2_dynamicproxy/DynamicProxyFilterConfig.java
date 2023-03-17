package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
@RequiredArgsConstructor
public class DynamicProxyFilterConfig {

    public static final String[] PATTERNS = {"request*", "order*", "save*"};

    public final LogTrace logTrace;

    @Bean
    public OrderControllerV1 orderControllerV1() {
        OrderControllerV1Impl orderController = new OrderControllerV1Impl(orderServiceV1());

        OrderControllerV1 proxyInstance = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceFilterHandler(orderController, logTrace, PATTERNS));

        return proxyInstance;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(){

        OrderServiceV1Impl orderService = new OrderServiceV1Impl(orderRepositoryV1());

        OrderServiceV1 proxyInstance = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceFilterHandler(orderService, logTrace, PATTERNS));
        return proxyInstance;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        OrderRepositoryV1Impl orderRepository = new OrderRepositoryV1Impl();

        // 프록시를 만들어야 함
        OrderRepositoryV1 proxyInstance = (OrderRepositoryV1) Proxy.newProxyInstance(orderRepository.getClass().getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceFilterHandler(orderRepository, logTrace, PATTERNS));

        return proxyInstance;
    }

}
