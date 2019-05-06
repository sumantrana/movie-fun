package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DatabaseServiceCredentials getDatabaseServiceCredentials(@Value("${vcap.services}") String vcapServices){
        System.out.println("*******   " + vcapServices + "   ***********");
        return new DatabaseServiceCredentials(vcapServices);
    }

    @Bean
    public HibernateJpaVendorAdapter getHibernateJpaVendor(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        hibernateJpaVendorAdapter.setDatabase( Database.MYSQL );

        return hibernateJpaVendorAdapter;
    }
}
