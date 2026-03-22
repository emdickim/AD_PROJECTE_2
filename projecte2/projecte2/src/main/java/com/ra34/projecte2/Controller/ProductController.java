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

    

    @PostMapping //  Kim
    public
    //Afegir un producte

    

   // @PatchMapping //  Kim
    //Modificar l’estoc de productes


    

    //@DeleteMapping //  Kim
    //Borrat físic d'un producte

    
}
