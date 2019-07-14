package org.zerock.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages= {"org.zerock.service"})
@ComponentScan(basePackages="org.zerock.aop")
@ComponentScan(basePackages="org.zerock.task")

@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement

@MapperScan(basePackages= {"org.zerock.mapper"})
public class RootConfig {
	@Bean
	public DataSource dataSource() {
		HikariConfig hk = new HikariConfig();
		hk.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		hk.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		hk.setUsername("system");
		hk.setPassword("1234");
		hk.setMinimumIdle(5);
		// test Query
		hk.setConnectionTestQuery("SELECT sysdate FROM dual");
		hk.setPoolName("springHikariCP");
		hk.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		hk.addDataSourceProperty("dataSource.prepStmtCacheSize", "200");
		hk.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
		hk.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		HikariDataSource ds = new HikariDataSource(hk);
		return ds;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
}