package edu.postech.csed332.homework1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank wb;

    @BeforeEach
    void setup() {
        wb = new Bank();
    }


    @Test
    void testFindAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account reqAccount = wb.findAccount(100000);
        assertEquals(reqAccount.getOwner(), "Thomas");
    }

    @Test
    void testHighInterestAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account s = wb.findAccount(100000);
        s.deposit(10000.);
        s.updateBalance(20);
        assertEquals(s.getBalance(), 122019.00399479672);
    }

    @Test
    void testFindInvaildAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account reqAccount = wb.findAccount(100001);
        assertEquals(reqAccount, null);
    }

    @Test
    void testInvaildInitialBalance() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 50.);
        Account reqAccount = wb.findAccount(100000);
        assertEquals(reqAccount, null);
    }

    @Test
    void testInvaildTransferOperation() throws IllegalOperationException {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        wb.createAccount("Thomas", ACCTYPE.HIGH, 60000.);
        wb.createAccount("Thomas", ACCTYPE.LOW, 30000.);
        wb.createAccount("James", ACCTYPE.LOW, 50000.);
        Account reqAccount = wb.findAccountByName("Thomas").get(0);
        Account resAccount = wb.findAccountByName("James").get(0);
        wb.transfer(reqAccount, resAccount, 100000);
        StringBuilder sb = new StringBuilder("");
        for (Account acc : wb.findAccountByName("Thomas")) {
            sb.append(Integer.toString(acc.getAccountNumber()));
        }
        assertEquals(sb.toString(), "100000100001100002");
    }


}

