package ec.be.java.template.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.be.java.template.dto.CustomerDto;
import ec.be.java.template.dto.response.Response;
import ec.be.java.template.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired(required = true)
	CustomerService service;

	@GetMapping("/")
	public Response getAllUser(Authentication authentication) {
		return Response.ok().setPayload(service.getAllCustomer());
	}

	@GetMapping("/{id}")
	public Response getCustomer(@PathVariable("id") Long id) {
		CustomerDto customer = service.getCustomer(id);
		if (customer == null) {
			return Response.notFound().setErrors(String.format("Customer with id '%d' not found", id));
		}
		return Response.ok().setPayload(customer);
	}

	@PostMapping("/")
	public Response createCustomer(@RequestBody CustomerDto customer) {
		return service.createCustomer(customer);
	}

	@PutMapping("/")
	public Response updateCustomer(@RequestBody CustomerDto customer) {
		return service.updateCustomer(customer);
	}

	@DeleteMapping("/{id}")
	public Response updateCustomer(@PathVariable("id") Long id) {
		return service.deleteCustomer(id);
	}
	
	
	@GetMapping("/anotation")
	public Response getCustomerByAnotation(@RequestParam("firstname") String firstname) {
		CustomerDto customer = service.getCustomerByAnotationQuery(firstname);
		if (customer == null) {
			return Response.notFound().setErrors(String.format("Customer with firsname '%s' not found", firstname));
		}
		return Response.ok().setPayload(customer);
	}
	
	@GetMapping("/entitymanager")
	public Response getCustomerByEntityManager(@RequestParam("firstname") String firstname) {
		CustomerDto customer = service.getCustomerByNativeQuery(firstname);
		if (customer == null) {
			return Response.notFound().setErrors(String.format("Customer with firsname '%s' not found", firstname));
		}
		return Response.ok().setPayload(customer);
	}
	
	@GetMapping("/check-admin")
	@RolesAllowed(value = {"Admin"})
	public Response checkAuthAdmin(Authentication authentication) {
		return Response.ok().setPayload(authentication);
	}
	
	@GetMapping("/check")
	@RolesAllowed(value = {"Admin","User"})
	public Response checkAuth(Authentication authentication) {
		return Response.ok().setPayload(authentication);
	}
	
}