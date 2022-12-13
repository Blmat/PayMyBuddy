package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ConnectAndSignUpControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void returnLoginPage() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getHome() throws Exception {
        mvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getLogin() throws Exception {
        mvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getDisconnected() throws Exception {
        mvc.perform(get("/disconnect"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getRegistration() throws Exception {
        mvc.perform(get("/registration"))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    void userLoginFailed() throws Exception {
        mvc.perform(formLogin("/login")
                        .user("mat@mail.fr").password("motdepasse"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void userLoginPassed() throws Exception {
        mvc.perform(formLogin("/login")
                        .user("admin@admin.com")
                        .password("admin"))
                .andExpect(authenticated());
    }

    @Test
    void newUserTest() throws Exception {
        UserDto newUser = new UserDto();
        newUser.setFirstName("john");
        newUser.setLastName("wick");
        newUser.setEmail("test@mail.fr");
        newUser.setPassword("sQn2?7.kTrgkQZF");

        mvc.perform(formLogin("/registration")
                        .user(newUser.getFirstName())
                        .user(newUser.getLastName())
                        .user(newUser.getEmail())
                        .password(newUser.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void newUserAddTest() throws Exception {
        UserDto newUser = new UserDto();
        newUser.setFirstName("john");
        newUser.setLastName("wick");
        newUser.setEmail("test@mail.fr");
        newUser.setPassword("sQn2?7.kTrgkQZF");

        mvc.perform(post("/registration").flashAttr("user", newUser))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }
}