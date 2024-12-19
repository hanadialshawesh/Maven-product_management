package com.example.product_management.repository;

import com.example.product_management.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private List<Product> products;

    // Constructor to initialize the list with some data
    public ProductRepository() {
        products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 1200.50, "Electronics"));
        products.add(new Product(2, "Smartphone", 800.00, "Electronics"));
        products.add(new Product(3, "Headphones", 150.75, "Accessories"));
        products.add(new Product(4, "Monitor", 250.00, "Electronics"));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product findById(int id) {
        return products.stream()
            .filter(product -> product.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }

    public void deleteAllProducts() {
        products.clear();
    }
}
