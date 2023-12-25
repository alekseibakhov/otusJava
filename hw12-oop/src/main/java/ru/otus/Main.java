package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exceprions.BalanceException;
import ru.otus.exceprions.InvalidBanknoteException;
import ru.otus.service.Banknote;
import ru.otus.service.Terminal;
import ru.otus.service.TerminalImpl;

import java.util.List;

public class Main {
    static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InvalidBanknoteException, BalanceException {
        Terminal terminal = new TerminalImpl();
        terminal.loadBanknotes(
                List.of(
                        Banknote.CASH10, Banknote.CASH10, Banknote.CASH10,
                        Banknote.CASH50, Banknote.CASH50,
                        Banknote.CASH100, Banknote.CASH100,
                        Banknote.CASH100, Banknote.CASH100,
                        Banknote.CASH200, Banknote.CASH200, Banknote.CASH200)
        );
        terminal.loadBanknotes(
                List.of(
                        Banknote.CASH10, Banknote.CASH10, Banknote.CASH10,
                        Banknote.CASH200, Banknote.CASH200, Banknote.CASH200)
        );

        log.info("Current balance: {} ", terminal.getBalance());

        List<Banknote> banknotes = terminal.getBanknotes(520);
        System.out.println(banknotes);
        log.info("Current balance: {} ", terminal.getBalance());

    }
}