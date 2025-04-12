package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController//表示提供rest的api的controler
public class TextController {
    @GetMapping("/hello")
    public List<String> hello(){
        return List.of("hello"," world");
    }
}
