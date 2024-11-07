package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.persistance.entities.Admin;
import com.ernestocesario.myclothes.persistance.repositories.AdminRepository;
import com.ernestocesario.myclothes.services.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserServiceImpl userServiceImpl;
    private final IsAdmin isAdmin;

    @Override
    @Transactional
    public Page<Admin> getListOfAllAdmins(Pageable pageable) {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return adminRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Admin getMe() {
        AuthorizationChecker.check(isAdmin, userServiceImpl.getCurrentUser());

        return (Admin) userServiceImpl.getCurrentUser();
    }
}
