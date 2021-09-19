package ec.be.java.template.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ec.be.java.template.dto.BookDto;
import ec.be.java.template.dto.response.Response;
import ec.be.java.template.dto.response.Response.Status;
import ec.be.java.template.model.Book;
import ec.be.java.template.repository.BookRepository;
import ec.be.java.template.service.BookService;

@Component
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<BookDto> getAllBook() {
		return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
				.map(book -> mapper.map(book, BookDto.class))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public BookDto getBook(Long id) {
		Optional<Book> book = bookRepository.findById(id);

		if (book.isEmpty()) {
			return null;
		}

		return mapper.map(book.get(), BookDto.class);
	}

	@Override
	public Response createBook(BookDto bookDto) {
		Response response = new Response();
		try {
			Book book = mapper.map(bookDto, Book.class);
			bookRepository.save(book);
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setStatus(Status.EXCEPTION).setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateBook(BookDto bookDto) {
		Response response = new Response();
		try {
			Book newData = mapper.map(bookDto, Book.class);
			Optional<Book> oldData = bookRepository.findById(newData.getId());
			if (!oldData.isPresent()) {
				response.setStatus(Status.NOT_FOUND)
						.setErrors(String.format("Book with id '%d' not found", newData.getId()));
			}
			Book book = oldData.get();
			book.setTitle(newData.getTitle());
			book.setIsbn(newData.getIsbn());
			bookRepository.save(book);
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setStatus(Status.EXCEPTION).setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response deleteBook(Long id) {
		Response response = new Response();
		try {
			bookRepository.deleteById(id);
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setStatus(Status.EXCEPTION).setErrors(e.getMessage());
		}
		return response;
	}
}
