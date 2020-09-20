package edu.postech.csed332.homework1;

/**
 * An account with a high interest rate(이자율) and a minimum balance(잔고).
 * The rate is 1% per day. E.g., if the balance is initially 100,
 * after 10 days (on the 11th day) the balance will be 100*(1.01)^10.
 * The balance should always be greater than or equal to 1000.
 */
class HighInterestAccount implements Account {
    //TODO implement this
    String owner;
    int accountNumber;
    double balance;

    public HighInterestAccount(String owner, int accountNumber, double balance){
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        //TODO implement this
        return accountNumber;
    }

    public double getBalance() {
        //TODO implement this
        return balance;
    }

    public String getOwner() {
        //TODO implement this
        return owner;
    }

    public void updateBalance(int elapsedDate) {
        //TODO implement this
        for(int i=0 ; i<elapsedDate ; i++) {
            balance = balance * 1.01;
        }
    }

    public void deposit(double amount) {
        //TODO implement this
        balance += amount;
    }

    public void withdraw(double amount) throws IllegalOperationException {
        //TODO implement this
        if (balance - amount < 1000) {
            throw new IllegalOperationException("예금 부족");
        }
        balance -= amount;
    }
}
