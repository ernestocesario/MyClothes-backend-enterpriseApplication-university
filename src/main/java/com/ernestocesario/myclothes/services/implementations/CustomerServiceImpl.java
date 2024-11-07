package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsCustomer;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.customer.CustomerOwnCustomerOrIsAdmin;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.exceptions.customer.InsufficientCustomerBalanceException;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.customer.InvalidCustomerShippingInfoException;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;
import com.ernestocesario.myclothes.persistance.repositories.ChatRepository;
import com.ernestocesario.myclothes.persistance.repositories.CustomerRepository;
import com.ernestocesario.myclothes.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ChatRepository chatRepository;
    private final CustomerRepository customerRepository;
    private final UserServiceImpl userServiceImpl;
    private final IsAdmin isAdmin;
    private final CustomerOwnCustomerOrIsAdmin customerOwnCustomerOrIsAdmin;
    private final IsCustomer isCustomer;

    @Override
    @Transactional
    public Page<Customer> getListOfAllCustomers(Pageable pageable) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return customerRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Page<Customer> getListOfCustomersByKeyword(String keyword, Pageable pageable) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return customerRepository.findAllByUsernameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    @Transactional
    public Page<Customer> getListOfAllBannedCustomers(Pageable pageable) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return customerRepository.findAllByBannedIsTrue(pageable);
    }

    @Override
    @Transactional
    public Customer getMe() {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        return (Customer) userServiceImpl.getCurrentUser();
    }

    @Override
    @Transactional
    public Customer getCustomer(String customerId) {
        AuthorizationChecker.check(customerOwnCustomerOrIsAdmin, userServiceImpl.getCurrentUser(), customerId);

        return customerRepository.findById(customerId).orElseThrow(InternalServerErrorException::new);
    }

    @Override
    @Transactional
    public boolean setCustomerLimitations(String customerId, boolean isBanned) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new InternalServerErrorException();

        customer.setBanned(isBanned);
        customerRepository.save(customer);

        return true;
    }

    @Override
    @Transactional
    public boolean addBalanceToCustomer(String customerId, double amount) {
        AuthorizationChecker.check(customerOwnCustomerOrIsAdmin, userServiceImpl.getCurrentUser(), customerId);

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new InternalServerErrorException();

        if (amount <= 0)
            throw new InvalidInputException();

        modifyCustomerBalance(customer, amount);
        customerRepository.save(customer);

        return true;
    }

    @Override
    @Transactional
    public boolean removeBalanceFromCustomer(String customerId, double amount) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new InternalServerErrorException();

        if (amount <= 0)
            throw new InvalidInputException();

        modifyCustomerBalance(customer, -amount);
        customerRepository.save(customer);

        return true;
    }

    @Override
    @Transactional
    public boolean setShippingInfo(CustomerShippingInfo customerShippingInfo) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();

        customer.setShippingInfo(customerShippingInfo);
        customerRepository.save(customer);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteCustomer() {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        User user = userServiceImpl.getCurrentUser();
        if (!user.isCustomer())
            throw new InternalServerErrorException();

        Customer customer = (Customer) user;
        customerRepository.delete(customer);

        return true;
    }



    //helper methods
    @Override
    @Transactional
    public boolean hasActiveChat(Customer customer) {  //only system
        return chatRepository.existsChatByActiveIsTrueAndCustomer(customer);
    }



    //Private methods
    private void modifyCustomerBalance(Customer customer, double amount) {
        double newCustomerBalance = customer.getBalance() + amount;
        if (newCustomerBalance < 0)
            throw new InsufficientCustomerBalanceException();
        customer.setBalance(newCustomerBalance);
    }
}
