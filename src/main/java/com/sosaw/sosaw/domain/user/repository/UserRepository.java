package com.sosaw.sosaw.domain.user.repository;

import com.sosaw.sosaw.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(String socialId);
    boolean existsByLoginId(String loginId); // 일반로그인 아이디 중복 확인용
}