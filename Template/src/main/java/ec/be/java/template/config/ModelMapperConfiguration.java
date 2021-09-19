package ec.be.java.template.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ec.be.java.template.dto.BookDto;
import ec.be.java.template.dto.CustomerDto;
import ec.be.java.template.model.Book;
import ec.be.java.template.model.Customer;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE);

		registerModel(modelMapper);

		return modelMapper;
	}

	private void registerModel(ModelMapper modelMapper) {
		modelMapper.typeMap(Customer.class, CustomerDto.class);
		modelMapper.typeMap(CustomerDto.class, Customer.class);
		modelMapper.typeMap(Book.class, BookDto.class);
		modelMapper.typeMap(BookDto.class, Book.class);
	}
}
