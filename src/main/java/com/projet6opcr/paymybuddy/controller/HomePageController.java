package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserService userService;

    @GetMapping("/home")//todo donner les sious
    public String getHome(Model model) {

//        UserAccount user = userService.getConnectedUser();
//        UpdateProfileDTO updateProfileDTO = new UpdateProfileDTO(user.getFirstName(), user.getLastName());
//        model.addAttribute("nameInfos", updateProfileDTO);
//        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("password", "password");
//        model.addAttribute("updatePasswordDTO", updatePasswordDTO);
//
//      userService.addMoney();
        return "home";
    }
}
