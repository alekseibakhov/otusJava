package ru.otus.service;

import ru.otus.exceprions.InvalidBanknoteException;

import java.util.List;
import java.util.Map;

public interface TerminalIssueBanknotes {
    List<Banknote> issueBanknotes(int amountIssue, Map<Banknote, Integer> banknoteSlots) throws InvalidBanknoteException;
}
