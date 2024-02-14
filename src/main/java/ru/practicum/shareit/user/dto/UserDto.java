package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.user.model.validationGroups.UserOnCreate;
import ru.practicum.shareit.user.model.validationGroups.UserOnUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;
    @NotBlank(groups = UserOnCreate.class)
    private String name;
    @NotBlank(groups = UserOnCreate.class)
    @Email(groups = {UserOnCreate.class, UserOnUpdate.class})
    private String email;
}
