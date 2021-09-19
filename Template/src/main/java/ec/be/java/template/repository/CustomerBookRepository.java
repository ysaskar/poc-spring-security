package ec.be.java.template.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.be.java.template.model.CustomerBook;
import ec.be.java.template.model.CustomerBookId;

@Repository
public interface CustomerBookRepository extends CrudRepository<CustomerBook, CustomerBookId> {
}