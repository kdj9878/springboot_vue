package com.xxx.boot.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

//classpath의 경로는 src/main/mresources를 뜻함
@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
    

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    //ConfigurationProperties : *.properties, *.yml 파일에 있는 property를 자바 클래스에 값을 가져와서 사용 할 수 있게 해주는 어노테이션
    //prefix(접두어) 설정을 통해 spring.datasource.hikari로 시작하는 설정을 이용해서 히카리CP의 설정파일을 만든다.
    public HikariConfig hikariConfig(){
        return new HikariConfig();
    }

    //앞에서 만든 히카리CP의 설정 파일을 이용해서 데이터베이스와 연결하는 데이터 소스를 생성한다.
    //여기서는 데이터 소스가 정상적으로 생성되었는지 확인하기 위해서 데이터 소스를 출력한다.
    @Bean
    public DataSource datasource() throws Exception{
        DataSource dataSource = new HikariDataSource(hikariConfig());
        System.out.println("datasource : " + dataSource.toString());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
