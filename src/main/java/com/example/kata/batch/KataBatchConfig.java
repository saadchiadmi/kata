package com.example.kata.batch;

import com.example.kata.service.KataService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@EnableBatchProcessing
public class KataBatchConfig {

    private final String OUTPUT_PATH = "src/main/resources/output/output.txt";
    private final String INPUT_PATH = "src/main/resources/input/input.txt";

    private final KataService kataService;
    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public KataBatchConfig(KataService kataService, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.kataService = kataService;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("kataJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("kata-step")
                .<Integer, String>chunk(1)
                .reader(kataItemReader())
                .processor(kataItemProcessor())
                .writer(kataItemWriter())
                .build();
    }

    @Bean
    public ItemReader<Integer> kataItemReader() {
        return new FlatFileItemReaderBuilder<Integer>()
                .name("kataItemReader")
                .resource(new FileSystemResource(INPUT_PATH))
                .lineMapper(lineMapper())
                .build();
    }

    public LineMapper<Integer> lineMapper() {
        DefaultLineMapper<Integer> lineMapper = new DefaultLineMapper<>();
        LineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> Integer.valueOf(fieldSet.readString(0)));
        return lineMapper;
    }

    @Bean
    public ItemProcessor<Integer, String> kataItemProcessor() {
        return item -> item+" \""+kataService.transform(item)+"\"";
    }

    @Bean
    public ItemWriter<String> kataItemWriter() {
        return new FlatFileItemWriterBuilder<String>()
                .name("kataItemWriter")
                .resource(new FileSystemResource(OUTPUT_PATH))
                .lineAggregator(item -> item)
                .build();
    }

    //Initialise a custom jobRepository to avoid the creation of a dataSource
    @Bean(name = "customJobRepository")
    public JobRepository jobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
