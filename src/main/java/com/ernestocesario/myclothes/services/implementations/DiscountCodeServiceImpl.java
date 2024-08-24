package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import com.ernestocesario.myclothes.persistance.entities.User;
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

@Service
@RequiredArgsConstructor
public class DiscountCodeServiceImpl implements DiscountCodeService {
    private final UserServiceImpl userServiceImpl;
    private final DiscountCodeRepository discountCodeRepository;
    private final CustomerRepository customerRepository;

    private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('0', '9').get();

    @Override
    @Transactional
    public Page<DiscountCode> getDiscountCodesOfCustomer(Pageable pageable) {
        User user = userServiceImpl.getCurrentUser();
        if (!user.isCustomer())
            throw new InternalServerErrorException();

        Sort sort = Sort.by(Sort.Direction.DESC, "discountPercentage");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Customer customer = (Customer) user;
        return discountCodeRepository.findAllByCustomer(customer, pageable);
    }

    @Override
    @Transactional
    public boolean addDiscountCodeToCustomer(int discountCodePercentage, String customerEmail) {  //only admin and system
        if (discountCodePercentage < 0 || discountCodePercentage > 100)
            throw new InvalidInputException();

        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null)
            throw new InvalidInputException();

        DiscountCode discountCode = new DiscountCode();
        discountCode.setCustomer(customer);
        discountCode.setDiscountPercentage(discountCodePercentage);
        discountCode.setCode(generateValidDiscountCode());
        discountCodeRepository.save(discountCode);

        return true;
    }

    @Override
    @Transactional
    public boolean removeDiscountCodeById(String discountCodeId) {  //only admin and system
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
