package ru.otus.service;

import ru.otus.exceprions.BalanceException;
import ru.otus.exceprions.InvalidBanknoteException;

import java.util.List;

public class ATMImpl implements ATM {
    private final BanknoteService banknoteService = new BanknoteService();
    private final BanknoteStorageService banknoteSlots = new BanknoteStorageImpl();
    private long balance;

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public List<Banknote> getBanknotes(int amount) throws InvalidBanknoteException, BalanceException {
        if (amount <= 0) {
            throw new InvalidBanknoteException("The amount must be positive");
        }
        if (amount > balance) {
            throw new BalanceException("Your balance is less than the requested amount");
        }
        if (amount % Banknote.CASH10.getAmount() != 0) {
            throw new InvalidBanknoteException("Minimum issue amount - 10р");
        }

        List<Banknote> banknotes = banknoteService.issueBanknotes(amount, banknoteSlots.returnBanknotes());
        balance -= amount;
        return banknotes;
    }

    @Override
    public void loadBanknotes(List<Banknote> banknotes) {
        balance += banknoteService.loadBanknotes(banknotes, banknoteSlots.returnBanknotes());
    }

}
