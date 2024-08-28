package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsCustomer;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import com.ernestocesario.myclothes.persistance.repositories.CustomerRepository;
import com.ernestocesario.myclothes.persistance.repositories.DiscountCodeRepository;
import com.ernestocesario.myclothes.services.interfaces.DiscountCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountCodeServiceImpl implements DiscountCodeService {
    private final UserServiceImpl userServiceImpl;
    private final DiscountCodeRepository discountCodeRepository;
    private final CustomerRepository customerRepository;

    private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('0', '9').get();
    private final IsCustomer isCustomer;
    private final IsAdmin isAdmin;

    @Override
    @Transactional
    public List<DiscountCode> getMyDiscountCodes() {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();
        return discountCodeRepository.findAllByCustomer(customer);
    }

    @Override
    @Transactional
    public boolean addDiscountCodeToCustomer(int discountCodePercentage, String customerEmail, boolean isSystem) {  //only admin and system
        if(!isSystem)
            AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        if (discountCodePercentage < 0 || discountCodePercentage > 100)
            throw new InvalidInputException();

        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow(InvalidInputException::new);

        DiscountCode discountCode = new DiscountCode();
        discountCode.setCustomer(customer);
        discountCode.setDiscountPercentage(discountCodePercentage);
        discountCode.setCode(generateValidDiscountCode());
        discountCodeRepository.save(discountCode);

        return true;
    }

    @Override
    @Transactional
    public boolean removeDiscountCodeById(String discountCodeId, boolean isSystem) {  //only admin and system
        if(!isSystem)
            AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        DiscountCode discountCode = discountCodeRepository.findById(discountCodeId).orElse(null);
        if (discountCode == null)
            throw new InternalServerErrorException();

        discountCodeRepository.delete(discountCode);
        return true;
    }



    //private methods
    private String generateValidDiscountCode() {
        String code;
        do {
            code = randomStringGenerator.generate(6);
        } while (discountCodeRepository.existsByCode(code));

        return code;
    }
}
