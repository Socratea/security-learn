package com.tizzy.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tizzy.dto.User.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping
    public User create(@Valid @RequestBody User user){
    /*public User create(@Valid @RequestBody User user, BindingResult errors){//如果加了BindingResult，框架就不会返回HTTP错误，需要自己设定
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
        }*/
        user.setId(1);
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, @PathVariable Integer id){
        user.setId(id);
        return user;
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@PageableDefault(page = 0, size = 10, sort = {"username"}, direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(pageable);
        return Arrays.asList(
                new User(1, "tom", "1232221", 21, new Date()),
                new User(2, "tom2", "1223213", 22, new Date()),
                new User(3, "tom3", "1222433", 30, new Date())
        );
    }

    @GetMapping(value = "/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id){
        return new User(1, "tom", "1232221", 21, new Date());
    }
}
