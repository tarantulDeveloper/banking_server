package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.ProductFullInfoResponse;
import kg.devcats.server.entity.Product;
import kg.devcats.server.exception.ProductNotFoundException;
import kg.devcats.server.repo.ProductRepository;
import kg.devcats.server.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAllExisting();
    }

    @Override
    public void delete(Long productId) {
        productRepository.softDelete(productId);
    }

    @Override
    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public List<Product> findAllByOwnerEmail(String email) {
        return productRepository.findAllExistingByOwnerEmail(email);
    }

    @Override
    public ProductFullInfoResponse findFullInfoById(Long productId) {
        return productRepository.findFullInfoById(productId);
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

}
