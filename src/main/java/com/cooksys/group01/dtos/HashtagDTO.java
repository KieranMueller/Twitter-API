package com.cooksys.group01.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashtagDTO {

	private String label;

	private Timestamp firstUsed;

	private Timestamp lastUsed;
}
