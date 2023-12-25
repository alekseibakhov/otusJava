package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class TerminalLoadBanknotesImpl implements TerminalLoadBanknotes {
    static final Logger log = LoggerFactory.getLogger(TerminalLoadBanknotesImpl.class);

    @Override
    public long loadBanknotes(List<Banknote> banknotesForLoad, Map<Banknote, Integer> banknoteSlots) {
        int loadingSum = 0;
        for (Banknote banknote : banknotesForLoad) {
            loadingSum += banknote.getAmount();
            banknoteSlots.put(banknote, banknoteSlots.get(banknote) + 1);
        }
        log.info("Loading sum: {}", loadingSum);
        return loadingSum;
    }
}
