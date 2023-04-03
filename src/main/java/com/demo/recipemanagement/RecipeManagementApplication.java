package com.demo.recipemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = { "com.demo.recipemanagement.*" })
public class RecipeManagementApplication {

    public static void main(String[] args) {
	SpringApplication.run(RecipeManagementApplication.class, args);
    }

}