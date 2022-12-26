package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.TransactionDto;
import com.projet6opcr.paymybuddy.repository.TransactionRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import com.projet6opcr.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class BankTransactionControllerTest {

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
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getTransfert() throws Exception {
        mvc.perform(get("/transfer"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getContact() throws Exception {
        mvc.perform(get("/contact"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getBankTransfer() throws Exception {
        mvc.perform(get("/bank_transfer"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void bankTransfer_Ok_Test() throws Exception {
        //Given
        final var url = "/bankTransfer";
        final var BankName = "NOMDEBANQUE2";
        final var transferType = "debit";
        final var amount = BigDecimal.valueOf(13.4);

        mvc.perform(post("/bankTransfer")
                        .flashAttr("BankName", BankName)
                        .flashAttr("transferType", transferType)
                        .flashAttr("amount", amount))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bank_transfer"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void bankTransfer_KO_Test() throws Exception {
        //Given
        final var url = "/bankTransfer";
        final var BankName = "mauvaisNomDeBanque";
        final var transferType = "credit";
        final var amount = BigDecimal.valueOf(-1);

        mvc.perform(post(url)
                        .flashAttr("BankName", BankName)
                        .flashAttr("transferType", transferType)
                        .flashAttr("amount", amount))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/bank_transfer"));
    }
}