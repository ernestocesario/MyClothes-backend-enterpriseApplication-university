package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.product.ProductAlreadyExistsException;
import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.ProductPicture;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import com.ernestocesario.myclothes.persistance.repositories.ProductRepository;
import com.ernestocesario.myclothes.persistance.repositories.ProductVariantRepository;
import com.ernestocesario.myclothes.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final IsAdmin isAdmin;
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public Page<Product> getAllProducts(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Page<Product> getProductsByKeyword(String keyword, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return productRepository.findAllByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    @Transactional
    public Page<Product> getProductsByCategory(ProductCategory productCategory, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return productRepository.findAllByCategory(productCategory, pageable);
    }

    @Override
    @Transactional
    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(InternalServerErrorException::new);
    }

    @Override
    @Transactional
    public ProductVariant getProductVariantById(String productVariantId) {
        return productVariantRepository.findById(productVariantId).orElseThrow(InternalServerErrorException::new);
    }

    @Override
    @Transactional
    public List<ProductPicture> getProductVariantPictures(String productVariantId) {
        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElseThrow(InternalServerErrorException::new);
        return productVariant.getPictures();
    }

    @Override
    @Transactional
    public boolean addNewProduct(ProductVariant productVariant) {  //here the Ids of the product and the productVariant are not set
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        boolean somethingChanged = false;
        Product product = productVariant.getProduct();

        if(!checkProductExistance(product)) {  //General Product do not exist, and it needs to be added
            productRepository.save(product);
            somethingChanged = true;
        }

        if(!checkProductVariantExistance(productVariant)) {  //Product Variant of the General Product do not exist, and it needs to be added
            productVariantRepository.save(productVariant);
            somethingChanged = true;
        }

        if (somethingChanged)
            return true;
        else
            throw new ProductAlreadyExistsException();
    }

    @Override
    @Transactional
    public boolean deleteProductVariant(String productVariantId) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElseThrow(InternalServerErrorException::new);
        productVariantRepository.delete(productVariant);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteProductAndItsVariants(String productId) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Product product = productRepository.findById(productId).orElseThrow(InternalServerErrorException::new);
        List<ProductVariant> productVariants = product.getProductVariants();

        productVariantRepository.deleteAll(productVariants);
        productRepository.delete(product);

        return true;
    }

    @Override
    @Transactional
    public boolean updateProductOnly(Product product) {  //here only the properties of Product are set. Not its variants.
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        //check if the product exists. If not throw an exception.
        if (productRepository.existsById(product.getId()))
            throw new InternalServerErrorException();

        productRepository.save(product);

        return true;
    }

    @Override
    @Transactional
    public boolean updateProductVariant(ProductVariant productVariant) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        //check if the productVariant exists. If not throw an exception.
        if (productVariantRepository.existsById(productVariant.getId()))
            throw new InternalServerErrorException();

        productVariantRepository.save(productVariant);

        return true;
    }


    //private methods
    private boolean checkProductExistance(Product product) {
        return productRepository.existsByBrandAndName(product.getBrand(), product.getName());
    }

    private boolean checkProductVariantExistance(ProductVariant productVariant) {
        return productVariantRepository.existsByGenderAndStyleAndSize(productVariant.getGender(), productVariant.getStyle(), productVariant.getSize());
    }
}
