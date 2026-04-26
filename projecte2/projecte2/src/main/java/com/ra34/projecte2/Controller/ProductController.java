package com.ra34.projecte2.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.ra34.projecte2.Service.ProductService;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.DTO.ProductDTO;
import com.ra34.projecte2.DTO.ErrorDTO;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> allProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        try {
            Product updated = productService.updateProduct(id, product);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            Product updatedProduct = productService.updateStock(id, stock);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<?> updatePrice(
            @PathVariable Long id,
            @RequestParam Double price) {
        try {
            Product updated = productService.updatePrice(id, price);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/search/nom")
    public ResponseEntity<?> searchByName(@RequestParam String prefix) {
        try {
            List<Product> products = productService.searchByName(prefix);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/search/order")
    public ResponseEntity<?> searchByOrder(@RequestParam String order) {
        try {
            List<ProductDTO> products = productService.searchByOrder(order);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/search/price-range")
    public ResponseEntity<?> searchByPriceRange(
            @RequestParam Double priceMin,
            @RequestParam Double priceMax,
            @RequestParam String camp,
            @RequestParam String order,
            @RequestParam int limit) {
        try {
            List<ProductDTO> products = productService.searchByPriceRange(
                    priceMin, priceMax, camp, order, limit
            );
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            ErrorDTO error = new ErrorDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/carrgarCSV")
    public ResponseEntity<?> carregarCsv(@RequestParam("file") MultipartFile file) {
        try {
            int total = productService.carregaMassivaCsv(file);
            return ResponseEntity.ok("Productes afegits: " + total);

        } catch (Exception e) {
            log.error("Error carregant CSV", e);
            ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/search/condition")
    public ResponseEntity<?> searchByCondition(@RequestParam String condition) {
        try {
            List<ProductDTO> products = productService.searchByCondition(condition);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/search/order")
    public ResponseEntity<?> searchByRatingOrder(@RequestParam String order) {
        try {
            List<ProductDTO> products = productService.searchByRatingOrder(order);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/search/rating")
    public ResponseEntity<?> searchByRatingRange(
            @RequestParam Double ratingMin,
            @RequestParam Double ratingMax,
            @RequestParam String camp,
            @RequestParam String order,
            @RequestParam int limit) {
        try {
            List<ProductDTO> products = productService.searchByRatingRange(ratingMin, ratingMax, camp, order, limit);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/top10/new")
    public ResponseEntity<?> top10NewProducts() {
        try {
            List<ProductDTO> products = productService.top10NewProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getProductsPaginated(@RequestParam(defaultValue = "0") int page) {
        try {
            List<ProductDTO> products = productService.getProductsPaginated(page);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}