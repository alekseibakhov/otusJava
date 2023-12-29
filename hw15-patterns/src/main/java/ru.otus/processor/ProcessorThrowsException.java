package ru.otus.processor;

import ru.otus.model.Message;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessorThrowsException implements Processor {
    @Override
    public Message process(Message message) {
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            Runnable task = () -> {
                if (Instant.now().getEpochSecond() % 2 == 0) {
                    throw new RuntimeException("Ошибка произошла каждую чётную секунду!");
                }
            };
            scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        }
        return message;
    }
}
