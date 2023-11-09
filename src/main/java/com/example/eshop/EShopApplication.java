package com.example.eshop;

import com.example.eshop.exception.GlobalEShopException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

@SpringBootApplication(scanBasePackages = {
        "uz.sicnt.mysoliq.security.config",
        "com.example.eshop.*"})
public class EShopApplication {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            SpringApplication.run(EShopApplication.class, args);
            jedis.flushAll();
            System.out.println("Redis cache cleared successfully.");
        }catch (Exception e) {
            throw new GlobalEShopException(e.getMessage());
        }
    }
}
