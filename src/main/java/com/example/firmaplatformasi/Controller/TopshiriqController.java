package com.example.firmaplatformasi.Controller;

import com.example.firmaplatformasi.Dto.TopshiriqDto;
import com.example.firmaplatformasi.Entity.Topshiriq;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Service.TopshiriqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topshiriq")
public class TopshiriqController {

    @Autowired
    TopshiriqService topshiriqService;
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody TopshiriqDto topshiriqDto){
        APIResponse apiResponse=topshiriqService.add(topshiriqDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody TopshiriqDto topshiriqDto){
        APIResponse apiResponse=topshiriqService.edit(topshiriqDto,id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @GetMapping("/readAll")
    public HttpEntity<?> read(){
        APIResponse apiResponse=topshiriqService.readAll();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
//    @GetMapping("/readMyTopshiriq")
//    public HttpEntity<?> readMyTask(){
//        APIResponse apiResponse=topshiriqService.readMyTask();
//    }
}
