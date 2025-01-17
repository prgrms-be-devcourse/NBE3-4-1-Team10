package dev4._team.cafemenu._team.user.repository;

import dev4._team.cafemenu._team.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

<<<<<<< HEAD
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findOrderListById(Long id);
=======
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
>>>>>>> user
}
