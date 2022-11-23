package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;


    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getBalanceTest() throws Exception {
        mockMvc.perform(get("/add_balance"))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getBuddytest() throws Exception {
        mockMvc.perform(get("/buddy"))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    public void addUserController_OKTest() throws Exception {

        // THEN
        mockMvc.perform(formLogin("/buddy")
                        .user("mat@email.com"))
                .andExpect((status().is2xxSuccessful()))
                .andExpect(view().name("/buddy"));
    }
//    @Test
//    @WithMockUser(username = "admin@admin.com", password = "admin")
//    public void addUserController_KOTest() throws Exception {
//
//        // THEN
//        mockMvc.perform(formLogin("/buddy")
//                        .user(null))
//                .andExpect((status().isOk()))
//                .andExpect(view().name("/buddy"));
//    }
}