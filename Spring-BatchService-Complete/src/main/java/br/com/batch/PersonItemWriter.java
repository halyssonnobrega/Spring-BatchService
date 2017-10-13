package br.com.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class PersonItemWriter implements ItemWriter<Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonItemWriter.class);

	@Override
	public void write(List<? extends Person> arg0) throws Exception {
    	log.info("BATCH JOB WRITER");
	}

}
