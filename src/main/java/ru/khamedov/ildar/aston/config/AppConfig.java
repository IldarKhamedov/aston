package ru.khamedov.ildar.aston.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.khamedov.ildar.aston.filter.TransactionFilter;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TransactionFilter transactionFilter(){
        return new TransactionFilter();
    }
}
