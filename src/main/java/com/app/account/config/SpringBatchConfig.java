package com.app.account.config;

import com.app.account.models.Account;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    //account alimentation batch
    @Autowired
    private ItemReader<Account> itemReader;
    @Autowired
    private ItemWriter<Account> itemWriter;
    @Autowired
    private ItemProcessor<Account,Account> itemProcessor;


    @Bean
    public Job clientCreationJob(){
        Step step1=stepBuilderFactory.get("step-load-data")
                .<Account,Account>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("account-alimentation-job").start(step1).build();
    }

}
