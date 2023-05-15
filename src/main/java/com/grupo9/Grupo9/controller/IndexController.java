
package com.grupo9.Grupo9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping("/")
    public String vista(Model model){    
        return "index.html";
    }
}
