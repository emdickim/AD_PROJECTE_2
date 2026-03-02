package com.ra34.projecte2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.ra34.projecte2.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    //Consultar tots els productes

    @GetMapping
    public List<Product> getProductById() {
    }

    @AddMapping
    //Afegir un producte

    @PutMapping
    //Modificar tots els camps d’un producte
    /*

Modificar l’estoc de productes
Modificar el preu d’un producte
Borrat físic d'un producte
Borrat lògic d’un producte

     */
}
