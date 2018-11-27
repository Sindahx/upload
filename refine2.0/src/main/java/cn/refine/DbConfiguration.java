package cn.refine;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

//@Configuration
public class DbConfiguration implements TransactionManagementConfigurer {  
    
  @Autowired  
  private DataSourceProperties properties;  
    
  @Bean
  private DataSource dataSource() {
      DataSourceBuilder factory = DataSourceBuilder  
              .create(this.properties.getClassLoader())  
              .driverClassName(this.properties.getDriverClassName())  
              .url(this.properties.getUrl()).username(this.properties.getUsername())  
              .password(this.properties.getPassword());  
//      if (this.properties.getType() != null) {  
//          factory.type(this.properties.getType());  
//      }  
      return factory.build();  
  }  

  @Bean  
  public PlatformTransactionManager txManager() {  
      return new DataSourceTransactionManager(dataSource());  
  }  

  @Override  
  public PlatformTransactionManager annotationDrivenTransactionManager() {  
      return txManager();  
  }  

}  