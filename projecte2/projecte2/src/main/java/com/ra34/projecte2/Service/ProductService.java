package com.ra34.projecte2.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra34.projecte2.Model.Product;
import com.ra34.projecte2.Model.Condition;

import com.ra34.projecte2.Repository.ProductRepository;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public int carregaMassivaCsv(MultipartFile file) throws IOException{
        // Implementa la lògica per carregar el fitxer CSV i guardar els productes a la base de dades
        // Retorna el nombre de productes carregats
        try (BufferedReader br = new BufferedReader(
        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    Product product = new Product();
                    product.setName(fields[0]);
                    product.setDescription(fields[1]);
                    product.setStock(Integer.parseInt(fields[2]));
                    product.setPrice(Double.parseDouble(fields[3]));
                    product.setRating(Double.parseDouble(fields[4]));
                    product.setCondition(Condition.valueOf(fields[5].toUpperCase()));
                    product.setStatus(true);
                    productRepository.save(product);
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}