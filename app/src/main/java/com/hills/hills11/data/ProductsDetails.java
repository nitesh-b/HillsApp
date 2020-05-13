package com.hills.hills11.data;

public class ProductsDetails {
    private String productImageURL, productLink, productSupplier, productDescription, productNumber;

    public ProductsDetails(String productImageURL , String productLink , String productSupplier , String productDescription , String productNumber) {
        this.productImageURL = productImageURL;
        this.productLink = productLink;
        this.productSupplier = productSupplier;
        this.productDescription = productDescription;
        this.productNumber = productNumber;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public String getProductLink() {
        return productLink;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductNumber() {
        return productNumber;
    }
}
