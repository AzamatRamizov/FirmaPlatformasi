package com.example.firmaplatformasi.Service;

import com.example.firmaplatformasi.Dto.SectionDto;
import com.example.firmaplatformasi.Entity.Section;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;
    public APIResponse addSection(SectionDto sectionDto) {
        Optional<Section> byNomi = sectionRepository.findByNomi(sectionDto.getNomi());
        if(!byNomi.isPresent()){
            Section section=new Section();
            section.setNomi(sectionDto.getNomi());
            sectionRepository.save(section);
            return new APIResponse("Section was saved",true);
        }
        return new APIResponse("Section already exist",false);
    }

    public APIResponse edit(Integer id, SectionDto sectionDto) {
        Optional<Section> byId = sectionRepository.findById(id);
        if(byId.isPresent()){
            Section section = byId.get();
            if(sectionDto.getNomi()!=null){
                section.setNomi(sectionDto.getNomi());
            }
            sectionRepository.save(section);
            return new APIResponse("Section update succesfully",true);
        }
        return new APIResponse("Section not found",false);
    }

    public APIResponse delete(Integer id) {
        Optional<Section> byId = sectionRepository.findById(id);
        if(byId.isPresent()){
            sectionRepository.deleteById(id);
            return new APIResponse("Section deleted",true);
        }
        return new APIResponse("Section not found",false);
    }

    public APIResponse readOne(Integer id) {
        Optional<Section> byId = sectionRepository.findById(id);
        if(byId.isPresent()){
            Section section = byId.get();
            return new APIResponse(section.toString(),true);
        }
        return new APIResponse("Section not found",false);
    }

    public APIResponse readAll() {
        List<Section> all = sectionRepository.findAll();
        if(!all.isEmpty()){
            return new APIResponse(all.toString(),true);
        }
        return new APIResponse("Sections not found",false);
    }
}
