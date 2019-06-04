package com.tizzy.dto.User;

import com.fasterxml.jackson.annotation.JsonView;
import com.tizzy.validator.MyConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

public class User {
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    private Integer id;
    @MyConstraint(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private int age;
    @Past(message = "生日必须是一个过去的日期")
    private Date birthday;

    public User() {
    }

    public User(Integer id, String username, String password, int age, Date birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.birthday = birthday;
    }

    @JsonView(UserDetailView.class)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
