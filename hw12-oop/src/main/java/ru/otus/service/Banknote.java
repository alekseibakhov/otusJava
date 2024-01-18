package ru.otus.service;

public enum Banknote {
    CASH10(10),
    CASH50(50),
    CASH100(100),
    CASH200(200),
    CASH500(500),
    CASH1000(1000);

    private final int amount;
    Banknote(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }
}
