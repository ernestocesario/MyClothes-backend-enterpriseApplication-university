package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.UserMapper;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users.UserProfileDTO;
import com.ernestocesario.myclothes.persistance.entities.Admin;
import com.ernestocesario.myclothes.services.implementations.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${adminsControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;
    private final UserMapper userMapper;

    @GetMapping("")
    public ResponseEntity<Page<UserProfileDTO>> getAdmins(Pageable pageable) {
        Page<Admin> adminPage = adminServiceImpl.getListOfAllAdmins(pageable);
        Page<UserProfileDTO> userProfileDTOPage = adminPage.map(userMapper::toUserProfileDTO);

        return ResponseEntity.ok(userProfileDTOPage);
    }

    @GetMapping("${selfInfoAdminsControllerSubPath}")
    public ResponseEntity<UserProfileDTO> getMe() {
        Admin admin = adminServiceImpl.getMe();
        return ResponseEntity.ok(userMapper.toUserProfileDTO(admin));
    }
}
