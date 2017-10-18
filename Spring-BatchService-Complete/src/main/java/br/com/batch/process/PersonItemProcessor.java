package br.com.batch.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.com.batch.entity.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    private Person transformedPerson;
    
	@Override
    public Person process(final Person person) throws Exception {
    	log.info("BATCH JOB PROCESS");

        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        
        if (firstName.equals("JUSTIN")) {
        	transformedPerson = new Person(firstName, lastName);			
        	log.info("Converting (" + person + ") into (" + transformedPerson + ")");        	
		} else {
			transformedPerson = null;
		}

        return transformedPerson;
    }
}
