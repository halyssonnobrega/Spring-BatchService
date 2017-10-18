package br.com.batch.process;

import java.util.List;

import org.assertj.core.util.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;

import br.com.batch.entity.Person;

public class PersonItemWriter implements ItemWriter<Person> {
	private static final Logger log = LoggerFactory.getLogger(PersonItemWriter.class);

	private FlatFileItemWriter<Person> writer;
	private FileSystemResource resource;

	public PersonItemWriter() {
		this.writer = new FlatFileItemWriter<>();
		this.resource = new FileSystemResource("target/output-teste.txt");
	}
	
	@BeforeWrite
	private void deleteFile() {
		Files.delete(resource.getFile());
	}

	@Override
	public void write(List<? extends Person> items) throws Exception {
		log.info("BATCH JOB WRITER");

		this.writer.setResource(new FileSystemResource(resource.getFile()));
		this.writer.setLineAggregator(new PassThroughLineAggregator<>());
		this.writer.afterPropertiesSet();
		this.writer.open(new ExecutionContext());
		this.writer.write(items);
	}

	@AfterWrite
	private void close() {
		this.writer.close();
	}

}
