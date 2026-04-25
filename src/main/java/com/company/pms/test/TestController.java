package com.company.pms.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        return "Protected API working";
    }

    @GetMapping("/api/admin/test")
    public String adminApi() {
        return "Admin API working";
    }

    @GetMapping("/api/user/test")
    public String userApi() {
        return "User API working";
    }
}
