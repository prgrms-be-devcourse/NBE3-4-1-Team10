package dev4._team.cafemenu._team.user.repository;

import dev4._team.cafemenu._team.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
