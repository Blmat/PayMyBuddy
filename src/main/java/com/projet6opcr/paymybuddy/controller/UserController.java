package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;


    @GetMapping("/home")
    public String showHome(Model model) {
        final var user = userService.getConnectedUser();
        model.addAttribute("user", user);
        return "home";
    }

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Optional<UserAccount> currentUser = userRepository.findByEmail(username);
//        Double balance = currentUser.get().getBalance();
//
//        model.addAttribute("balance");
//        return "home";
//    }



//        return "home" +"Bienvenue ~["
//                + getName(authentication)
//                +" ]~ !";
//    }
//
//    private String getName(Authentication authentication) {
//        return Optional.of(authentication)
//                .filter(OAuth2AuthenticationToken.class::isInstance)
//                .map(OAuth2AuthenticationToken.class::cast)
//                .map(OAuth2AuthenticationToken::getPrincipal)
//                .map(DefaultOidcUser.class::cast)
//                .map(StandardClaimAccessor::getEmail)
//                .orElse(authentication.getName());
//    }

    @PostMapping("/addFriend")
    public String addFriend(@RequestParam("friendEmail") String email){
        userService.addFriend(email);
        return "/contact";
    }

}
