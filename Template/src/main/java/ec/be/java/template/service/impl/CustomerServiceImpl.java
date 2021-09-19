package ec.be.java.template.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ec.be.java.template.dto.BookDto;
import ec.be.java.template.dto.CustomerDto;
import ec.be.java.template.dto.response.Response;
import ec.be.java.template.dto.response.Response.Status;
import ec.be.java.template.model.Customer;
import ec.be.java.template.model.CustomerBook;
import ec.be.java.template.model.CustomerBookId;
import ec.be.java.template.repository.CustomerBookRepository;
import ec.be.java.template.repository.CustomerRepository;
import ec.be.java.template.service.CustomerService;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private CustomerBookRepository customerBookRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<CustomerDto> getAllCustomer() {
		return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
				.map(customer -> mapper.map(customer, CustomerDto.class))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public CustomerDto getCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (customer.isEmpty()) {
			return null;
		}

		CustomerDto customerDto = mapper.map(customer.get(), CustomerDto.class);
		customerDto.setBooks(
				customer.get().getCustomerBook().stream().map(x -> mapper.map(x.getBook(), BookDto.class)).toList());

		return customerDto;
	}

	@Override
	public CustomerDto getCustomerByAnotationQuery(String firstname) {
		// sample by native query anotation
		Customer customer = customerRepository.findCustomerByFirstName("baruu");
		List<BookDto> customerBook = customer.getCustomerBook().stream()
				.map(x -> mapper.map(x.getBook(), BookDto.class)).toList();

		CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
		customerDto.setBooks(customerBook);

		return null;
	}

	@Override
	public CustomerDto getCustomerByNativeQuery(String firstname) {
		// sample by entity manager
		Query query = em.createNativeQuery(
				"select distinct c.id,c.first_name,c.last_name from customer c left join customer_book cb on c.id=cb.customer_id "
						+ "left join book b on cb.book_id=b.id " + "where c.first_name = :firstname ");
		query.setParameter("firstname", "baruu");

		Object[] resultObject = (Object[]) query.getSingleResult();

		CustomerDto customer = new CustomerDto();
		customer.setId(((BigDecimal) resultObject[0]).longValue());
		customer.setFirstName((String) resultObject[1]);
		customer.setLastName((String) resultObject[2]);

		return customer;
	}

	@Override
	@Transactional
	public Response createCustomer(CustomerDto customerDto) {
		Response response = new Response();
		try {
			Customer customer = mapper.map(customerDto, Customer.class);
			customerRepository.save(customer);

			List<CustomerBook> customerBooks = customerDto.getBooks().stream().map(book -> {
				CustomerBookId customerBookId = new CustomerBookId(customer.getId(), book.getId());
				CustomerBook customerBook = new CustomerBook();
				customerBook.setCustomerBookId(customerBookId);
				return customerBook;
			}).toList();
			customerBookRepository.saveAll(customerBooks);

			response.setStatus(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.EXCEPTION).setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateCustomer(CustomerDto customerDto) {
		Response response = new Response();
		try {
			Customer newData = mapper.map(customerDto, Customer.class);
			Optional<Customer> oldData = customerRepository.findById(newData.getId());
			if (!oldData.isPresent()) {
				response.setStatus(Status.NOT_FOUND)
						.setErrors(String.format("Customer with id '%d' not found", newData.getId()));
			}
			Customer customer = oldData.get();
			customer.setFirstName(newData.getFirstName());
			customer.setLastName(newData.getLastName());
			customerRepository.save(customer);
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setStatus(Status.EXCEPTION).setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response deleteCustomer(Long id) {
		Response response = new Response();
		try {
			customerRepository.deleteById(id);
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setStatus(Status.EXCEPTION).setErrors(e.getMessage());
		}
		return response;
	}

}
