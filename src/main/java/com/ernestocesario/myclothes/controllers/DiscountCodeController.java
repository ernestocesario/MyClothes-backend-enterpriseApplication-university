package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.DiscountCodeMapper;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.discountCode.DiscountCodeCreationDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.discountCode.DiscountCodeDTO;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import com.ernestocesario.myclothes.services.implementations.DiscountCodeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${discountCodeControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DiscountCodeController {


    private final DiscountCodeServiceImpl discountCodeServiceImpl;
    private final DiscountCodeMapper discountCodeMapper;

    @GetMapping("")
    public ResponseEntity<List<DiscountCodeDTO>> getDiscountCodes() {
        List<DiscountCode> discountCodes = discountCodeServiceImpl.getMyDiscountCodes();
        return ResponseEntity.ok(discountCodes.stream().map(discountCodeMapper::toDiscountCodeDTO).toList());
    }

    @PostMapping("")
    public ResponseEntity<Void> addDiscountCode(@Valid @RequestBody DiscountCodeCreationDTO discountCodeCreationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidInputException();

        discountCodeServiceImpl.addDiscountCodeToCustomer(discountCodeCreationDTO.getDiscountPercentage(), discountCodeCreationDTO.getCustomerEmail(), false);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{discountCodeId}")
    public ResponseEntity<Void> deleteDiscountCode(@PathVariable String discountCodeId) {
        discountCodeServiceImpl.removeDiscountCodeById(discountCodeId, false);
        return ResponseEntity.ok().build();
    }
}