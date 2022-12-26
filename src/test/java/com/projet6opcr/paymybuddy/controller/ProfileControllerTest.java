package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDto;
import com.projet6opcr.paymybuddy.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getProfile() throws Exception {
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void addBankAccount_OK_Test() throws Exception {
        BankAccountDto newBank = new BankAccountDto();
        newBank.setBankName("Labanque");
        newBank.setIban("IbanTest");
        newBank.setBic("BicTest");
        // THEN
        mockMvc.perform(post("/profile").flashAttr("bank", newBank))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void addBankAccount_KO_Test() throws Exception {
        BankAccountDto newBank = new BankAccountDto();
        newBank.setBankName("bankName");
        newBank.setIban(null);
        newBank.setBic("BicTest");
        // THEN
        mockMvc.perform(post("/profile").flashAttr("bank", newBank))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
    }
}