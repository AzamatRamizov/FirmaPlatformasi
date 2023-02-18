package com.example.firmaplatformasi.Dto;

import com.example.firmaplatformasi.EnumStep.Huquqlar;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class LavozimDto{
    @NotBlank
    private String nomi;

    @NotEmpty
    private List<Huquqlar> huquqlars;

    @NotNull
    private String izoh;
}
