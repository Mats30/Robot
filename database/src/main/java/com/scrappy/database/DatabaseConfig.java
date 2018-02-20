package com.scrappy.database;

import com.scrappy.database.repositories.BooksRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {BooksRepository.class})
public class DatabaseConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter adapter, DataSource source) {
        LocalContainerEntityManagerFactoryBean entityFactory = new LocalContainerEntityManagerFactoryBean();
        entityFactory.setPersistenceUnitName("spring-jpa-pu");
        Map<String, String> properties = new HashMap<>();
        //na razie drop i create
        properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        entityFactory.setJpaPropertyMap(properties);
        entityFactory.setPackagesToScan("com.scrappy.database");
        entityFactory.setJpaVendorAdapter(adapter);
        entityFactory.setDataSource(source);
        return entityFactory;
    }

    @Bean
    public DataSource createDataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setUrl("jdbc:postgresql://localhost/test");
        source.setUsername("criss");
        source.setPassword("wapim32");
        source.setDriverClassName("org.postgresql.Driver");
        //ile chcemy miec polaczen w puli dostepnych?
        source.setInitialSize(10);
        return source;
    }

    @Bean
    public JpaVendorAdapter createAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        //ustawia DDL
        adapter.setGenerateDdl(true);
        //pozwala na logowanie tego jakie kwerendy ida do bazy
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
