package com.example.firmaplatformasi.Service;

import com.example.firmaplatformasi.Dto.TopshiriqDto;
import com.example.firmaplatformasi.Entity.Topshiriq;
import com.example.firmaplatformasi.Entity.Users;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Repository.TopshiriqRepository;
import com.example.firmaplatformasi.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

@Service
public class TopshiriqService {
    @Autowired
    TopshiriqRepository topshiriqRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UsersRepository usersRepository;

    public APIResponse add(TopshiriqDto topshiriqDto) {
        Optional<Users> byIsm = usersRepository.findByIsm(topshiriqDto.getHodimIsmi());
        if(byIsm.isPresent()){
            Users users = byIsm.get();
            Topshiriq topshiriq=new Topshiriq();
            topshiriq.setNomi(topshiriqDto.getNomi());
            topshiriq.setMatni(topshiriqDto.getMatni());
            topshiriq.setDeadline(topshiriqDto.getDeadline());
            topshiriq.setHodim(users);
            if(xabaryuborish(users.getUsername(),topshiriq.getDeadline())){
                topshiriqRepository.save(topshiriq);
                return new APIResponse("Topshiriq yuborildi",true);
            }
            return new APIResponse("Something wrong! Check internet.",false);
        }
        return new APIResponse("Hodim topilmadi",false);
    }

    public boolean xabaryuborish(String email,String muddat){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(email);
            helper.setSubject("Email tasdiqlash");
            helper.setText("Sizga Yangi topshiriq mavjud. Muddat "+muddat+".");
//            helper.setText("<button style=\"width: 100px;height:50px;box-shadow: 0px 0px 20px blueviolet; border-radius:10px; background: rgb(113, 0, 218); border: none; margin: 50px 100px; padding: 15px;\"><a href=\"http://localhost:8080/user/tasdiqlash?email="+email+"&email_kod="+email_kod+"\" style=\"text-decoration: none; color: white; font-size: 16px; text-shadow: 0 0 10px white;\">Tasdiqlash</a></button>",true);
            javaMailSender.send(message);
            return  true;
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }

    public boolean uzgartirishxabari(String email){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(email);
            helper.setSubject("Email tasdiqlash");
            helper.setText("Sizning topshirig'ingizga o'zgartirish kiritildi. Qaytadan tekshiring.");
            javaMailSender.send(message);
            return  true;
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }

    public APIResponse edit(TopshiriqDto topshiriqDto, Integer id) {
        Optional<Topshiriq> byId = topshiriqRepository.findById(id);
        if(byId.isPresent()){
            Topshiriq topshiriq = byId.get();
            boolean holat=false;
            if(topshiriqDto.getNomi()!=null){
                holat=true;
                topshiriq.setNomi(topshiriqDto.getNomi());
            }
            if(topshiriqDto.getMatni()!=null){
                holat=true;
                topshiriq.setMatni(topshiriqDto.getMatni());
            }
            if(topshiriqDto.getDeadline()!=null){
                holat=true;
                topshiriq.setDeadline(topshiriqDto.getDeadline());
            }
            Optional<Users> byIsm = usersRepository.findByIsm(topshiriqDto.getHodimIsmi());
            Users users = byIsm.get();
            if(holat){
                if(uzgartirishxabari(users.getUsername())){
                    topshiriqRepository.save(topshiriq);
                    return new APIResponse("Topshiriq o'zgartirildi",true);
                }
                return new APIResponse("Nimadir xato! Internet aloqasini tekshirib ko'ring.",false);
            }
            return new APIResponse("Nothing updated",false);
        }
        return new APIResponse("Topshiriq topilmadi",false);
    }


    public APIResponse readAll() {
        List<Topshiriq> all = topshiriqRepository.findAll();
        if(!all.isEmpty()){
            return new APIResponse(all.toString(),true);
        }
        return new APIResponse("Empty",false);
    }
}
