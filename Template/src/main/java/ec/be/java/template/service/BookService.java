package ec.be.java.template.service;

import java.util.List;

import ec.be.java.template.dto.BookDto;
import ec.be.java.template.dto.response.Response;


public interface BookService {

	List<BookDto> getAllBook();

	BookDto getBook(Long id);

	Response createBook(BookDto BookDto);

	Response updateBook(BookDto BookDto);

	Response deleteBook(Long id);
}
