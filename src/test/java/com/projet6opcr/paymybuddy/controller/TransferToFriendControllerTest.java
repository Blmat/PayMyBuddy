package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.TransactionDto;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

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
class TransferToFriendControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

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
    void transfer_to_friend_OK_test() throws Exception {


        UserAccount friend = new UserAccount();
        friend.setFirstName("Bob");
        friend.setLastName("obo");
        friend.setPassword("password");
        friend.setEmail("friend@mail.fr");
        friend.setBalance(BigDecimal.valueOf(100.56));
        userRepository.save(friend); //enregistrement d'un ami dans la base de donnée

        final var url = "/transfer";
        final var creditorEmail = "friend@mail.fr";
        final var amount = BigDecimal.valueOf(15.20);
        final var reason = "Remboursement restau"; // initialisation des données pour le test


        mvc.perform(post(url)
                        .flashAttr("transation", new TransactionDto(creditorEmail, amount, reason)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/transfer"));

    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void transfer_to_friend_KO_test() throws Exception {

        final var url = "/transfer";
        final var creditorEmail = "user@email.com";
        final var amount = BigDecimal.valueOf(13.4);


        mvc.perform(post(url)
                        .flashAttr("creditorEmail", creditorEmail)
                        .flashAttr("amount", amount))
                .andDo(print())
                .andExpect(status().isOk());
    }
}