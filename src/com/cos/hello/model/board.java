package com.cos.hello.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class board {
	private int id;
	private String title;
	private String content;
	private int userId; // fk
}
