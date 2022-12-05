package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BuddyDTO;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

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
    void getBuddyTest() throws Exception {

        UserAccount newUser = new UserAccount();
        newUser.setFirstName("Bob");
        newUser.setLastName("obo");
        newUser.setEmail("test@mail.fr");
        newUser.setBalance(10.1);
        BuddyDTO buddy = new BuddyDTO(newUser);

        mockMvc.perform(get("/buddy").flashAttr("buddies", buddy))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    @WithUserDetails
    void getBalanceTest() throws Exception {
        mockMvc.perform(get("/add_balance"))
                .andDo(print())
                .andExpect((status().isOk()));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "admin")
    public void addBuddyController_OKTest() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setEmail("test@mail.fr");
        // THEN
        mockMvc.perform(post("/buddy").flashAttr("email", newUser.getEmail()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/buddy"));
    }
}