package com.example.firmaplatformasi.Controller;

import com.example.firmaplatformasi.Dto.LoginDto;
import com.example.firmaplatformasi.Dto.UserDto;
import com.example.firmaplatformasi.Entity.Lavozim;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hodim")
public class UsersController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDTO){
        APIResponse apiResponse=userService.login(loginDTO);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody UserDto userDto){
        APIResponse apiResponse=userService.addHodim(userDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@RequestBody UserDto userDto,@PathVariable Integer id){
        APIResponse apiResponse=userService.edit(userDto,id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        APIResponse apiResponse=userService.delete(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @GetMapping("/readOne/{id}")
    public HttpEntity<?> readOne(@PathVariable Integer id){
        APIResponse apiResponse=userService.readOne(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @GetMapping("/readAll")
    public HttpEntity<?> readAll(){
        APIResponse apiResponse=userService.readAll();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }



    @GetMapping("/tasdiqla")
    public HttpEntity<?> tasdiqlash(@RequestParam String email,@RequestParam String email_code){
        APIResponse apiResponse=userService.tasdiqlash(email,email_code);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}
