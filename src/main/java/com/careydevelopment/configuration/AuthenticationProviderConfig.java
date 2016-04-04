package com.careydevelopment.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@Configuration
public class AuthenticationProviderConfig {
	   private static final String PROPS_FILE = "/etc/tomcat8/resources/mysql.properties";
		
	   @Bean(name = "dataSource")
	   public DriverManagerDataSource dataSource() {
		   DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		   
		   try {
			 Properties props = new Properties();
			 File file = new File(PROPS_FILE);
			 FileInputStream inStream = new FileInputStream(file);
			 props.load(inStream);
			 
		     driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		     driverManagerDataSource.setUrl(props.getProperty("db.url"));
		     driverManagerDataSource.setUsername(props.getProperty("db.username"));
		     driverManagerDataSource.setPassword(props.getProperty("db.password"));
		   } catch (Exception e) {
			   e.printStackTrace();
			   throw new RuntimeException ("Can't get the data source!");
		   }
		   
		   return driverManagerDataSource;
	   }

    
    @Bean(name="userDetailsService")
    public UserDetailsService userDetailsService(){
     JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
     jdbcImpl.setDataSource(dataSource());
     jdbcImpl.setUsersByUsernameQuery("select username,password, enabled from users where username=?");
     jdbcImpl.setAuthoritiesByUsernameQuery("select username, role from user_roles where username=?");
     return jdbcImpl;
    }
}