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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import com.ra34.projecte2.Service.ProductService;
import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.ProductDTO;



@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/all")  //  Kim
    public ResponseEntity<List<Product>> allProducts() {

        try {
            List<Product> products = productService.getAllProducts();
            
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            // Error inesperat → 500
            ErrorDTO error = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }
    

    

    // @PatchMapping //  Kim
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            Product updatedProduct = productService.updateStock(id, stock);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            // Producte no trobat → 404
            ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            // Error inesperat → 500
            ErrorDTO error = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
            );
            return ResponseEntity.internalServerError().body(error);
        }
    }



    

    //@DeleteMapping //  Kim
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); 
        } catch (RuntimeException e) {
            // Producte no trobat → 404
            ErrorDTO error = new ErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            // Error inesperat → 500
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
    @GetMapping("/search/order")
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
    
}
