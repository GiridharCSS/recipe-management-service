# Recipe Management Service

## Introduction

This is a RESTful API built with Java Spring Boot that allows users to create, read, update, and delete recipes. The API also includes endpoints for retrieving a list of all recipes, retrieving a single recipe by its ID, and searching for recipes by name.

Spring Security has been implemented in this API to ensure that only authenticated users are able to perform the CRUD operations such as creating, updating, and deleting recipes. When an unauthenticated user attempts to access these endpoints, they will receive a 401 Unauthorized response.

To use this API, users must obtain an access token aginst the valid credentials. This token must be included in the Authorization header of all requests that require authentication. The API will then verify the validity of the token before allowing the request to proceed. 

## Technologies/Frameworks Used

- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database
- Maven

## Requirements

- JDK 11 or later
- Maven 3.6.0 or later

## Installation

1. Clone the repository: `git clone https://github.com/GiridharCSS/recipe-management-service.git`
2. Navigate to the project directory: `cd recipe-management-service`
3. Run the application: `mvn spring-boot:run`

## API Documentation

### Base URL

The base URL for all API requests is `http://localhost:8080/recipe-management-service`.

### API Endpoints

|HTTP Method|URL|Description|
|--------------|----------------|---|
|`POST`|http://localhost:8080/recipe-management-service/recipes | Create a new recipe |
|`PUT`|http://localhost:8080/recipe-management-service/recipes/{id} | Update specific recipe by id |
|`GET`|http://localhost:8080/recipe-management-service/recipes | Get all recipes |
|`GET`|http://localhost:8080/recipe-management-service/recipes/{id} | Get specific recipe by id |
|`DELETE`|http://localhost:8080/recipe-management-service/recipes/{id} | Delete specific recipe by id |


## Sample JSON Response

When making requests to the Recipe Management Service API, you can expect to receive JSON responses containing information about recipes and their corresponding ingredients. Here is the sample format of the response that you receive when using this API.
```
{
    "id": 1,
    "recipeName": "Veggie Burger",
    "creationDateTime": "2023-04-02T18:22:07.8079062",
    "isVegetarian": true,
    "serves": 2,
    "ingredients": [
        {
            "id": 1,
            "ingredientName": "Lentils",
            "quantity": 200.0,
            "measurementUnit": "GRAM"
        },
        {
            "id": 2,
            "ingredientName": "Breadcrumbs",
            "quantity": 50.0,
            "measurementUnit": "GRAM"
        },
        {
            "id": 3,
            "ingredientName": "Egg",
            "quantity": 1.0,
            "measurementUnit": "ITEM"
        },
        {
            "id": 4,
            "ingredientName": "Onion",
            "quantity": 1.0,
            "measurementUnit": "ITEM"
        },
        {
            "id": 5,
            "ingredientName": "Garlic",
            "quantity": 2.0,
            "measurementUnit": "ITEM"
        },
        {
            "id": 6,
            "ingredientName": "Salt",
            "quantity": 1.0,
            "measurementUnit": "TEASPOON"
        },
        {
            "id": 7,
            "ingredientName": "Black pepper",
            "quantity": 1.0,
            "measurementUnit": "TEASPOON"
        },
        {
            "id": 8,
            "ingredientName": "Burger buns",
            "quantity": 2.0,
            "measurementUnit": "ITEM"
        },
        {
            "id": 9,
            "ingredientName": "Lettuce leaves",
            "quantity": 4.0,
            "measurementUnit": "ITEM"
        },
        {
            "id": 10,
            "ingredientName": "Tomato slices",
            "quantity": 4.0,
            "measurementUnit": "ITEM"
        },
        {
            "id": 11,
            "ingredientName": "Mayonnaise",
            "quantity": 2.0,
            "measurementUnit": "TABLESPOON"
        },
        {
            "id": 12,
            "ingredientName": "Ketchup",
            "quantity": 2.0,
            "measurementUnit": "TABLESPOON"
        }
    ],
    "cookingInstructions": "Rinse lentils and cook in boiling water for 20 minutes until soft. Drain lentils and transfer to a large bowl. Mash lentils with a fork or potato masher. Add breadcrumbs, egg, finely chopped onion and garlic, salt, and black pepper to the bowl. Mix well. Form the mixture into 2 patties. Heat oil in a pan over medium-high heat. Cook patties until browned and crispy, about 4 minutes per side. Toast burger buns. Spread mayonnaise and ketchup on the bottom bun. Add lettuce, tomato, and veggie patty. Cover with the top bun. Serve immediately."
}
```
