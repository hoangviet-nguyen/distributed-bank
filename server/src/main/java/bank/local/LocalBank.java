package bank.local;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import bank.Account;
import bank.exceptions.InactiveException;
import bank.exceptions.OverdrawException;

public class LocalBank implements bank.Bank {

        private final Map<String, Account> accounts = new ConcurrentHashMap<>();
        private int numAccounts = 0;

        @Override
        public Set<String> getAccountNumbers() throws IOException{
            Set<String> numbers = new HashSet<>();
            for (Account account : accounts.values()) {
                if (account!= null && account.isActive()) {
                    numbers.add(account.getNumber());
                }
            }
            return numbers;
        }

        @Override
        public String createAccount(String owner) {
            String accountNumber = String.valueOf(numAccounts++);
            LocalAccount account = new LocalAccount(owner, accountNumber);
            accounts.put(accountNumber, account);
            return accountNumber;
        }

        @Override
        public boolean closeAccount(String number) throws IOException {
            if (getAccount(number) == null || !getAccount(number).isActive())
                return false;

            if (getAccount(number).getBalance() == 0) {
                ((LocalAccount)(accounts.get(number))).closeAccount();;
                return true;
            }

            return false;
        }

        @Override
        public bank.Account getAccount(String number) {
            return accounts.get(number);
        }

        @Override
        public void transfer(bank.Account from, bank.Account to, double amount)
                throws IOException, InactiveException, OverdrawException {

            if (getAccount(from.getNumber()) == null || getAccount(to.getNumber()) == null)
                throw new IOException();

            if (!from.isActive() || !to.isActive())
                throw new InactiveException();

            getAccount(from.getNumber()).withdraw(amount);
            getAccount(to.getNumber()).deposit(amount);
        }

        public void updateAccount(String number, Account account) throws IOException {
            accounts.put(number, new LocalAccount(account));
        }

    }
