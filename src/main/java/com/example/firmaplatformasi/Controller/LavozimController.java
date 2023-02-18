package com.example.firmaplatformasi.Controller;

import com.example.firmaplatformasi.Dto.LavozimDto;
import com.example.firmaplatformasi.Entity.Lavozim;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Repository.LavozimRepository;
import com.example.firmaplatformasi.Service.LavozimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lavozim")
public class LavozimController {

    @Autowired
    LavozimService lavozimService;

    @PreAuthorize(value = "hasAuthority('ADDLAVOZIM')")
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody LavozimDto lavozimDto){
        APIResponse apiResponse=lavozimService.addLavozim(lavozimDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITLAVOZIM')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@RequestBody LavozimDto lavozimDto,@PathVariable Integer id){
        APIResponse apiResponse=lavozimService.editLavozim(lavozimDto,id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETELAVOZIM')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> del(@PathVariable Integer id){
        APIResponse apiResponse=lavozimService.delLavozim(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READLAVOZIM')")
    @GetMapping("/read/{id}")
    public HttpEntity<?> read(@PathVariable Integer id){
        APIResponse apiResponse=lavozimService.readLavozim(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READLAVOZIM')")
    @GetMapping("/readAll")
    public HttpEntity<?> readAll(){
        APIResponse apiResponse=lavozimService.readAll();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}
