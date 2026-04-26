package com.company.pms.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // ✅ Any authenticated user
    @GetMapping("/api/test")
    public String test() {
        return "✅ Protected API working — you are authenticated!";
    }

    // ✅ ADMIN only
    @GetMapping("/api/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminApi() {
        return "✅ Admin API working — you are ADMIN!";
    }

    // ✅ ADMIN or MANAGER
    @GetMapping("/api/manager/test")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String managerApi() {
        return "✅ Manager API working — you are ADMIN or MANAGER!";
    }

    // ✅ Any role
    @GetMapping("/api/user/test")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public String userApi() {
        return "✅ User API working — you are logged in!";
    }
}
