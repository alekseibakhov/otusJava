package ru.otus.service;

import java.util.HashMap;
import java.util.Map;

public class BanknoteStorageImpl implements BanknoteStorageService {
    private final Map<Banknote, Integer> banknoteSlots = new HashMap<>();

    public BanknoteStorageImpl() {
        createSlots();
    }

    @Override
    public Map<Banknote, Integer> returnBanknotes() {
        return banknoteSlots;
    }

    private void createSlots() {
        banknoteSlots.put(Banknote.CASH10, 0);
        banknoteSlots.put(Banknote.CASH50, 0);
        banknoteSlots.put(Banknote.CASH100, 0);
        banknoteSlots.put(Banknote.CASH200, 0);
        banknoteSlots.put(Banknote.CASH500, 0);
    }
}
