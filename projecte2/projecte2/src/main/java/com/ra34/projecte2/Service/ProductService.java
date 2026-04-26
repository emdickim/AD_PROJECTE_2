package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.ProductDTO;
import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByStatusTrue();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));
    }

    public Product save(Product product) {
        product.setStatus(true);
        product.setDataCreated(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setStock(updatedProduct.getStock());
        product.setPrice(updatedProduct.getPrice());
        product.setRating(updatedProduct.getRating());
        product.setCondition(updatedProduct.getCondition());
        product.setDataUpdated(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Product updateStock(Long id, Integer stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));

        product.setStock(stock);
        product.setDataUpdated(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Product updatePrice(Long id, Double price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));

        product.setPrice(price);
        product.setDataUpdated(LocalDateTime.now());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producte no trobat amb id: " + id));

        product.setStatus(false); // 👈 en lugar de borrar
        product.setDataUpdated(LocalDateTime.now());

        productRepository.save(product);
    }

    public List<Product> searchByName(String prefix) {
        return productRepository.findByNameContainingAndStatusTrue(prefix);
    }

    public List<ProductDTO> searchByOrder(String order) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by("price").ascending()
                : Sort.by("price").descending();

        List<Product> products = productRepository.findByStatusTrue(sort);

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

        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(camp).ascending()
                : Sort.by(camp).descending();

        Pageable pageable = PageRequest.of(0, limit, sort);

        List<Product> products = productRepository
                .findByPriceBetweenAndStatusTrue(priceMin, priceMax, pageable);

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
                    product.setCondition(String.valueOf(fields[5].trim().toUpperCase()));
                    product.setStatus(true);
                    products.add(product);
                }
            }
            productRepository.saveAll(products);
            return products.size();

        } catch (Exception e) {
            throw new RuntimeException("Error a la línia " + lineNumber + ": " + e.getMessage());
        }
    }
    public List<ProductDTO> searchByCondition(String condition) {
        List<Product> products = productRepository.findByConditionAndStatusTrue(condition);
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
    public List<ProductDTO> searchByRatingOrder(String order) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by("rating").ascending()
                : Sort.by("rating").descending();

        List<Product> products = productRepository.findByStatusTrue(sort);

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
    public List<ProductDTO> searchByRatingRange(Double ratingMin, Double ratingMax,
                                                String camp, String order, int limit) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(camp).ascending()
                : Sort.by(camp).descending();

        Pageable pageable = PageRequest.of(0, limit, sort);
        List<Product> products = productRepository.findByRatingRange(ratingMin, ratingMax, pageable);

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

    public List<ProductDTO> top10NewProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = productRepository.findTop10NewProducts(pageable);

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
    public List<ProductDTO> getProductsPaginated(int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
                                       
        Page<Product> productPage = productRepository.findByStatusTrue(pageable);
        List<ProductDTO> dtos = new ArrayList<>();

        for (Product p : productPage.getContent()) {
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

}