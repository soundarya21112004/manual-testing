package com.eagle.mas;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.eagle.mas.config.ConstantValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.eagle.mas.repository")
public class DBConfiguration {
	File propertyFile;
//	@Value("${spring.datasource.driver}")
//	private String DRIVER;
//
//	@Value("${spring.datasource.password}")
//	private String PASSWORD;
//
//	@Value("${spring.datasource.url}")
//	private String URL;
//
//	@Value("${spring.datasource.username}")
//	private String USERNAME;

	@Value("${hibernate.dialect}")
	private String DIALECT;

	@Value("${hibernate.show_sql}")
	private String SHOW_SQL;

//	@Value("${hibernate.hbm2ddl.auto}")
//	private String HBM2DDL_AUTO;

	@Value("com")
	private String PACKAGES_TO_SCAN;


	public void OSCheck() {
		String command = "";
		String sOsName = System.getProperty("os.name");

		if (sOsName.startsWith("Windows")) {
			System.out.println("WINDOWS OS");
			String filePath = new FileSystemResource("").getFile().getAbsolutePath();
			propertyFile = new File(filePath+"\\MVS.conf");
			System.out.println("filepath :"+filePath);
//			propertyFile = new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\bin\\PSAIDA.conf");
		} else {

			if ((sOsName.startsWith("Linux")) || (sOsName.startsWith("Mac")) || (sOsName.startsWith("HP-UX"))) {
				System.out.println("LINUX OS");
				File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
				propertyFile = new File(catalinaBase, "bin/MVS.conf");
			} else {
				System.out.println("The current operating system '" + sOsName + "' is not supported.");
			}
		}
	}

	@Bean
	public DataSource dataSource() {
		OSCheck();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		try{
			InputStream in = new FileInputStream(propertyFile);
			ResourceBundle resource = new PropertyResourceBundle(in);
			ConstantValue.KERNELAUTHMANAGER=resource.getString("KERNELAUTHMANAGER");
			ConstantValue.appId=resource.getString("appId");
			ConstantValue.clientId=resource.getString("clientId");
			ConstantValue.secretKey=resource.getString("secretKey");
			ConstantValue.IDENTITY=resource.getString("IDENTITY");

			dataSource.setDriverClassName(resource.getString("db.driver"));
			dataSource.setUrl(resource.getString("db.url"));
			dataSource.setUsername(resource.getString("db.username"));
			dataSource.setPassword(resource.getString("db.password"));
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", DIALECT);
		hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
//		hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
