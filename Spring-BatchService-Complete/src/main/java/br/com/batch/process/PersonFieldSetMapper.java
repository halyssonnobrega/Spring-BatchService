package br.com.batch.process;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import br.com.batch.entity.Person;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {

	@Override
	public Person mapFieldSet(FieldSet fieldSet) throws BindException {

		Person person = new Person();

		person.setFirstName(fieldSet.readString("firstName"));
		person.setLastName(fieldSet.readString("lastName"));

		return person;
	}

}
