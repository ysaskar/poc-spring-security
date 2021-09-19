package ec.be.java.template.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerDto {
	private Long id;
	private String firstName;
	private String lastName;
	private List<BookDto> books;
}