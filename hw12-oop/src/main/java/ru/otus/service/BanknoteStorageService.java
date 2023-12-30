package ru.otus.service;

import java.util.Map;

public interface BanknoteStorageService {
    Map<Banknote, Integer> returnBanknotes();
}
