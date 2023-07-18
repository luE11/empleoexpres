package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.repository.UserRepository;

import java.util.Optional;

/**
 * @author luE11 on 17/07/23
 */
@AllArgsConstructor
@Service
public class AuthService {
    private UserRepository userRepository;

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
