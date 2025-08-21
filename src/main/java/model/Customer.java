package model;

public class Customer {
    private int customerId;
    private String accountNo;
    private String name;
    private String email;
    private String address;
    private String telephone;
    private boolean isActive;
    private int totalPurchases;

    public Customer() {
    }

    public Customer(int customerId, String accountNo, String name, String email, String address, String telephone, boolean isActive, int totalPurchases) {
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.isActive = isActive;
        this.totalPurchases = totalPurchases;
    }

    public Customer(String accountNo, String name, String email, String address, String telephone) {
        this.accountNo = accountNo;
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
}
