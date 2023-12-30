package ru.otus.service;

import java.util.List;
import java.util.Map;

public interface TerminalLoadBanknotes {
    long loadBanknotes(List<Banknote> banknotesForLoad, Map<Banknote, Integer> banknoteSlots);
}
