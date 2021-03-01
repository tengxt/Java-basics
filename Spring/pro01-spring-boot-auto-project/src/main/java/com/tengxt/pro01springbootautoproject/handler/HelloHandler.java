package com.tengxt.pro01springbootautoproject.handler;

import com.tengxt.pro01springbootautoproject.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloHandler {

    @Autowired
    private Person person;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/yaml")
    public Map testYAML() {
        Map<String, Object> map = new HashMap<>();
        map.put("person", person);
        return map;
    }
}
