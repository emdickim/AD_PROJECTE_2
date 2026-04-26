package com.ra34.projecte2.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ra34.projecte2.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStatusTrue();

    List<Product> findByStatusTrue(Sort sort);

    Page<Product> findByStatusTrue(Pageable pageable);

    List<Product> findByPriceLessThanEqualAndStatusTrue(Double price, Pageable pageable);

    List<Product> findByNameContainingAndStatusTrue(String name);

    List<Product> findByPriceBetweenAndStatusTrue(Double priceMin, Double priceMax, Pageable pageable);

    List<Product> findByConditionAndStatusTrue(String condition);

    List<Product> findByRatingBetween(Double ratingMin, Double ratingMax, Pageable pageable);

    List<Product> findTop10NewProducts(Pageable pageable);
    List<Product> findByRatingRange(Double ratingMin, Double ratingMax, Pageable pageable);


}
