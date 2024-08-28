package com.ernestocesario.myclothes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${reviewsControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReviewController {

}
