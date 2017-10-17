package br.com.batch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import br.com.batch.entity.Person;
import br.com.batch.process.JobCompletionListener;
import br.com.batch.process.PersonFieldSetMapper;
import br.com.batch.process.PersonItemProcessor;
import br.com.batch.process.PersonItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job(Step step1) throws Exception {
		return jobBuilderFactory.get("job1")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Person, Person>chunk(10)
				.reader(reader())
				.processor(new PersonItemProcessor())
				.writer(new PersonItemWriter())
				.build();
	}
	
	@Bean
	ItemReader<Person> reader() {
    	log.info("BATCH JOB READER");
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(lineMapper());
		return reader;
	}
	
	public LineMapper<Person> lineMapper() {
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
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

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}
}