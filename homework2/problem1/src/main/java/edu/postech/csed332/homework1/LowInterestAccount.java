package edu.postech.csed332.homework1;

/**
 * An account with a low interest rate. The rate is 0.5% per day.
 * E.g., if the balance is initially 100, after 10 days (on the 11th day)
 * the balance will be 100*(1.005)^10.
 */
class LowInterestAccount implements Account {
    //TODO implement this
    String owner;
    Integer accountNumber;
    double balance;

    public LowInterestAccount(String owner, Integer accountNumber, double balance){
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
            balance = balance * 1.005;
        }
    }

    public void deposit(double amount) {
        //TODO implement this
        balance += amount;
    }

    public void withdraw(double amount) throws IllegalOperationException {
        //TODO implement this
        if (amount > balance) {
            throw new IllegalOperationException("예금이 부족합니다.");
        }
        balance -= amount;
    }
}
