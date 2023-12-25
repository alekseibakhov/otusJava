package ru.otus.service;

import ru.otus.exceprions.BalanceException;
import ru.otus.exceprions.InvalidBanknoteException;

import java.util.List;

public interface ATM {
    long getBalance();
    List<Banknote> getBanknotes(int amount) throws InvalidBanknoteException, BalanceException;
    void loadBanknotes(List<Banknote> banknotes);
}
