package edu.postech.csed332.homework1;

/**
 * An account with a low interest rate. The rate is 0.5% per day.
 * E.g., if the balance is initially 100, after 10 days (on the 11th day)
 * the balance will be 100*(1.005)^10.
 */
class LowInterestAccount implements Account {
    //TODO implement this
    String accountName;
    Integer accountNumber;
    double balance;

    public LowInterestAccount(String accountName, Integer accountNumber, double balance){
        this.accountName = accountName;
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
        return accountName;
    }

    public void updateBalance(int elapsedDate) {
        //TODO implement this
        this.balance = balance * Math.pow(1.005, elapsedDate);
    }

    public void deposit(double amount) {
        //TODO implement this
        this.balance += amount;
    }

    public void withdraw(double amount) throws IllegalOperationException {
        //TODO implement this
        if (amount > balance) {
            throw new IllegalOperationException("예금 부족");
        }
        this.balance -= amount;
    }
}
