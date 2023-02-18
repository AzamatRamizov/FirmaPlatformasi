package com.example.firmaplatformasi.Service;

import com.example.firmaplatformasi.Dto.LavozimDto;
import com.example.firmaplatformasi.Entity.Lavozim;
import com.example.firmaplatformasi.Payload.APIResponse;
import com.example.firmaplatformasi.Repository.LavozimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LavozimService {
    @Autowired
    LavozimRepository lavozimRepository;
    public APIResponse addLavozim(LavozimDto lavozimDto) {
        Optional<Lavozim> byLavozimNomi = lavozimRepository.findByLavozimNomi(lavozimDto.getNomi());
        if(!byLavozimNomi.isPresent()){
            Lavozim lavozim=new Lavozim();
            lavozim.setLavozimNomi(lavozimDto.getNomi());
            lavozim.setHuquqlars(lavozimDto.getHuquqlars());
            lavozim.setIzoh(lavozimDto.getIzoh());
            lavozimRepository.save(lavozim);
            return new APIResponse("Lavozim muvaffaqqiyatli qo'shildi",true);
        }
        return new APIResponse("Bunday lavozim allaqachon mavjud",false);
    }

    public APIResponse editLavozim(LavozimDto lavozimDto, Integer id) {
        Optional<Lavozim> byId = lavozimRepository.findById(id);
        if(byId.isPresent()){
            Lavozim lavozim = byId.get();
            if(lavozimDto.getNomi()!=null){
                lavozim.setLavozimNomi(lavozimDto.getNomi());
            }
            if(!lavozimDto.getHuquqlars().isEmpty()){
                lavozim.setHuquqlars(lavozimDto.getHuquqlars());
            }
            if(lavozimDto.getIzoh()!=null){
                lavozim.setIzoh(lavozimDto.getIzoh());
            }
            lavozimRepository.save(lavozim);
            return new APIResponse("Lavozim was edited",true);
        }
        return new APIResponse("Lavozim not found",false);
    }

    public APIResponse delLavozim(Integer id) {
        Optional<Lavozim> byId = lavozimRepository.findById(id);
        if(byId.isPresent()){
            lavozimRepository.deleteById(id);
            return new APIResponse("Lavozim was deleted",true);
        }
        return new APIResponse("Lavozim not found",false);
    }

    public APIResponse readLavozim(Integer id) {
        Optional<Lavozim> byId = lavozimRepository.findById(id);
        if (byId.isPresent()){
            Lavozim lavozim = byId.get();
            return new APIResponse(lavozim.toString(),true);
        }
        return new APIResponse("lavozim not found",false);
    }

    public APIResponse readAll() {
        List<Lavozim> all = lavozimRepository.findAll();
        if (!all.isEmpty()){
            return new APIResponse(all.toString(),true);
        }
        return new APIResponse("Lavozim hali mavjud emas",false);
    }
}
