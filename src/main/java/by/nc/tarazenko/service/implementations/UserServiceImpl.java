package by.nc.tarazenko.service.implementations;

import by.nc.tarazenko.entity.User;
import by.nc.tarazenko.repository.UserRepository;
import by.nc.tarazenko.service.UserService;
import by.nc.tarazenko.service.exceptions.UserAlreadyExistException;
import by.nc.tarazenko.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("There is no user with such id."));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        if(userRepository.findByUserName(user.getUsername()).isPresent()){
            throw new UserAlreadyExistException("User with such id already exist.");
        }
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User update(User user) {
        userRepository.findById(user.getId()).orElseThrow(()->
                new UserNotFoundException("There is no user with such id."));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("There is no user with such id."));
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = userRepository.findByUserName(userName).get();
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }
}
