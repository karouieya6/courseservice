package com.example.courseservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice")
public interface UserClient {

    @GetMapping("/user/email/{email}")
    Long getUserIdByEmail(@PathVariable String email);
}
