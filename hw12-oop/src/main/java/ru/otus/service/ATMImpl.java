package ru.otus.service;

import ru.otus.exceprions.BalanceException;
import ru.otus.exceprions.InvalidBanknoteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMImpl implements ATM {
    private final BanknoteService banknoteService = new BanknoteService();

    private final Map<Banknote, Integer> banknoteSlots = new HashMap<>();

    public ATMImpl() {
        createSlots();
    }

    private long balance = 0;

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
        if (amount % 10 != 0) {
            throw new InvalidBanknoteException("Minimum issue amount - 10р");
        }

        List<Banknote> banknotes = banknoteService.issueBanknotes(amount, banknoteSlots);
        balance -= amount;
        return banknotes;
    }

    @Override
    public void loadBanknotes(List<Banknote> banknotes) {
        balance += banknoteService.loadBanknotes(banknotes, banknoteSlots);
    }

    private void createSlots() {
        banknoteSlots.put(Banknote.CASH10, 0);
        banknoteSlots.put(Banknote.CASH50, 0);
        banknoteSlots.put(Banknote.CASH100, 0);
        banknoteSlots.put(Banknote.CASH200, 0);
        banknoteSlots.put(Banknote.CASH500, 0);
    }

}
