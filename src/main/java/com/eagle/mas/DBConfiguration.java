package com.eagle.mas;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.eagle.mas.config.ConstantValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.eagle.mas.repository", entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager")
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

	Logger log = LoggerFactory.getLogger(DBConfiguration.class);

//	@Value("${hibernate.hbm2ddl.auto}")
//	private String HBM2DDL_AUTO;

	@Value("${entitymanager.packagesToScan}")
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
	@Primary
	@Bean(name = "dataSource")
	public DataSource dataSource() throws IOException {
		OSCheck();
		System.out.println("Data Source");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		InputStream in = null;
		try{
			in = new FileInputStream(propertyFile);
			ResourceBundle resource = new PropertyResourceBundle(in);
			ConstantValue.KERNELAUTHMANAGER=resource.getString("KERNELAUTHMANAGER");
			ConstantValue.appId=resource.getString("appId");
			ConstantValue.clientId=resource.getString("clientId");
			ConstantValue.secretKey=resource.getString("secretKey");
			ConstantValue.IDENTITY=resource.getString("IDENTITY");
			ConstantValue.KERNELAUTHMANAGERAPI=resource.getString("KERNELAUTHMANAGERAPI");
			ConstantValue.BIOAPI=resource.getString("BIOAPI");
			ConstantValue.METAINFOAPI=resource.getString("METAINFOAPI");
			ConstantValue.DOCUMENTAPI=resource.getString("DOCUMENTAPI");
			ConstantValue.SEARCHFIELDAPI=resource.getString("SEARCHFIELDAPI");
			ConstantValue.AUDITAPI=resource.getString("AUDITAPI");
			ConstantValue.TokenAppId=resource.getString("TokenAppId");
			ConstantValue.TokenRequestSecretKey=resource.getString("TokenRequestSecretKey");
			ConstantValue.TokenClientId=resource.getString("TokenClientId");
			ConstantValue.elapsedHours=Long.parseLong(resource.getString("case.unassign.time.limit"));
			ConstantValue.MAXRESULT =Integer.parseInt(resource.getString("search.filter.max.result"));
			ConstantValue.corePoolSize = Integer.parseInt(resource.getString("corePoolSize"));
			ConstantValue.maximumPoolSize = Integer.parseInt(resource.getString("maximumPoolSize"));
			ConstantValue.keepAliveTime = Integer.parseInt(resource.getString("keepAliveTime"));
			ConstantValue.connectTimeout = Integer.parseInt(resource.getString("connectTimeout"));
			ConstantValue.socketTimeout = Integer.parseInt(resource.getString("socketTimeout"));
			ConstantValue.executorShutdown = Integer.parseInt(resource.getString("executorShutdown"));
			dataSource.setDriverClassName(resource.getString("db.driver"));
			dataSource.setUrl(resource.getString("db.url"));
			System.out.println("url ---> " + resource.getString("db.url"));
			dataSource.setUsername(resource.getString("db.username"));
			dataSource.setPassword(resource.getString("db.password"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			in.close();
		}
		return dataSource;
	}
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory(@Qualifier("dataSource") DataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", DIALECT);
		hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
//		hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}
	@Primary
	@Bean(name = "transactionManager")
	public HibernateTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalSessionFactoryBean sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory.getObject());
		return transactionManager;
	}

	@PostConstruct
	public void warDetials(){
		log.info("Third Operator Assignment Fix - 11.10.2025");
	}
}
