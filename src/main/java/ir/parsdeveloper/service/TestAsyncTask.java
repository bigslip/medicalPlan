package ir.parsdeveloper.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author hadi tayebi
 */
@Component
public class TestAsyncTask {

    @Scheduled(initialDelay = 10, fixedDelay = 300000)
    public void task() {

    }

}
