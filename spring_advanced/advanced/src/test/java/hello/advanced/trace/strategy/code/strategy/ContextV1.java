package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 필드에 주입받는 방식
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }
    public void execute() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직이짐
        strategy.call();
        // 비즈니스 로직 종료했숨
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}ms", resultTime);
    }
}
