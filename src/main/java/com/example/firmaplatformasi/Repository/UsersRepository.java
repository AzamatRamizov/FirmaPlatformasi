package com.example.firmaplatformasi.Repository;

import com.example.firmaplatformasi.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {
//    boolean findByUsername(@Email String username);
    Optional<Users> findByIsm(String ism);
    Optional<Users> findByUsername(@Email String username);
    boolean existsByUsername(@Email String username);

    Optional<Users> findByUsernameAndEmailpassword(@Email String username, String emailpassword);
}
