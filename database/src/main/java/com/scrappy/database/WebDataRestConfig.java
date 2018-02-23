package com.scrappy.database;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * It provides webdata configuration.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.scrappy.database")
public class WebDataRestConfig extends RepositoryRestMvcConfiguration {
    public WebDataRestConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
        super(context, conversionService);
    }
}
