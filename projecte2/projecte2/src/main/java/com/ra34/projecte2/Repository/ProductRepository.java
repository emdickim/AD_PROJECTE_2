package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ra34.projecte2.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingAndStatusTrue(String name);

    List<Product> findByStatusTrue(Sort sort);

    List<Product> findByStatusTrue();

    List<Product> findByPriceBetweenAndStatusTrue(
            Double priceMin,
            Double priceMax,
            Pageable pageable
    );

    List<Product> findByConditionAndStatusTrue(String condition);

    @Query("SELECT p FROM Product p WHERE p.rating BETWEEN :ratingMin AND :ratingMax AND p.status = true")
    List<Product> findByRatingRange(@Param("ratingMin") Double ratingMin,
                                    @Param("ratingMax") Double ratingMax,
                                    Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.condition = com.ra34.projecte2.Model.Condition.NEW AND p.status = true ORDER BY p.rating DESC")
    List<Product> findTop10NewProducts(Pageable pageable);

    Page<Product> findByStatusTrue(Pageable pageable);
}