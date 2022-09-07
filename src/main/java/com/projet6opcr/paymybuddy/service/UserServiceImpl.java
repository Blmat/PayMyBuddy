package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(UserDTO userDTO) {

        User user = new User(null,null,userDTO.getFirstname(),
                userDTO.getLastname(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), (double) 0,null);

      return  userRepository.save(user);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Sorry, this user doesn't exist")));
    }

    @Override
    public void addFriend(String friendEmail) { //Todo faire cette methode
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Optional<User> currentUser = userRepository.findByEmail(username);
//
//        Set<User> contacts = currentUser.get().getFriends();
//
//        Optional<User> friend = userRepository.findByEmail(friendEmail);
//        contacts.add(friend.orElseThrow(()->new UserNotFoundException("Sorry, this user doesn't exist")));
//        currentUser.setFriends(contacts);
//        userRepository.save(currentUser);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
         userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }


    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        return new HashSet<GrantedAuthority>();
    }


}
