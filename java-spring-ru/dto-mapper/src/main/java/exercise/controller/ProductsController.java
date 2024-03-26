package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired
    private ProductMapper productMapper;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO productCreateDTO) {
        var product = productMapper.map(productCreateDTO);
        productRepository.save(product);

        return productMapper.map(product);
    }

    @GetMapping(path = "")
    public List<ProductDTO> showAll() {
        var productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (var product : productList) {
            productDTOList.add(productMapper.map(product));
        }

        return  productDTOList;
    }

    @GetMapping(path = "/{id}")
    public ProductDTO getProduct(@PathVariable long id) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product id %d is not found", id))
        );

        return productMapper.map(product);
    }

    @PutMapping(path = "/{id}")
    public ProductDTO update(@RequestBody ProductUpdateDTO productData,
                             @PathVariable long id) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product id %d is not found", id))
        );
        productMapper.update(productData, product);
        productRepository.save(product);

        return productMapper.map(product);
    }
    // END
}
