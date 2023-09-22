package database;

import fileInput.CredentialsInput;

/**
 * Adds int field for storing balance to CredentialsInput Class.
 */
public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;
    private int intBalance;

    /* Constructor */
    public Credentials(CredentialsInput credentialsInput) {
        this.name = credentialsInput.getName();
        this.password = credentialsInput.getPassword();
        this.accountType = credentialsInput.getAccountType();
        this.country = credentialsInput.getCountry();
        this.balance = credentialsInput.getBalance();

        this.intBalance = Integer.parseInt(this.balance);
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }
    public int getIntBalance() {
        return intBalance;
    }
    public void setIntBalance(int intBalance) {
        this.intBalance = intBalance;
    }
}
