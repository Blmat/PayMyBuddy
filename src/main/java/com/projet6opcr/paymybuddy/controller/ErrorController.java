package com.projet6opcr.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletResponse response) {

        Map<String, Object> model = new HashMap<>();
        model.put("code", response.getStatus());

        return new ModelAndView("error", model);
    }
}
