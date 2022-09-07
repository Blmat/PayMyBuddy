//package IntegrationTest;
//
//import com.projet6opcr.paymybuddy.model.User;
//import com.projet6opcr.paymybuddy.repository.UserRepository;
//import com.projet6opcr.paymybuddy.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.transaction.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@ExtendWith(SpringExtension.class)
//@Transactional
//class UserServiceImplTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    void findExistingEmailTest(){
//        //GIVEN
//        var email = "email@email";
//        var user = new User(1L, null, null, null, email, null, 0.0);
//        userRepository.save(user);
//
//        //WHEN
//        var response = userService.findByEmail(email);
//
//        //THEN
//        assertThat(response).isEqualTo(user);Tsi.
//    }
//    @Test
//    void findNonExistingEmailTest(){
//        //GIVEN
//        var email = "email@email";
//
//
//        //WHEN
//        var response = userService.findByEmail(email);
//
//        //THEN
//        assertThat(response).isEqualTo(null);
//    }
//
//}