package com.url.shortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequestDTO {

    @NotBlank(message = "URL must not be blank")
    @URL(message = "Invalid URL format")
    private String url;
}


