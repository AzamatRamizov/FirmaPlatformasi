package com.example.firmaplatformasi.Repository;

import com.example.firmaplatformasi.Entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section,Integer> {
    Optional<Section> findByNomi(String nomi);
}
