package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.exceptions.InvalidAuthorizationException;
import com.ernestocesario.myclothes.persistance.entities.Admin;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.utils.UserRole;
import com.ernestocesario.myclothes.persistance.repositories.AdminRepository;
import com.ernestocesario.myclothes.services.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public Page<Admin> getListOfAllAdmins(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return adminRepository.findAll(pageable);
    }
}
