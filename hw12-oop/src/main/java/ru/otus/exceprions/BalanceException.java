package ru.otus.exceprions;

public class BalanceException extends Exception{
    public BalanceException(String errorMessage){
        super(errorMessage);
    }
}
