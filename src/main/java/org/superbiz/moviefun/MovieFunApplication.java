package org.superbiz.moviefun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MovieFunApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieFunApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean getRegistrationBean( ActionServlet actionServlet ){

        return new ServletRegistrationBean<ActionServlet>(actionServlet, "/moviefun/*");
    }

}
