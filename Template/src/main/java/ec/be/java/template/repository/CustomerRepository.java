package ec.be.java.template.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.be.java.template.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	@Query(value = "select c.*  from customer c "
			+ "left join customer_book cb on c.id=cb.customer_id "
			+ "left join book b on cb.book_id=b.id "
			+ "where c.first_name = ?1 "
			+ " and rownum= 1 ",
			nativeQuery = true)
	Customer findCustomerByFirstName(String id);
}