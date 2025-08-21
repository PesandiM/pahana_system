package dto;

public class CustomerDTO {
    private String accountNo;
    private String name;
    private String email;
    private String address;
    private String telephone;

    public CustomerDTO() {
    }

    public CustomerDTO(String accountNo, String name, String email, String address, String telephone) {
        this.accountNo = accountNo;
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
