package ec.be.java.template.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.be.java.template.dto.BookDto;
import ec.be.java.template.dto.response.Response;
import ec.be.java.template.service.BookService;

@RestController
@RequestMapping("book")
@RolesAllowed(value = {"Admin","User"})
//@PreAuthorize("hasAnyAuthority('Admin','User')")
public class BookController {

	@Autowired(required = true)
	BookService service;

	@GetMapping("/")
	public Response getAllUser() {
		return Response.ok().setPayload(service.getAllBook());
	}

	@GetMapping("/{id}")
	public Response getBook(@PathVariable("id") Long id) {
		BookDto book = service.getBook(id);
		if (book == null) {
			return Response.notFound().setErrors(String.format("Book with id '%d' not found", id));
		}
		return Response.ok().setPayload(book);
	}

	@PostMapping("/")
	public Response createBook(@RequestBody BookDto book) {
		return service.createBook(book);
	}

	@PutMapping("/")
	public Response updateBook(@RequestBody BookDto book) {
		return service.updateBook(book);
	}

	@DeleteMapping("/{id}")
	public Response updateBook(@PathVariable("id") Long id) {
		return service.deleteBook(id);
	}
}