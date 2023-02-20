package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BuddyDto;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void getBuddyTest() throws Exception {

        UserAccount newUser = new UserAccount();
        newUser.setFirstName("Bob");
        newUser.setLastName("obo");
        newUser.setEmail("test@mail.fr");
        newUser.setBalance(BigDecimal.valueOf(10.1));
        BuddyDto buddy = new BuddyDto(newUser);

        mockMvc.perform(get("/buddy").flashAttr("buddies", buddy))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    @WithUserDetails
    void getBalanceTest() throws Exception {
        mockMvc.perform(get("/bank_transfer"))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    public void addBuddyController_OK_Test() throws Exception {
        UserAccount newUser = new UserAccount();
        newUser.setFirstName("Bob");
        newUser.setLastName("obo");
        newUser.setPassword("password");
        newUser.setEmail("test@mail.fr");
        newUser.setBalance(BigDecimal.valueOf(10.1));

        userRepository.save(newUser);

        // THEN
        mockMvc.perform(post("/buddy").flashAttr("email", newUser.getEmail()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/buddy"));

    }

    @Test
    @DisplayName("capte l'erreur car l'ami n'est pas dans la base de donnée")
    @WithMockUser(username = "admin@admin.com", password = "admin")
    void addBuddyController_KO_Test() throws Exception {

        // THEN
        mockMvc.perform(post("/buddy").flashAttr("email", "email@email.fr"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}