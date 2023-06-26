package com.cooksys.group01.entity.embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
