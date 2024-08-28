package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPictureDTO {
    @NotBlank
    private String productVariantId;

    @NotEmpty
    private byte[] image;
}
