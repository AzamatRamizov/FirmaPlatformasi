package com.example.firmaplatformasi.AutoLoader;

import com.example.firmaplatformasi.Entity.Lavozim;
import com.example.firmaplatformasi.Entity.Users;
import com.example.firmaplatformasi.EnumStep.Huquqlar;
import com.example.firmaplatformasi.Repository.LavozimRepository;
import com.example.firmaplatformasi.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//import static com.example.firmaplatformasi.Entity.Enums.Huquqlar.*;

import static com.example.firmaplatformasi.EnumStep.Huquqlar.*;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Value(value = "${spring.sql.init.mode}")
    String firstLoad;

    @Autowired
    LavozimRepository lavozimRepository;

    @Override
    public void run(String... args) throws Exception {
        if(firstLoad.equals("always")){
            Huquqlar[] values=Huquqlar.values();
            Lavozim direktor = lavozimRepository.save(new Lavozim(
                    LavozimConst.DIREKTOR,
                    Arrays.asList(values),
                    "Direktor"
            ));
            usersRepository.save(new Users(
                    "Azamat","Ramizov","+998911938981","direktor@gmail.com", passwordEncoder.encode("0000"),direktor,true
            ));
        }
    }
}
