package ec.be.java.template.service;

import java.util.List;

import ec.be.java.template.dto.CustomerDto;
import ec.be.java.template.dto.response.Response;

public interface CustomerService {

	List<CustomerDto> getAllCustomer();

	CustomerDto getCustomer(Long id);

	Response createCustomer(CustomerDto customerDto);

	Response updateCustomer(CustomerDto customerDto);

	Response deleteCustomer(Long id);

    CustomerDto getCustomerByAnotationQuery(String firstname);

	CustomerDto getCustomerByNativeQuery(String firstname);
}
