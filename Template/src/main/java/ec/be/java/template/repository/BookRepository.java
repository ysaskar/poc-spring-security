package ec.be.java.template.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.be.java.template.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	
}