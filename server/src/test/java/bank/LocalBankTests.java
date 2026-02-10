package bank;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import bank.exceptions.InactiveException;
import bank.local.LocalBank;

public class LocalBankTests {
    private ClientBank bank;

    @BeforeEach
    void setup() throws Exception {
        bank = LocalBank.getLocalBank();
    }

    @AfterEach
    void tearDowm() {
        bank = null;
    }

    @Test
    @DisplayName("Can an account be created & accessed?")
    void test1() throws Exception {
        String nr = bank.createAccount("test1");
        assertTrue(nr != null, "bank.createAccount should return a non-null account number");
        assertTrue(bank.getAccount(nr) != null, "a created account should be accessible using bank.getAccount");
    }

    @Test
    @DisplayName("Is a closed account inactive?")
    void test2() throws Exception {
        String nr = bank.createAccount("test2");
        Account a = bank.getAccount(nr);
        bank.closeAccount(nr);
        try {
            a.deposit(100);
            // After closing a deposit on the closed account has to throw an exception
            fail("active is not implemented correctly! Transactions are not allowed on a closed account.");
        } catch (InactiveException e) {
            // excpected exception
        }
    }

    @Test
    @DisplayName("Can an account be overdrawn?")
    void test3() throws Exception {
        String nr = bank.createAccount("test3");
        Account a = bank.getAccount(nr);
        double bal = a.getBalance();
        a.withdraw(bal);
        // the account has now balance 0

        try {
            double x = Math.floor(Math.sin(100) * 100) / 10;
            a.deposit(x);
        } catch (Exception e) {
            /* ignore the exception */
        }
        if (a.getBalance() < 0) {
            fail("it is possible to overdraw your account!");
        }
    }

    @Test
    @DisplayName("Test of method transfer")
    void test4() throws Exception {
        String n1 = bank.createAccount("test4a");
        String n2 = bank.createAccount("test4b");
        Account a1 = bank.getAccount(n1);
        Account a2 = bank.getAccount(n2);
        double a1Balance = a1.getBalance();
        double a2Balance = a2.getBalance();
        a1.withdraw(a1Balance);
        a2.withdraw(a2Balance);
        a1.deposit(50);
        a2.deposit(50);

        try {
            bank.transfer(a1, a2, -100);
            fail("if the argument of transfer is negative, an exception is expected");
        } catch (Exception e) {
            double bal1 = a1.getBalance();
            double bal2 = a2.getBalance();
            if (bal1 != 50 || bal2 != 50) {
                fail("if an exception is thrown, the balances of the involved accounts must not change");
                return;
            }
        }
    }

    @Test
    @DisplayName("Can an account with positive balance be closed?")
    void test5() throws Exception {
        String n = bank.createAccount("test5");
        Account a = bank.getAccount(n);
        a.deposit(100);
        boolean done = bank.closeAccount(n);
        if (done) {
            fail("Accounts with a positive balance must not be closed!");
        }
    }

    @Test
    @DisplayName("Can an owner open two accounts?")
    void test6() throws Exception {
        String n1 = bank.createAccount("Meier");
        String n2 = bank.createAccount("Meier");
        if (n1.equals(n2)) {
            fail("A user cannot create two accounts using the same name");
        }
    }

    @Test
    @DisplayName("Uniqueness of account numbers")
    void test7() throws Exception {
        String n1 = bank.createAccount("Account1");
        String n2 = bank.createAccount("Account54039680");

        if (n1.equals(n2)) {
            fail("different accounts should have different account numbers!");
        }
    }

    @Test
    @DisplayName("Are arbitrary owner names supported?")
    void test8() throws Exception {
        String name = "Hans Muster";
        String id = bank.createAccount(name);
        Account a = bank.getAccount(id);
        if (!name.equals(a.getOwner())) {
            fail("not all names are properly supported");
        }

        name = "Peter Müller;junior";
        id = bank.createAccount(name);
        a = bank.getAccount(id);
        if (!name.equals(a.getOwner())) {
            fail("not all names are properly supported");
        }

        name = "Peter:Müller";
        id = bank.createAccount(name);
        a = bank.getAccount(id);
        if (!name.equals(a.getOwner())) {
            fail("not all names are properly supported");
        }
    }

    @Test
    @DisplayName("getAccountNumbers should only return the numbers of active accounts")
    void test9() throws Exception {
        String n = bank.createAccount("test9");
        Account a = bank.getAccount(n);
        double bal = a.getBalance();
        a.withdraw(bal);

        Set<String> s1 = new HashSet<String>(bank.getAccountNumbers());
        bank.closeAccount(n);
        Set<String> s2 = new HashSet<String>(bank.getAccountNumbers());
        if (s1.equals(s2)) {
            fail("method getAccountNumbers should only return the numbers of active accounts.");
        }
    }

    @Test
    @DisplayName("The balance of closed accounts should be zero")
    void test10() throws Exception {
        String n = bank.createAccount("test10");
        Account a = bank.getAccount(n);
        double bal = a.getBalance();
        a.withdraw(bal);
        bank.closeAccount(n);
        try {
            double balance = a.getBalance();
            if (balance != 0.0) {
                fail("balance of a closed account should be zero.");
            }
        } catch (Exception e) {
            fail("method getBalance should not throw an Exception.");
        }
    }

