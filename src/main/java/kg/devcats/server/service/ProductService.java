package kg.devcats.server.service;

import kg.devcats.server.dto.response.ProductFullInfoResponse;
import kg.devcats.server.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> findAll();

    Product getProductById(Long id);

    void delete(Long productId);

    boolean existsById(Long productId);

    List<Product> findAllByOwnerEmail(String email);

    ProductFullInfoResponse findFullInfoById(Long productId);
}
