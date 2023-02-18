package com.example.firmaplatformasi.Service;

import com.example.firmaplatformasi.Dto.LoginDto;
import com.example.firmaplatformasi.Dto.UserDto;
import com.example.firmaplatformasi.Entity.Lavozim;
import com.example.firmaplatformasi.Entity.Section;
import com.example.firmaplatformasi.Entity.Users;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Repository.LavozimRepository;
import com.example.firmaplatformasi.Repository.SectionRepository;
import com.example.firmaplatformasi.Repository.UsersRepository;
import com.example.firmaplatformasi.Token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LavozimRepository lavozimRepository;

//    public APIResponse register(UserDto userDto) {
//        boolean isemaill = usersRepository.existsByUsername(userDto.getEmail());
//        if(!isemaill){
//            Users users=new Users();
//
//            users.setIsm(userDto.getIsm());
//            users.setFamiliya(userDto.getFamiliya());
//            users.setEmail(userDto.getEmail());
//            users.setPassword(passwordEncoder.encode(userDto.getPassword()));
////            users.setRoles(roleRepositary.findByRoleName(RolesEnum.HODIM));
//            users.setEmailpassword(UUID.randomUUID().toString().substring(0,5));
//            hodimRepository.save(users);
//            if (xabaryuborish(users.getEmail(),users.getEmailpassword())){
//                return new APIResponse("Foydalanuvchi malumoti qo'shildi",true);
//            }
//
////            userRepository.save(userDto)
//        }
//        return new APIResponse("Foydalanuvchi malumoti qo'shilmadi",false);
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> byEmail = usersRepository.findByUsername(username);
        if (byEmail.isPresent()){
            return (UserDetails) byEmail.get();
        }
        return (UserDetails) new UsernameNotFoundException("Topilmadi");
    }

