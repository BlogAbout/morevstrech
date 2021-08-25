package ru.morevstrech.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.morevstrech.service.entity.User;
import ru.morevstrech.service.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<User> findByPhone(String phone);
}