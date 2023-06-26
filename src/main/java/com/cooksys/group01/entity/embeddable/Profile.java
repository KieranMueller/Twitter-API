package com.cooksys.group01.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
