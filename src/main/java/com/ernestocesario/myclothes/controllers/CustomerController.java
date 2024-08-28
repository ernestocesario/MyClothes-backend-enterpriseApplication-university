package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.CustomerMapper;
import com.ernestocesario.myclothes.exceptions.customer.InvalidCustomerShippingInfoException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users.CustomerProfileDTO;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import com.ernestocesario.myclothes.services.implementations.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${customersControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;
    private final CustomerMapper customerMapper;

    @GetMapping("")
    public ResponseEntity<Page<CustomerProfileDTO>> getListOfAllCustomers(Pageable pageable) {
        Page<Customer> customerPage = customerServiceImpl.getListOfAllCustomers(pageable);
        return ResponseEntity.ok(customerPage.map(customerMapper::toCustomerProfileDTO));
    }

    @GetMapping(params = "keyword")
    public ResponseEntity<Page<CustomerProfileDTO>> getListOfCustomersByKeyword(@RequestParam String keyword, Pageable pageable) {
        Page<Customer> customerPage = customerServiceImpl.getListOfCustomersByKeyword(keyword, pageable);
        return ResponseEntity.ok(customerPage.map(customerMapper::toCustomerProfileDTO));
    }

    @GetMapping("${limitationsCustomersControllerSubPath}")
    public ResponseEntity<Page<CustomerProfileDTO>> getListOfAllBannedCustomers(Pageable pageable) {
        Page<Customer> customerPage = customerServiceImpl.getListOfAllBannedCustomers(pageable);
        return ResponseEntity.ok(customerPage.map(customerMapper::toCustomerProfileDTO));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerProfileDTO> getCustomer(@PathVariable String customerId) {
        Customer customer = customerServiceImpl.getCustomer(customerId);
        return ResponseEntity.ok(customerMapper.toCustomerProfileDTO(customer));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteCustomer() {
        customerServiceImpl.deleteCustomer();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{customerId}${limitationsCustomersControllerSubPath}")
    public ResponseEntity<Void> setCustomerLimitations(@PathVariable String customerId, @RequestBody boolean limitations) {
        customerServiceImpl.setCustomerLimitations(customerId, limitations);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{customerId}${balanceCustomersControllerSubPath}")
    public ResponseEntity<Void> addBalance(@PathVariable String customerId, @RequestBody double amount) {
        customerServiceImpl.addBalanceToCustomer(customerId, amount);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}${balanceCustomersControllerSubPath}")
    public ResponseEntity<Void> removeBalance(@PathVariable String customerId, @RequestBody double amount) {
        customerServiceImpl.removeBalanceFromCustomer(customerId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("${shippingInfoCustomersControllerSubPath}")
    public ResponseEntity<Void> setShippingInfo(@RequestBody CustomerShippingInfo customerShippingInfo) {
        if (!customerShippingInfo.isComplete())
            throw new InvalidCustomerShippingInfoException();

        customerServiceImpl.setShippingInfo(customerShippingInfo);
        return ResponseEntity.ok().build();
    }
}
