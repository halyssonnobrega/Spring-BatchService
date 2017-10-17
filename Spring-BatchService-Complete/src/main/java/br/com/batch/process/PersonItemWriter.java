package br.com.batch.process;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import br.com.batch.entity.Person;

public class PersonItemWriter implements ItemWriter<Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonItemWriter.class);

    private PrintWriter writer;
    
    @SuppressWarnings("resource")
	public PersonItemWriter() {
        OutputStream out;
        try {
            out = new FileOutputStream("output.txt");
        } catch (FileNotFoundException e) {
            out = System.out;
        }
        this.writer = new PrintWriter(out);
    }
    
	@Override
	public void write(List<? extends Person> items) throws Exception {
    	log.info("BATCH JOB WRITER");
    	
    	for (Person item : items) {
            this.writer.println(item.toString());
        }

	}

    @PreDestroy
    private void close() {
		this.writer.close();
	}
    
}
