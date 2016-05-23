package test.services;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author hadi tayebi
 */

@Service
public class ScheduledProcessor {

    @Scheduled(fixedDelay = 3000, initialDelay = 5000)
    public void process() {
        System.out.println(Thread.currentThread().getThreadGroup().getName());
    }

    @Async
    public void doWork() {

    }
}