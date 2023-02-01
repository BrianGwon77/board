package com.example.spring.DatabaseConfiguration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(value="com.example.spring.Mapper.erp", sqlSessionFactoryRef="erpSqlSessionFactory")
public class ErpDatabaseConfiguration {

    @Primary
    @Bean(name="erpDataSource")
    @ConfigurationProperties(prefix="spring.erp-datasource")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name="erpSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("erpDataSource") DataSource erpDataSource) throws Exception{

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(erpDataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath:Mappers/erp/*.xml"));
        return sessionFactory.getObject();

    }

    @Primary
    @Bean(name="erpSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("erpSqlSessionFactory") SqlSessionFactory erpSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(erpSqlSessionFactory);
    }

}