//    public APIResponse emailtasdiqlash(String email, String email_kod) {
//        Optional<Users> byEmailAndEmailpassword = hodimRepository.findByEmailAndEmailpassword(email, email_kod);
//        if (byEmailAndEmailpassword.isPresent()){
//            Users users = byEmailAndEmailpassword.get();
//            users.setEmailpassword(null);
//            users.setEnabled(true);
//            hodimRepository.save(users);
//            return new APIResponse("Welcome to our platform!",true);
//        }
//        return new APIResponse("This Profile is already exists.",false);
//    }

    public APIResponse login(LoginDto loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            Users user = (Users) authenticate.getPrincipal();
            String token = tokenGenerator.token(user.getUsername(), user.getLavozim());
            return new APIResponse("Platformaga kirildi: "+token,true);
        }
        catch (BadCredentialsException badCredentialsException){
            return new APIResponse("Login yoki parol xato",false);
        }
    }

    public APIResponse addhodim(com.example.firmaplatformasi.Dto.UserDto userDto) {
        if(userDto.getPassword().equals(userDto.getRepassword())){
            Users users=new Users();
            users.setIsm(userDto.getIsmi());
            users.setFamiliya(userDto.getFamiliyasi());
            users.setTelefon(userDto.getTelefon());
            users.setUsername(userDto.getUsername());
            users.setPassword(passwordEncoder.encode(userDto.getPassword()));
            users.setEmailpassword(UUID.randomUUID().toString().substring(0,5));
            if(xabaryuborish(users.getUsername(), users.getEmailpassword())){
                return new APIResponse("Emailga tasdiqlash kodi yuborildi.",true);
            }
            usersRepository.save(users);

        }
        return new APIResponse("Parollar mos emas",false);
    }
    public boolean xabaryuborish(String email,String email_kod){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(email);
            helper.setSubject("Email tasdiqlash");
            helper.setText("<button style=\"width: 100px;height:50px;box-shadow: 0px 0px 20px blueviolet; border-radius:10px; background: rgb(113, 0, 218); border: none; margin: 50px 100px; padding: 15px;\"><a href=\"http://localhost:8080/hodim/tasdiqla?email="+email+"&email_code="+email_kod+"\" style=\"text-decoration: none; color: white; font-size: 16px; text-shadow: 0 0 10px white;\">Tasdiqlash</a></button>",true);
            javaMailSender.send(message);
            return  true;
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }

    public APIResponse addHodim(UserDto userDto) {
        Optional<Users> byUsername = usersRepository.findByUsername(userDto.getUsername());
        Optional<Section> byNomi = sectionRepository.findByNomi(userDto.getBulimNomi());
        if(!byNomi.isPresent()){
            return new APIResponse("Bo'lim mavjud emas!",false);
        }
        Optional<Lavozim> byLavozimNomi = lavozimRepository.findByLavozimNomi(userDto.getLavozimi());
        if(!byLavozimNomi.isPresent()){
            return new APIResponse("Lavozim not found",false);
        }
        if(userDto.getPassword().equals(userDto.getRepassword())){
            if(!byUsername.isPresent()){
                Users users=new Users();
                users.setIsm(userDto.getIsmi());
                users.setFamiliya(userDto.getFamiliyasi());
                users.setTelefon(userDto.getTelefon());
                users.setUsername(userDto.getUsername());
                users.setSection(byNomi.get());
                users.setLavozim(byLavozimNomi.get());
                users.setPassword(passwordEncoder.encode(userDto.getPassword()));
                users.setEmailpassword(UUID.randomUUID().toString().substring(0,5));
                if(xabaryuborish(users.getUsername(), users.getEmailpassword())){
                    usersRepository.save(users);
                    return new APIResponse("Emailga tasdiqlash kodi yuborildi.",true);
                }
            }
            return new APIResponse("Username already exists",false);
        }
        return new APIResponse("Parollar mos emas",false);
    }

    public APIResponse tasdiqlash(String email, String email_code) {

        Optional<Users> byUsernameAndEmailpassword = usersRepository.findByUsernameAndEmailpassword(email, email_code);
        if(byUsernameAndEmailpassword.isPresent()){
            Users users = byUsernameAndEmailpassword.get();
            users.setEnabled(true);
            users.setEmailpassword(null);
            usersRepository.save(users);
            return new APIResponse("Profile activated",true);
        }
        return new APIResponse("Profile already activated",false);
    }

    public APIResponse edit(UserDto userDto, Integer id) {
        Optional<Users> byId = usersRepository.findById(id);
        if(byId.isPresent()){
            Users users = byId.get();
            if(userDto.getIsmi()!=null){
                users.setIsm(userDto.getIsmi());
            }
            if(userDto.getFamiliyasi()!=null){
                users.setFamiliya(userDto.getFamiliyasi());
            }
            if(userDto.getTelefon()!=null){
                users.setTelefon(userDto.getTelefon());
            }
            if(userDto.getBulimNomi()!=null){
                Optional<Section> byNomi = sectionRepository.findByNomi(userDto.getBulimNomi());
                if(!byNomi.isPresent()){
                    return new APIResponse("Section not found",false);
                }
                users.setSection(byNomi.get());
            }
            if(userDto.getLavozimi()!=null){
                Optional<Lavozim> byLavozimNomi = lavozimRepository.findByLavozimNomi(userDto.getLavozimi());
                if(!byLavozimNomi.isPresent()){
                    return new APIResponse("Lavozim mavjud emas",false);
                }
                users.setLavozim(byLavozimNomi.get());
            }
            if(userDto.getPassword()!=null){
                users.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            usersRepository.save(users);
            return new APIResponse("Hodim edited",true);
        }
        return new APIResponse("Hodim not found",false);
    }

    public APIResponse delete(Integer id) {
        Optional<Users> byId = usersRepository.findById(id);
        if(byId.isPresent()){
            usersRepository.deleteById(id);
            return new APIResponse("Hodim deleted",true);
        }
        return new APIResponse("Hodim not found",false);
    }

    public APIResponse readOne(Integer id) {
        Optional<Users> byId = usersRepository.findById(id);
        if(byId.isPresent()){
            Users users = byId.get();
            return new APIResponse(users.toString(),true);
        }
        return new APIResponse("Hodim not found",false);
    }

    public APIResponse readAll() {
        List<Users> all = usersRepository.findAll();
        return new APIResponse(all.toString(),true);
    }
}
