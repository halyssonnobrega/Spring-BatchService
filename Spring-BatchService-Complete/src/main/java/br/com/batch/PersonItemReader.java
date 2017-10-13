package br.com.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.core.io.ClassPathResource;

public class PersonItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> {
    private static final Logger log = LoggerFactory.getLogger(PersonItemReader.class);

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    	log.info("BATCH JOB READER");
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(lineMapper());
		return (T) reader;
	}

	@Override
	protected T doRead() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doOpen() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doClose() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public LineMapper<Person> lineMapper() {
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(";");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[]{"firstName", "lastName"});

		BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<Person>();
		fieldSetMapper.setTargetType(Person.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(personFieldSetMapper());

		return lineMapper;
	}

	public PersonFieldSetMapper personFieldSetMapper() {
		return new PersonFieldSetMapper();
	}
}
