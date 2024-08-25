package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Page<Customer> getListOfAllCustomers(Pageable pageable);
    Page<Customer> getListOfCustomersByKeyword(String keyword, Pageable pageable);
    Page<Customer> getListOfAllBannedCustomers(Pageable pageable);
    Customer getCustomer(String id);

    boolean setCustomerLimitations(String customerId, boolean isBanned);
    boolean addBalanceToCustomer(String customerId, double amount);
    boolean removeBalanceFromCustomer(String customerId, double amount);
    boolean setShippingInfo(CustomerShippingInfo customerShippingInfo);
    boolean deleteCustomer();


    //helper methods
    boolean hasActiveChat(Customer customer);
}
