package br.com.batch.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import br.com.batch.entity.Person;

public class PersonItemWriter implements ItemWriter<Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonItemWriter.class);

	@Override
	public void write(List<? extends Person> arg0) throws Exception {
    	log.info("BATCH JOB WRITER");
	}

}
