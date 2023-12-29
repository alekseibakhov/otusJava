package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Deque<Message> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message msg) {
        stack.push(msg.toBuilder().build()
        );
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return stack.stream().filter(message -> id == message.getId()).findAny();
    }
}
