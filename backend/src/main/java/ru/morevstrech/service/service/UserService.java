package ru.morevstrech.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.morevstrech.service.entity.Role;
import ru.morevstrech.service.entity.User;
import ru.morevstrech.service.pojo.SignupRequest;
import ru.morevstrech.service.repository.UserDetailsRepository;
import ru.morevstrech.service.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    /**
     * <p>Создает новую учетную запись на основе поступивших данных с формы регистрации.</p>
     * @param signupRequest объект регистрационных данных
     * @return новый объект User с заполненными данными после добавления в базу данных.
     */
    public User create(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        user.setEnabled(true);
        user.setRegistration(LocalDateTime.now());
        user.setLastVisit(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User update(User userFromDb, User user) {

        return userRepository.save(userFromDb);
    }

    /**
     * <p>"Удаляет" пользователя, а точнее - меняет статус аккаунта, но сама учетная запсь остается в базе данных.</p>
     * @param user объект пользователя, которого необходимо удалить/отключить
     */
    public void delete(User user) {
        user.setEnabled(false);
        user.setLocked(true);
        userRepository.save(user);
    }

    /**
     * <p>Проверяет, существует ли пользователь с указанным именем пользователя.</p>
     * @param username имя пользователя
     * @return true, если пользователь с таким именем пользователя существует, false - если не существует.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * <p>Проверяет, существует ли пользователь с указанным e-mail.</p>
     * @param email e-mail пользователя
     * @return true, если пользователь с таким e-mail существует, false - если не существует.
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}