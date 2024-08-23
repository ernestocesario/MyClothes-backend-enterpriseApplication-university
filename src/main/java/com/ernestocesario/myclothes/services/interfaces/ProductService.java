package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.ProductPicture;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<ProductVariant> getAllProductVariantsOfAProduct(String productId);
    Product getProductById(String productId);
    ProductVariant getProductVariantById(String productVariantId);
    ProductPicture getProductVariantPicture(String productVariantId);

    boolean addNewProduct(ProductVariant productVariant);
    boolean deleteProductVariant(String productVariantId);
    boolean deleteCompleteProduct(String productId);
    boolean updateProductVariant(ProductVariant productVariant);
}
