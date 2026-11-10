package com.bootcamp19.helloworld.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharAnalysisRequest {

    @NotBlank(message = "Kalimat wajib di isi")
    @Size(max = 255, message = "Kalimat wajib isi sampai 255 character")
    private String kalimat;

}
