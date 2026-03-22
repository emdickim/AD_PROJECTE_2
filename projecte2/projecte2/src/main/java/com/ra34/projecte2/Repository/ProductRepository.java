package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ra34.projecte2.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingAndStatusTrue(String name);
    List<Product> findByStatusTrue(Sort sort);
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceMin AND :priceMax " +
       "AND p.status = true ORDER BY " +
       "CASE WHEN :camp = 'price' THEN p.price END ASC")
        List<Product> findByPriceRange(
            @Param("priceMin") Double priceMin,
            @Param("priceMax") Double priceMax,
            @Param("camp") String camp,
            Pageable pageable
        );

}
