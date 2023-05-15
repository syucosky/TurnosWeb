
package com.grupo9.Grupo9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping("index")
    public String vista(){    
        return "index.html";
    }
}
