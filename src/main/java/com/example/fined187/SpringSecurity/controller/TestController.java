package com.example.fined187.SpringSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/defaultSuccessUrl")                           //  login 성공 시 출력.
    String defaultSuccessUrl() {
        return "/defaultSuccessUrl";
    }

    @GetMapping("/loginPage")                               //  localhost:8080/loginPage 입력시 loginPage 출력
    String loginPage() {
        return "/loginPage";
    }

    @GetMapping("/")                                        //  localhost:8080 입력 시 Test
    String printTest() {
        return "Test";
    }
}
