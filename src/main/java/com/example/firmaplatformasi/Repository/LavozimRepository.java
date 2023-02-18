package com.example.firmaplatformasi.Repository;

import com.example.firmaplatformasi.Entity.Lavozim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LavozimRepository extends JpaRepository<Lavozim,Integer> {
    Optional<Lavozim> findByLavozimNomi(String lavozimNomi);
}
