package ru.morevstrech.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.morevstrech.service.dto.ERole;
import ru.morevstrech.service.dto.PageListDto;
import ru.morevstrech.service.entity.User;
import ru.morevstrech.service.entity.UserInfo;
import ru.morevstrech.service.pojo.SignupRequest;
import ru.morevstrech.service.repository.UserInfoRepository;
import ru.morevstrech.service.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
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
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<ERole> roles = new HashSet<>();
        if (signupRequest.isBusiness()) {
            user.setAccount("company");
            roles.add(ERole.ROLE_COMPANY);
        } else {
            user.setAccount("user");
            roles.add(ERole.ROLE_USER);
        }
        user.setRoles(roles);

        int code = (int) (Math.random() * (9999 - 1001)) + 1001;
        user.setValidationCode(code);

        user.setEnabled(false);
        user.setRegistration(LocalDateTime.now());
        user.setLastVisit(LocalDateTime.now());

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(signupRequest.getPhone());
        userInfo.setUser(user);

        userRepository.save(user);
        userInfoRepository.save(userInfo);

        // Важно - Доделать! Отправка кода на номер телефона через СМС

        return user;
    }

    public User update(User userFromDb, User user) {


        return userRepository.save(userFromDb);
    }

    public void activate(User user) {
        user.setValidationCode(0);
        user.setEnabled(true);
        userRepository.save(user);
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

    public boolean existsByPhone(String phone) {
        return userInfoRepository.existsByPhone(phone);
    }

    /**
     * <p>Возвращает список объектов разбиты на страницы, а также дополнительную информацию.</p>
     * @param pageable интерфейс разбиения информации на страницы
     * @return объект PageListDto(объекты на странице, текущая страница, общее число страниц, общее число объектов).
     */
    public PageListDto findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);

        return new PageListDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}