package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.ProductDTO;
import com.ra34.projecte2.Controller.ErrorDTO;
import com.ra34.projecte2.Model.Condition;

import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        product.setStatus(true);
        product.setDataCreated(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateStock(Long id, Integer stock) {
        // Busquem el producte per id
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));
        
        // Actualitzem només l'estoc
        product.setStock(stock);
        product.setDataUpdated(LocalDateTime.now());
        
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
    // Comprovem que existeix abans d'esborrar
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));
        
        productRepository.deleteById(id);
    }

    public List<Product> searchByName(String prefix) {
        return productRepository.findByNameContainingAndStatusTrue(prefix);
    }

    public List<ProductDTO> searchByOrder(String order) {
    // Creem el Sort segons el valor de order
        Sort sort = order.equalsIgnoreCase("asc") 
            ? Sort.by("price").ascending() 
            : Sort.by("price").descending();

        List<Product> products = productRepository.findByStatusTrue(sort);

        // Convertim Product → ProductDTO
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setRating(product.getRating());
            dto.setCondition(product.getCondition());
            productDTOs.add(dto);
        }
        return productDTOs;
    }

    public List<ProductDTO> searchByPriceRange(Double priceMin, Double priceMax, 
                                            String camp, String order, int limit) {
        // Creem el Sort (ordre)
        Sort sort = order.equalsIgnoreCase("asc") 
            ? Sort.by(camp).ascending() 
            : Sort.by(camp).descending();
        
        // Pageable per limitar resultats
        Pageable pageable = PageRequest.of(0, limit, sort);
        
        List<Product> products = productRepository.findByPriceRange(
            priceMin, priceMax, camp, pageable
        );
        
        // Convertim a DTO
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product p : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setStock(p.getStock());
            dto.setPrice(p.getPrice());
            dto.setRating(p.getRating());
            dto.setCondition(p.getCondition());
            dtos.add(dto);
        }
        return dtos;
    }




    @Transactional
    public int carregaMassivaCsv(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();
        int lineNumber = 0; 

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    Product product = new Product();
                    product.setName(fields[0]);
                    product.setDescription(fields[1]);
                    product.setStock(Integer.parseInt(fields[2]));
                    product.setPrice(Double.parseDouble(fields[3]));
                    product.setRating(Double.parseDouble(fields[4]));
                    product.setCondition(Condition.valueOf(fields[5].trim().toUpperCase()));
                    product.setStatus(true);
                    products.add(product);
                }
            }
            productRepository.saveAll(products);
            return products.size();

        } catch (Exception e) {
            // Rellacem amb el número de línia → @Transactional fa rollback
            throw new RuntimeException("Error a la línia " + lineNumber + ": " + e.getMessage());
        }
    }
}