package org.lms.destory;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ShutdownHook {

    private final ExecutorService executorService;

    public ShutdownHook(@Qualifier("taskExecutor") ExecutorService executorService) {
        this.executorService = executorService;
    }

    @PreDestroy
    public void onDestroy() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}