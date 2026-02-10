package bank.local;

import java.io.IOException;

import bank.Account;
import bank.exceptions.InactiveException;
import bank.exceptions.OverdrawException;

public class LocalAccount implements bank.Account {
    private final String number;
    private final String owner;
    private double balance;
    private boolean active = true;
    
    public LocalAccount(String owner, String number) {
        this.owner = owner;
        this.number = number;
        balance = 0;
    }

    public LocalAccount (Account account) throws IOException {
        owner = account.getOwner();
        number = account.getNumber();
        balance = account.getBalance();
        active = account.isActive();
    }

     @Override
    public synchronized void deposit(double amount) throws InactiveException {
        if (amount < 0)
            throw new IllegalArgumentException();
        if (!isActive())
            throw new InactiveException();
        balance += amount;
    }

    @Override
    public synchronized void withdraw(double amount) throws InactiveException, OverdrawException {
        if (balance - amount < 0)
            throw new OverdrawException();
        if (amount < 0)
            throw new IllegalArgumentException();
        if (!isActive())
            throw new InactiveException();
        balance -= amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void closeAccount() {
        active = !active;
    }

}