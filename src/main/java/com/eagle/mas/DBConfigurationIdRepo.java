package com.eagle.mas;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.eagle.mas.idrepo.repo", entityManagerFactoryRef = "idrepoEntityManagerFactory",
        transactionManagerRef = "idrepoTransactionManager")
public class DBConfigurationIdRepo {
    File propertyFile;


    @Value("${hibernate.dialect}")
    private String DIALECT;

    @Value("${hibernate.show_sql}")
    private String SHOW_SQL;



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


    @Bean(name = "idrepoDataSource")
    public DataSource dataSource() {
       OSCheck();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        try {
            InputStream in = new FileInputStream(propertyFile);
            ResourceBundle resource = new PropertyResourceBundle(in);

            dataSource.setDriverClassName(resource.getString("db2.driver"));
            dataSource.setUrl(resource.getString("db2.url"));
            dataSource.setUsername(resource.getString("db2.username"));
            dataSource.setPassword(resource.getString("db2.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
//    @Qualifier("idrepoDataSource")
    @Bean(name = "idrepoEntityManagerFactory")
    public LocalSessionFactoryBean sessionFactory(@Qualifier("idrepoDataSource") DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.eagle.mas.idrepo.model");
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", DIALECT);
        hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
//		hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }
//    @Qualifier("idrepoEntityManagerFactory")
    @Bean(name = "idrepoTransactionManager")
    public HibernateTransactionManager transactionManager(@Qualifier("idrepoEntityManagerFactory") LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());
        return transactionManager;
    }
}
