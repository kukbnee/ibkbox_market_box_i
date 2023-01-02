package com.ibk.sb.restapi.app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class DBConfiguration {

    private final ApplicationContext applicationContext;

    private SqlSessionFactory sqlSessionFactory;

    /** dev Profile DB Config : HikariCP **/

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean("dataSource")
    @Profile({"dev"})
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /** prod, stage Profile DB Config : JNDI **/

    @Getter
    @Setter
    class JndiPropertyHolder {
        private String jndiName;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public JndiPropertyHolder getJndiPropertyHolder() {
        return new JndiPropertyHolder();
    }

    @Bean("dataSource")
    @Profile({"stage", "prod"})
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        String jndiName = getJndiPropertyHolder().getJndiName();
        
        // JndiDataSourceLookup 처리
        return (DataSource) new JndiDataSourceLookup().getDataSource(jndiName);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // mappers 하위 디렉터리 정리가 가능하도록 경로 수정
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/database/mappers/**/*Mapper.xml"));
        factoryBean.setTypeAliasesPackage("com.ibk.sb.restapi.biz");
        factoryBean.setConfiguration(mybatisConfg());

        // Nullable에 대한 null 데이터 삽입 시 전체 jdbcType 처리를 위한 로직
        SqlSessionFactory factory = factoryBean.getObject();
        factory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

        sqlSessionFactory = factory;

        return factory;
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfg() {
        return new org.apache.ibatis.session.Configuration();
    }

    // Tx Aspect 를 사용할 경우
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}