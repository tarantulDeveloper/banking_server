package kg.devcats.server.endpoint;


import kg.devcats.server.dto.request.ProductCreateRequest;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.ProductFullInfoResponse;
import kg.devcats.server.dto.response.ProductResponseForClient;
import kg.devcats.server.dto.response.ProductResponseForManager;
import kg.devcats.server.entity.Catalog;
import kg.devcats.server.entity.Currency;
import kg.devcats.server.entity.Product;
import kg.devcats.server.exception.CurrencyNotFoundException;
import kg.devcats.server.exception.ProductNotFoundException;
import kg.devcats.server.exception.UserNotFoundException;
import kg.devcats.server.mapper.ProductMapper;
import kg.devcats.server.repo.CurrencyRepository;
import kg.devcats.server.service.CatalogService;
import kg.devcats.server.service.ImageStorageService;
import kg.devcats.server.service.ProductService;
import kg.devcats.server.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductEndpoint {

    ProductService productService;
    ImageStorageService imageStorageService;
    ProductMapper productMapper;
    CurrencyRepository currencyRepository;
    UserService userService;
    CatalogService catalogService;



    public List<ProductResponseForClient> getAllProducts() {
        List<Product> products = productService.findAll();
        return getProductResponseForClients(products);
    }

    private List<ProductResponseForClient> getProductResponseForClients(List<Product> products) {
        return products.stream().map(product -> {
            BigDecimal currencyValue = product.getCurrency().getValue();
            BigDecimal priceInSom = product.getPrice().add(product.getCommission()).multiply(currencyValue);
            priceInSom = priceInSom.setScale(0, RoundingMode.HALF_UP);
            return new ProductResponseForClient(product.getId(), product.getName(),
                    product.getImageUrl(), product.getQuantity(),priceInSom);
        }).collect(Collectors.toList());
    }

    public List<ProductResponseForClient> getAllByOwnerEmail(String dealerEmail) {
        List<Product> products = productService.findAllByOwnerEmail(dealerEmail);
        return getProductResponseForClients(products);
    }


    public List<ProductResponseForManager> getAllProductsForManager() {
        List<Product> products = productService.findAll();
        Currency softCoinValue = currencyRepository.findByIsoCode("SFC")
                .orElseThrow(CurrencyNotFoundException::new);

        return products.stream().map(product -> {
            BigDecimal productCurrencyValue = product.getCurrency().getValue();
            BigDecimal priceInSoftCoin = product.getPrice().add(product.getCommission()).multiply(productCurrencyValue).multiply(softCoinValue.getValue());
            priceInSoftCoin = priceInSoftCoin.setScale(0,RoundingMode.HALF_UP);
            BigDecimal commissionInSoftCoin = product.getCommission().multiply(productCurrencyValue).multiply(softCoinValue.getValue());
            commissionInSoftCoin = commissionInSoftCoin.setScale(0, RoundingMode.HALF_UP);
            return new ProductResponseForManager(product.getId(), product.getName(), product.getImageUrl(), priceInSoftCoin, product.getPercentageOfProfitForDealer(), product.getPercentageOfProfitForSystem(), commissionInSoftCoin);
        }).collect(Collectors.toList());
    }

    public MessageResponse deleteProductById(Long productId) {
        productService.delete(productId);
        return new MessageResponse("Product has been deleted");
    }

    @Transactional
    public MessageResponse updateProductById(Long productId, ProductCreateRequest productCreateRequest,
                                             String managerEmail) {
        if (productService.existsById(productId)) {
            updateOrCreateProduct(productCreateRequest, managerEmail, productId);
            return new MessageResponse("Product has been updated");
        }
        throw new ProductNotFoundException();
    }

    @Transactional
    public MessageResponse createProduct(ProductCreateRequest productCreateRequest, String managerEmail) {
        updateOrCreateProduct(productCreateRequest, managerEmail, null);
        return new MessageResponse("Product has been created");
    }

    public MessageResponse updateOrCreateProduct(ProductCreateRequest productCreateRequest,
                                                 String managerEmail,
                                                 Long productId) {
        String createdUrl = imageStorageService.uploadImage(productCreateRequest.file());
        log.info("Image has been saved to url " + createdUrl);
        Product product = productMapper.toEntity(productCreateRequest);
        Currency currency = currencyRepository.findByIsoCode(productCreateRequest.isoCode())
                .orElseThrow(CurrencyNotFoundException::new);
        product.setImageUrl(createdUrl);
        product.setCurrency(currency);
        product.setPastRate(currency.getValue());
        product.setManager(userService.findUserByEmail(managerEmail).orElseThrow(UserNotFoundException::new));
        if (productId != null) {
            product.setId(productId);
        }
        Product savedProduct = productService.saveProduct(product);
        Catalog catalog = catalogService.getCatalogByOwnerEmail(productCreateRequest.dealerEmail()).orElse(
                Catalog.builder()
                        .dealer(userService.findUserByEmail(productCreateRequest.dealerEmail())
                                .orElseThrow(UserNotFoundException::new))
                        .products(new ArrayList<>())
                        .build()
        );
        catalog.getProducts().add(savedProduct);
        catalogService.save(catalog);
        return (productId != null) ? new MessageResponse("Product has been updated") :
                new MessageResponse("Product has been created");
    }


    public ProductFullInfoResponse getProductById(Long productId) {
        return productService.findFullInfoById(productId);
    }
}
