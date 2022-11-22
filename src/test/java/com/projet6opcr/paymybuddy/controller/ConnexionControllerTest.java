package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ConnexionControllerTest {

    @Autowired
    private MockMvc mvc;

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
    void userAuthenticatedTest() throws Exception {
        mvc.perform(formLogin("/login")
                        .user("mat@email.com").password("motdepasse"))
                .andExpect(unauthenticated());
    }

    @Test
    void newUserTest() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("john");
        newUser.setLastName("wick");
        newUser.setEmail("test@mail.fr");
        newUser.setPassword("sQn2?7.kTrgkQZF");

        mvc.perform(post("/registration").flashAttr("newUser", newUser))
//                        .user("firstname", newUser.getFirstName())
//                        .user("lastName", newUser.getLastName())
//                        .user("email", newUser.getEmail())
//                        .user("password", newUser.getPassword()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("/login"));
    }

}