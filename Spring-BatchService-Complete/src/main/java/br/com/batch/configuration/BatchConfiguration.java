package br.com.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.batch.JobCompletionListener;
import br.com.batch.Person;
import br.com.batch.PersonItemProcessor;
import br.com.batch.PersonItemReader;
import br.com.batch.PersonItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

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
	ItemReader<Person> reader() {
		return new PersonItemReader<Person>();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Person, Person>chunk(1)
				.reader(reader())
				.processor(new PersonItemProcessor())
				.writer(new PersonItemWriter())
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}
}