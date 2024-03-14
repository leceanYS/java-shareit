package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Builder
public class ItemRequestDtoReceived {

    @NotBlank
    @Size(min = 0, max = 255)
    private String description;


    public Long getUserId() {
        return null;
    }
}
