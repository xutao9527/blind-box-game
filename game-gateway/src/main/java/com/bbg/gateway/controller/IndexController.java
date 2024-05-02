package com.bbg.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class IndexController {

    @RequestMapping("/index")
    public Integer index(){
        return 1;
    }
}
