package com.projet6opcr.paymybuddy.integration.controller;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
class BankViewControllerIT {

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
    void getProfile() throws Exception {
        //Given
        final var url = "/profile";

        mockMvc.perform(get(url)
                        .with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    @DisplayName("bank adding test")
    public void bankAddingTest() throws Exception {
        //Given
        final var url = "/profile";
        final var bankName = "qsdgfhj";
        final var iban = "bbbbbbbbbbbb";
        final var bic = "azertuyi";
        BankAccountDTO bank = new BankAccountDTO(bankName, iban, bic);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .flashAttr("bank", bank))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    @DisplayName("bank adding test error")
    public void bankAddingTestKO() throws Exception {
        //Given
        final var url = "/profile";
        final var bankName = "qsdgfhj";
        final var iban = "";
        final var bic = "azergdh";
        BankAccountDTO bank = new BankAccountDTO(bankName, iban, bic);

        mockMvc.perform(post(url)
                        .with(csrf())
                        .flashAttr("bank", bank))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }
}