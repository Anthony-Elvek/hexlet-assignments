package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceBadRequestException;
import exercise.mapper.CategoryMapper;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.model.Product;
import exercise.repository.CategoryRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "")
    List<ProductDTO> index() {
        return productRepository.findAll().stream()
                .map(product -> productMapper.map(product))
                .toList();
    }

    @GetMapping(path = "/{id}")
    ProductDTO show(@PathVariable long id) {
        var product = checkProduct(id);

        return productMapper.map(product);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO create(@Valid @RequestBody ProductCreateDTO productData) {
        var product = productMapper.map(productData);
        var category = checkCategory(productData.getCategoryId());

        product.setCategory(category);
        productRepository.save(product);

        return productMapper.map(product);
    }

    @PutMapping(path = "/{id}")
    ProductDTO update(@Valid @RequestBody ProductUpdateDTO productData,
                      @PathVariable long id) {
        var product = checkProduct(id);
        var category = checkCategory(productData.getCategoryId().get());

        productMapper.update(productData, product);
        product.setCategory(category);
        productRepository.save(product);

        return productMapper.map(product);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }

    private Category checkCategory(Long categoryID) {
        return categoryRepository.findById(categoryID).orElseThrow(
                () -> new ResourceBadRequestException("Category not found")
        );
    }

    private Product checkProduct(Long productID) {
        return productRepository.findById(productID).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product id %d is not found", productID))
        );
    }
    // END
}
