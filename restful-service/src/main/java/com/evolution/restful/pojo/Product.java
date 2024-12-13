package com.evolution.restful.pojo;

public class Product {

    private Long id;
    private String productName;
    private Double productAmount;
    private Long importUser;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getImportUser() {
        return importUser;
    }

    public void setImportUser(Long importUser) {
        this.importUser = importUser;
    }

    public Double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Double productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
