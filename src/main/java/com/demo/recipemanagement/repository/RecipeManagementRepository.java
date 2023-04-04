package com.demo.recipemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.recipemanagement.entity.RecipeEntity;

@Repository
public interface RecipeManagementRepository extends JpaRepository<RecipeEntity, Long> {

}