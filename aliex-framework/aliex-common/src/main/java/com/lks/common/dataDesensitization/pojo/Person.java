package com.lks.common.dataDesensitization.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lks.common.dataDesensitization.MobileSerializer;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/11/12 21:29
 */
public class Person {



    private String username;

    private String id;

    private Integer age;


    private String email;

    private String address;

    @JsonSerialize(using = MobileSerializer.class)
    private String phoneNumber;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
