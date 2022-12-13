package com.projet6opcr.paymybuddy.integration.controller;

import com.projet6opcr.paymybuddy.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
class ConnectAndSignUpControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    void getRegistration() throws Exception {
        //Given
        final var url = "/registration";

        mockMvc.perform(get(url)
                        .with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Registration OK test")
    public void registrationTest() throws Exception {
        //Given
        final var url = "/registration";
        final var firstname = "Jonh";
        final var lastName = "boyd";
        final var email = "JBoyd@mail.com";
        final var password = "jboyd";
        final var user = new UserDto(firstname, lastName, email, password);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .flashAttr("user", user))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("Registration KO test")
    public void registrationKOTest() throws Exception {
        //Given
        final var url = "/registration";
        final var firstname = "John";
        final var lastName = "wick";
        final var email = "Babayagad@mail.com";
        final var password = "";
        final var user = new UserDto(firstname, lastName, email, password);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .flashAttr("user", user))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());

    }
}