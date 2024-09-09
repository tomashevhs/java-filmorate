package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"email", "id", "name", "birthday", "login"})
public class User {

    private Long id;

    @NotBlank(message = "Имейл не должен быть пустым")
    @Email(message = "Имейл должен содержать симвом @")
    private String email;

    @NotBlank(message = "Логин не должен быть пустым")
    private String login;

    private String name;

    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();
}
