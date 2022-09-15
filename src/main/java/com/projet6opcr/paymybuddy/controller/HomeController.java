//package com.projet6opcr.paymybuddy.controller;
//
//import com.projet6opcr.paymybuddy.dto.UserDTO;
//import com.projet6opcr.paymybuddy.model.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//
//@Controller
//public class HomeController {
//
//
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
//
//    @GetMapping("/user/create")
//    public String getUser( Model model) {
//
//
//        return "user";
//    }
//
//    @PostMapping("/user/create")
//    public String addUser(UserDTO user, BindingResult result, Model model ){
//
//        if(result.hasErrors()){
//            model.addAttribute("errors", "error");
//            return "user";
//        }
//
//        User user = userService.addUser(user);
//
//        return "redirect:/home";
//
//
//
//    }
//
//}
