package com.company;

public class BankBranch implements Comparable<BankBranch>{

    //Class Variables
    private String firstName = "";
    private String lastName = "";
    private int acct = 0;
    private short pin = 0;
    private double balance = 0;
    private short attempts = 0;

    //Constructor
    public BankBranch(String first, String last, int acc, short pswrd, double bal){
        // pre: account is a string identifying the bank account
        // balance is the starting balance
        // post: constructs a bank account with desired balance

        this.firstName += first;
        this.lastName += last;
        this.acct = acc;
        this.pin = pswrd;
        this.balance = bal;
    }


    public boolean equals(Object other) {
        // pre: other is a valid bank account
        // post: returns true if this bank account is the same as other
        boolean valid = false;

        return valid;
    }

    public String getFirstName(){
        // post: returns first name of account.
        return firstName;
    }

    public String getLastName(){
        // post: returns last name of account.
        return lastName;
    }


    public int getAccount() {
        // post: returns the bank account number of this account

        return acct;
    }

    public short getPin() {
        // post: returns the pin number of this account

        return pin;
    }


    public double getBalance() {
        // post: returns the balance of this bank account

        return balance;
    }


    public void deposit(double amount) {
        // post: deposit money in the bank account

        balance += amount;
    }


    public void withdraw(double amount) {
        // pre: there are sufficient funds in the account
        // post: withdraw money from the bank account

        if( (balance - amount) > 0){
            balance -= amount;
        }
        else{
            System.out.println("Insufficient Funds!");
        }
    }

    public void displayAcct(){

        System.out.println("////////////////////////");
        System.out.printf("Name: %18s%n", getFirstName() + " " + getLastName());
        System.out.printf("Account: %15d%n", getAccount());
        System.out.printf("Balance: %15.2f%n", getBalance());
        System.out.println("////////////////////////");

    }

    @Override
    public int compareTo(BankBranch obj){

        //sort in ascending order
        return this.acct-obj.acct;

        //sort in descending order
        //return obj.age-this.age;
    }

}
