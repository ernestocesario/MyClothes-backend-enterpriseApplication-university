package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, String> {
}
