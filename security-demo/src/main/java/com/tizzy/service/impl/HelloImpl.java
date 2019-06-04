package com.tizzy.service.impl;

import com.tizzy.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloImpl implements HelloService {

    @Override
    public String print(Object name) {
        System.out.println(name);
        return "验证service调用完成";
    }
}
