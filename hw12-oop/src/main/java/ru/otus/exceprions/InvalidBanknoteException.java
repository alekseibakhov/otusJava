package ru.otus.exceprions;

public class InvalidBanknoteException extends Exception{
    public InvalidBanknoteException(String errorMessage){
        super(errorMessage);
    }
}
