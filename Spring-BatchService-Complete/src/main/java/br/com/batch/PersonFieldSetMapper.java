package br.com.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {

	@Override
	public Person mapFieldSet(FieldSet fieldSet) throws BindException {

		Person person = new Person();

		person.setFirstName(fieldSet.readString("firstName"));
		person.setLastName(fieldSet.readString("lastName"));

		return person;
	}

}
