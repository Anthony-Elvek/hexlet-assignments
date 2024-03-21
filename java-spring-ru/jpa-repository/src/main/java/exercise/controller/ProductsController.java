package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> getProducts(@RequestParam(required = false) Integer min,
                                     @RequestParam(required = false)  Integer max) {
        return getProductsByPrice(min, max);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }

    private List<Product> getProductsByPrice(Integer min, Integer max) {
        if (min == null && max != null) {
            return productRepository.findProductsByMaxPrice(max);
        } else if (min != null && max == null) {
            return productRepository.findProductsByMinPrice(min);
        } else if (min != null) {
            return productRepository.findProductsByPrice(min, max);
        } else {
            return productRepository.findAll();
        }
    }
}