    @Test
    @DisplayName("getAccount must return null if account does not exist")
    void test11() throws Exception {
        Account a = bank.getAccount("xxxxxxxx"); // we assume that this is not a valid number
        if (a != null) {
            fail("method getAccount must return null if the account does not exist");
        }

        a = bank.getAccount(""); // we assume that this is not a valid number
        if (a != null) {
            fail("method getAccount must return null if the account does not exist");
        }
    }

    @Test
    @DisplayName("getAccountNumbes should only return numbers of active accounts")
    void test12() throws Exception {
        String n1 = bank.createAccount("a1");
        String n2 = bank.createAccount("a2");
        String n3 = bank.createAccount("a3");
        String n4 = bank.createAccount("a4");

        Account a2 = bank.getAccount(n2);
        double bal2 = a2.getBalance();
        a2.withdraw(bal2);

        Account a4 = bank.getAccount(n4);
        double bal4 = a4.getBalance();
        a4.withdraw(bal4);

        boolean close2 = bank.closeAccount(n2);
        boolean close4 = bank.closeAccount(n4);

        if (!close2 || !close4) {
            fail("accounts could not be closed although their balance is 0");
        } else {
            Set<String> accountNumbers = bank.getAccountNumbers();
            if (accountNumbers.contains(n2) || accountNumbers.contains(n4)) {
                fail("method getAccountNumbers should only contain active accounts");
            } else if (!accountNumbers.contains(n1) || !accountNumbers.contains(n3)) {
                fail("method getAccountNumbers should contain all active accounts");
            }
        }

        Account a = bank.getAccount(n2);
        if (a == null) {
            fail("method getAccount must return all created accounts, even if they are closed.");
        }
    }

    @Test
    @DisplayName("getAccount with invalid argument must not throw an Exception")
    void test13() throws Exception {
        try {
            Account a = bank.getAccount("xxxxxxxx"); // we assume that this is not a valid number
            if (a != null) {
                fail("if getAccount is called with an invalid account number then null must be returned.");
            }
        } catch (Exception e) {
            fail("if bank.getAccount is called with an invalid account number null is expected, not an exception.");
        }
    }

    @Test
    @DisplayName("Closing of closed accounts")
    void test14() throws Exception {
        try {
            String id = bank.createAccount("test14");
            Account a = bank.getAccount(id);
            double bal = a.getBalance();
            a.withdraw(bal);

            boolean close1 = bank.closeAccount(id);
            boolean close2 = bank.closeAccount(id);
            if (!close1) {
                fail("An account which has just been created must be closeable, but this implementation returned false.");
            } else if (close2) {
                fail("If closeAccount(id) is invoked on an account twice, then false must be returned on the second invocation.");
            }
        } catch (Exception e) {
            fail("closing a closed account must not throw an exception but simply return false.");
        }
    }

    @Test
    @DisplayName("Accessing same account twice")
    void test15() throws Exception {
        try {
            String id = bank.createAccount("test15");
            Account a1 = bank.getAccount(id);
            double bal = a1.getBalance();
            a1.withdraw(bal);

            Account a2 = bank.getAccount(id);

            a1.deposit(100);
            if (a2.getBalance() != 100) {
                fail("if an account is accessed twice with getAccount(id), and if on the first reference\n"
                        + "its value is changed, then this change must also be visible over the second reference");
            }
        } catch (Exception e) {
            fail("accessing an account twice with getAccount(id), and then invoking deposit on the first reference\n"
                    + "and getBalance on the second reference, this must not lead to an exception.");
        }
    }

    @Test
    @DisplayName("Is an exception thrown if deposit is invoked with a negative argument?")
    void test16() throws Exception {
        String id = bank.createAccount("test16");
        Account a = bank.getAccount(id);
        try {
            a.deposit(100);
            a.deposit(-50);
            fail("An IllegalArgumemtException must be thrown if method deposit is called with a negative argument");
        } catch (IllegalArgumentException e) {
            // expected exception
        } catch (Exception e) {
            fail("An IllegalArgumemtException must be thrown if method deposit is called with a negative argument");
        }
    }

    @Test
    @DisplayName("Is it possible to invoke close on non-existing accounts?")
    void test17() throws Exception {
        try {
            boolean res = bank.closeAccount("test17"); // this account dies not exist
            if (res) {
                fail("closeAccount must return false if invoked with a non-existing account number.");
            }
        } catch (Exception e) {
            fail("closeAccount must not throw an exception if invoked with a non-existing account number.");
        }
    }

    @Test
    @DisplayName("does getNumber return the correct number?")
    void test18() throws Exception {
        try {
            String id = bank.createAccount("test18");
            Account a = bank.getAccount(id);
            if (!id.contentEquals(a.getNumber())) {
                fail("getNumber does not return the number which was returned when the account was created.");
            }
            bank.closeAccount(id);
        } catch (Exception e) {
            fail("Invoking getNumber on an account must not throw an exception");
        }
    }

}