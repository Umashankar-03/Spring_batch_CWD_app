package com.batch.example.config;

import com.batch.example.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // job bean

    @Bean
    public Job jobBean(
        JobRepository jobRepository,
        JobCompletionNotificationImpl listener,
        Step steps
    ){
        return  new JobBuilder("jobBean",jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

// Steps

    @Bean
    public Step steps(
            JobRepository jobRepository,
            DataSourceTransactionManager transactionManager,
            FlatFileItemReader<Product> itemReader,
            ItemProcessor<Product,Product> itemProcessor,
            ItemWriter<Product> itemWriter
    ){
        return new StepBuilder("JobSteps", jobRepository)
                .<Product, Product>chunk(5, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    // ItemReader

    @Bean
    public FlatFileItemReader<Product> itemReader(){
        return  new FlatFileItemReaderBuilder<Product>()
                .name("itemReader")
                .resource(new ClassPathResource("data.csv"))
                .linesToSkip(1)
                .delimited()
                .names("productId","title","description","price","discount")
                .targetType(Product.class)
                .build();
    }

    // ItemProcessor
    @Bean
    public ItemProcessor<Product,Product>itemProcessor(){
        return new CustomItemProcessor();
    }

    // itemWriter

    @Bean
    public ItemWriter<Product> itemWriter(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("insert into products(product_id,title,description,price,discount,discounted_price)values(:productId, :title, :description, :price, :discount, :discountedPrice)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }



}
