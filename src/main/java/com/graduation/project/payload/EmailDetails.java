package com.graduation.project.payload;

import java.util.Map;

import lombok.Data;

@Data
public class EmailDetails {

	String to;
	String from;
	String subject;
	String text;
	String template;
	Map<String, Object> properties;
}
