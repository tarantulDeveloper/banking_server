package kg.devcats.server.controller;


import jakarta.validation.Valid;
import kg.devcats.server.dto.request.ProductCreateRequest;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.ProductFullInfoResponse;
import kg.devcats.server.dto.response.ProductResponseForClient;
import kg.devcats.server.dto.response.ProductResponseForManager;
import kg.devcats.server.endpoint.ProductEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductEndpoint productEndpoint;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse createProduct(@Valid @ModelAttribute ProductCreateRequest productCreateRequest,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return productEndpoint.createProduct(productCreateRequest, userDetails.getUsername());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{productId}")
    public MessageResponse updateProduct(@PathVariable Long productId,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         @Valid @ModelAttribute ProductCreateRequest productCreateRequest) {
        return productEndpoint.updateProductById(productId,productCreateRequest,userDetails.getUsername());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{productId}")
    public MessageResponse deleteProduct(@PathVariable Long productId) {
        return productEndpoint.deleteProductById(productId);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/{productId}")
    public ProductFullInfoResponse getProductById(@PathVariable("productId") Long productId) {
        return productEndpoint.getProductById(productId);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/myProducts")
    public List<ProductResponseForClient> fetchDealerProducts(
            @AuthenticationPrincipal UserDetails userDetail) {
        return productEndpoint.getAllByOwnerEmail(userDetail.getUsername());
    }

    @GetMapping()
    public List<ProductResponseForClient> fetchAllProducts() {
        return productEndpoint.getAllProducts();
    }

    @GetMapping("/manager")
    public List<ProductResponseForManager> fetchAllProductsForManager() {
        return productEndpoint.getAllProductsForManager();
    }


}
