package com.wendu;


import com.wendu.entily.IdWorker;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * 整体启动
 * @author LittleDumpling
 */
@EnableAutoConfiguration
@SpringBootApplication
@MapperScan(basePackages={"com.wendu.dao"})
public class Initiallizer {
    public static void main(String[] args) {
        SpringApplication.run(Initiallizer.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
