package com.example.controller;

import com.example.Repository.AccountRepository;
import com.example.entity.Account;
import com.example.entity.resp.RestBean;
import io.swagger.annotations.Api;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Api(tags = "用户信息",description = "所有的用户信息包括更新，查询等")
@RestController
@RequestMapping("/api/user")
public class AccountAuthController {


    @Resource
    AccountRepository repository;

    @RequestMapping("/info")
    public RestBean<Account> info(HttpSession session) {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = repository.findAccountByUsername(context.getAuthentication().getName());
        account.setPassword("");
        return new RestBean<>(200, "请求成功", account);
    }
}
