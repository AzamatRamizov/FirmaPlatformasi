package com.example.firmaplatformasi.Controller;

import com.example.firmaplatformasi.Dto.SectionDto;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Repository.SectionRepository;
import com.example.firmaplatformasi.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    SectionService sectionService;

    @PreAuthorize(value = "hasAuthority('ADDSECTION')")
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody SectionDto sectionDto){
        APIResponse apiResponse=sectionService.addSection(sectionDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITSECTION')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody SectionDto sectionDto){
        APIResponse apiResponse=sectionService.edit(id,sectionDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETESECTION')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        APIResponse apiResponse=sectionService.delete(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READSECTION')")
    @GetMapping("/readOne/{id}")
    public HttpEntity<?> readOne(@PathVariable Integer id){
        APIResponse apiResponse=sectionService.readOne(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READSECTION')")
    @GetMapping("/readAll")
    public HttpEntity<?> readALl(){
        APIResponse apiResponse=sectionService.readAll();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}
