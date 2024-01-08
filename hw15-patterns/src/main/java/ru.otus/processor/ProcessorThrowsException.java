package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowsException implements Processor {
    private final LocalDateTime dateTime;
    public ProcessorThrowsException(LocalDateTime localDateTime){
        this.dateTime = localDateTime;
    }
    @Override
    public Message process(Message message) {

        if (dateTime.getSecond() % 2 == 0) {
            throw new RuntimeException("Ошибка произошла каждую чётную секунду!");
        } else return message;

    }
}
