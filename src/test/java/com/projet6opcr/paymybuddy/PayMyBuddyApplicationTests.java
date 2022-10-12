package com.projet6opcr.paymybuddy;

import com.projet6opcr.paymybuddy.controller.MainController;
import com.projet6opcr.paymybuddy.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PayMyBuddyApplicationTests {

    @Autowired
    private MainController controller;
    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(userController).isNotNull();
    }

}
