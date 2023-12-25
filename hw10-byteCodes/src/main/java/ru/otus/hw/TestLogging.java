package ru.otus.hw;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {
    @Log
    @Override
    public void calculation(int param) {
    }

    @Override
    public void calculation(int param, int param2) {
    }

    @Log
    @Override
    public void calculation(int param, String paramStr) {
    }
}
