package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.ProductPicture;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Page<Product> getProductsByKeyword(String keyword, Pageable pageable);
    Page<Product> getProductsByCategory(ProductCategory productCategory, Pageable pageable);
    Product getProductById(String productId);
    ProductVariant getProductVariantById(String productVariantId);
    List<ProductPicture> getProductVariantPictures(String productVariantId);

    boolean addNewProduct(ProductVariant productVariant);  //only admin
    boolean deleteProductVariant(String productVariantId);  //only admin
    boolean deleteProductAndItsVariants(String productId);  //only admin
    boolean updateProductOnly(Product product);  //only admin
    boolean updateProductVariant(ProductVariant productVariant);  //only admin
    boolean setProductVariantPictures(String productVariantId, List<ProductPicture> productPictures);  //only admin
}