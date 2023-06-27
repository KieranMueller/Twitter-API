package com.cooksys.group01.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileReqDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
