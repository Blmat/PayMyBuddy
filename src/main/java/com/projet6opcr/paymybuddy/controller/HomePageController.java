package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserService userService;

    @GetMapping("/home")
    public ModelAndView getHome() {
        log.info("New request: show the homepage template in the view ");

        UserAccount user = userService.getConnectedUser();
        ModelAndView mav = new ModelAndView("home_page");
        mav.addObject("user", user);

        return mav;
    }
}
