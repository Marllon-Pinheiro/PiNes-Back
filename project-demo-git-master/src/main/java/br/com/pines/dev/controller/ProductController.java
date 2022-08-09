package br.com.pines.dev.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pines.dev.model.Product;
import br.com.pines.dev.model.dto.ProductDto;
import br.com.pines.dev.repository.ProductRepository;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @GetMapping
    @Transactional
    public List<Product> listProducts(String name){
    	if(name != null) {
    	List<Product> products = productRepository.findByNameContaining(name); 	        
       	 return products;
    	} else {
    		List<Product> products = productRepository.findAll(); 
    		return products; 
    	} 
    	
    }
    
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductDto> productById(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            ProductDto productDto = product.get().conversor();
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.notFound().build();
    }

    
    @PostMapping("/new-product")
    @Transactional
    public ResponseEntity<ProductDto> registrationProduct(@RequestBody ProductDto productDto) {
        Product product = productDto.conversor();
        productRepository.save(product);
        return ResponseEntity.ok(productDto);
    }

    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

   
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productRepository.findById(id)
                .map(updatedProduct -> {
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            return ResponseEntity.ok().body(productRepository.save(updatedProduct));
        }).orElse(ResponseEntity.notFound().build());
    }
}