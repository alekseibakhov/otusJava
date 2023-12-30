package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exceprions.InvalidBanknoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BanknoteService implements TerminalIssueBanknotes, TerminalLoadBanknotes{
    static final Logger log = LoggerFactory.getLogger(BanknoteService.class);

    @Override
    public List<Banknote> issueBanknotes(int amountIssue, Map<Banknote, Integer> banknoteSlots) throws InvalidBanknoteException {
        List<Banknote> result = new ArrayList<>();
        int remainingAmount = amountIssue;

        for (Banknote banknote : Banknote.values()) {
            if (banknote != Banknote.CASH10) {
                int count = banknoteSlots.getOrDefault(banknote, 0);
                if (count != 0) {
                    int maxCount = remainingAmount / banknote.getAmount();
                    int takeCount = Math.min(count, maxCount);

                    remainingAmount -= takeCount * banknote.getAmount();

                    for (int i = 0; i < takeCount; i++) {
                        banknoteSlots.put(banknote, banknoteSlots.get(banknote) - 1);
                        result.add(banknote);
                    }
                }
            }
        }

        if (remainingAmount != 0) {
            Banknote banknote = Banknote.CASH10;
            // Довыдаем мелкими остаток
            int count = banknoteSlots.getOrDefault(banknote, 0);
            int maxCount = remainingAmount / banknote.getAmount();
            int takeCount = Math.min(count, maxCount);

            remainingAmount -= takeCount * banknote.getAmount();

            for (int i = 0; i < takeCount; i++) {
                banknoteSlots.put(banknote, banknoteSlots.get(banknote) - 1);
                result.add(banknote);
            }
        }
        if (remainingAmount != 0) {
            throw new InvalidBanknoteException("Not enough banknotes to cover the amount");
        }
        log.info("The amount has been issued {}", amountIssue);
        return result;
    }

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
