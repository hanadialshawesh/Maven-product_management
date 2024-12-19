package com.example.product_management.controller;

import com.example.product_management.model.Product;
import com.example.product_management.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController //Defines the base URL (/products) for all endpoints in this controller.
@RequestMapping("/products")
public class ProductController {
    private ProductRepository repository = new ProductRepository();

    @GetMapping
    public String welcome() { //http://localhost:8080/products

        return "Welcome to the Product Management System!";
    }

    @GetMapping("/all") //http://localhost:8080/products/all
    public List<Product> getAllProducts() {

        return repository.getAllProducts();
    }

    @GetMapping("/{id}") //http://localhost:8080/products/1
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        Product product = repository.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    @PostMapping("/add") //http://localhost:8080/products/add
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        if (product.getPrice() < 0) {
            return ResponseEntity.badRequest().body("Price cannot be negative.");
        }
        repository.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully!");
    }

    @DeleteMapping("/{id}") //http://localhost:8080/products/3
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        repository.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    @DeleteMapping("/all") //http://localhost:8080/products/all
    public ResponseEntity<String> deleteAllProducts() {
        repository.deleteAllProducts();
        return ResponseEntity.ok("All products deleted.");
    }

    // New Search Method
    @GetMapping("/search")
    //http://localhost:8080/products/search?keyword=laptop
    public List<Product> searchProducts(@RequestParam(required = false) String keyword, @RequestParam(required = false) String category) {
        //This annotation maps the keyword query parameter from the URL (e.g., ?keyword=laptop) to the keyword parameter in the method.
        return repository.getAllProducts()
                .stream()
                .filter(product -> (keyword == null || product.getName().toLowerCase().contains(keyword.toLowerCase())) &&
                        category == null || product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}


