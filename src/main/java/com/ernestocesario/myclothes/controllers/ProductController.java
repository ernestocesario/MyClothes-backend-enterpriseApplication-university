package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.ProductMapper;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductVariantDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductPictureDTO;
import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.ProductPicture;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import com.ernestocesario.myclothes.services.implementations.ProductServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${productsControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;
    private final ProductMapper productMapper;

    @GetMapping("")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productServiceImpl.getAllProducts(pageable);
        return ResponseEntity.ok(productPage.map(productMapper::toProductDTO));
    }

    @GetMapping(params = "keyword")
    public ResponseEntity<Page<ProductDTO>> getProductsByKeyword(@RequestParam String keyword, Pageable pageable) {
        Page<Product> productPage = productServiceImpl.getProductsByKeyword(keyword, pageable);
        return ResponseEntity.ok(productPage.map(productMapper::toProductDTO));
    }

    @GetMapping(params = "category")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(@RequestParam String category, Pageable pageable) {
        ProductCategory productCategory = ProductCategory.valueOf(category);

        Page<Product> productPage = productServiceImpl.getProductsByCategory(productCategory, pageable);
        return ResponseEntity.ok(productPage.map(productMapper::toProductDTO));
    }

    @GetMapping("${productProductsControllerSubPath}/{productId}")
    public ResponseEntity<FullProductDTO> getProductById(@PathVariable String productId) {
        Product product = productServiceImpl.getProductById(productId);
        return ResponseEntity.ok(productMapper.toFullProductDTO(product));
    }

    @GetMapping("${productVariantProductsControllerSubPath}/{productVariantId}")
    public ResponseEntity<FullProductVariantDTO> getProductVariantById(@PathVariable String productVariantId) {
        ProductVariant productVariant = productServiceImpl.getProductVariantById(productVariantId);
        return ResponseEntity.ok(productMapper.toFullProductVariantDTO(productVariant));
    }

    @GetMapping("${picturesProductVariantProductsControllerSubPath}/{productVariantId}")
    public ResponseEntity<List<ProductPictureDTO>> getProductVariantPictures(@PathVariable String productVariantId) {
        List<ProductPicture> productPictures = productServiceImpl.getProductVariantPictures(productVariantId);
        return ResponseEntity.ok(productPictures.stream().map(productMapper::toProductPictureDTO).toList());
    }

    @PostMapping("${productVariantProductsControllerSubPath}")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody FullProductVariantDTO fullProductVariantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidInputException();

        ProductVariant productVariant = productMapper.toProductVariant(fullProductVariantDTO);
        productServiceImpl.addNewProduct(productVariant);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("${productVariantProductsControllerSubPath}/{productVariantId}")
    public ResponseEntity<Void> deleteProductVariant(@PathVariable String productVariantId) {
        productServiceImpl.deleteProductVariant(productVariantId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("${productProductsControllerSubPath}/{productId}")
    public ResponseEntity<Void> deleteProductAndItsVariant(@PathVariable String productId) {
        productServiceImpl.deleteProductAndItsVariants(productId);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("${productProductsControllerSubPath}")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidInputException();

        Product product = productMapper.toProduct(productDTO);
        productServiceImpl.updateProductOnly(product);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("${productVariantProductsControllerSubPath}")
    public ResponseEntity<Void> updateProductVariant(@Valid @RequestBody FullProductVariantDTO fullProductVariantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || StringUtils.hasLength(fullProductVariantDTO.getProductId()))
            throw new InvalidInputException();

        ProductVariant productVariant = productMapper.toProductVariant(fullProductVariantDTO);
        productServiceImpl.updateProductVariant(productVariant);
        return ResponseEntity.ok().build();
    }

    @PutMapping("${picturesProductVariantProductsControllerSubPath}")
    public ResponseEntity<Void> setProductVariantPictures(@Valid @NotEmpty @RequestBody List<ProductPictureDTO> productPictureDTOs) {
        //check that all the productPictureDTOs have the same productVariantId
        if (productPictureDTOs.stream().map(ProductPictureDTO::getProductVariantId).distinct().count() != 1)
            throw new InvalidInputException();

        String productVariantId = productPictureDTOs.get(0).getProductVariantId();
        List<ProductPicture> productPictures = productPictureDTOs.stream().map(productMapper::toProductPicture).toList();

        productServiceImpl.setProductVariantPictures(productVariantId, productPictures);
        return ResponseEntity.ok().build();
    }
}
