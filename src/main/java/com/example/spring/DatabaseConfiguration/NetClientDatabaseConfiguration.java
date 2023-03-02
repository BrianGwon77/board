package com.example.spring.DatabaseConfiguration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(value="com.example.spring.Mapper.netclient", sqlSessionFactoryRef="netclientSqlSessionFactory")
public class NetClientDatabaseConfiguration {

    @Bean(name="netclientDataSource")
    @ConfigurationProperties(prefix="spring.netclient-datasource")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="netclientSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("netclientDataSource") DataSource groupwareDataSource) throws Exception{
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(groupwareDataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath:Mappers/netclient/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean(name="netclientSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("netclientSqlSessionFactory") SqlSessionFactory netclientSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(netclientSqlSessionFactory);
    }

}
