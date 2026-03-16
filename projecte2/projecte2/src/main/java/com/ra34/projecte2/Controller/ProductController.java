package com.ra34.projecte2.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.ra34.projecte2.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping  //  Kim
    //Consultar tots els productes

    @GetMapping //  mark
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        log.info("GET /Products/{} - Obtener Productos", id);

        Product product = ProductService.findById(id);
        if (product == null) {
            log.warn("Producte amb id {} no trobat", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping //  Kim
    public
    //Afegir un producte

    @PutMapping("/{id}") //  mark
    //Modificar tots els camps d’un producte
    public ResponseEntity<Void> updateProducte(
            @PathVariable long id,
            @RequestBody
            )


    @PatchMapping //  Kim
    //Modificar l’estoc de productes


    @PatchMapping //  mark
    //Modificar el preu d’un producte

    @DeleteMapping //  Kim
    //Borrat físic d'un producte

    @DeleteMapping //  mark
    //Borrat lògic d’un producte
}
