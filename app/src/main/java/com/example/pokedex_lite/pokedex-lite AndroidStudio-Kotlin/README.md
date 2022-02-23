# io.swagger.client - Kotlin client library for Pokedex API

## Requires

* Kotlin 1.4.30
* Gradle 5.3

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in Swagger definitions.
* Implementation of ApiClient is intended to reduce method counts, specifically to benefit Android targets.

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://localhost:8080*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**statusGet**](docs/DefaultApi.md#statusget) | **GET** /status | is server running?
*PokemonApi* | [**pokemonGet**](docs/PokemonApi.md#pokemonget) | **GET** /pokemon | Retrieves all pokemons given a specific user
*PokemonApi* | [**pokemonPost**](docs/PokemonApi.md#pokemonpost) | **POST** /pokemon | Creates a new pokemon in the system
*PokemonApi* | [**pokemonPut**](docs/PokemonApi.md#pokemonput) | **PUT** /pokemon | Edits a pokemon information in the system
*SecurityApi* | [**loginPOST**](docs/SecurityApi.md#loginpost) | **POST** /login | Allow a user to be autenticated
*UserApi* | [**userGet**](docs/UserApi.md#userget) | **GET** /user | Retrieves all users that match the criteria
*UserApi* | [**userPost**](docs/UserApi.md#userpost) | **POST** /user | Creates a new user
*UserApi* | [**userUserIdGet**](docs/UserApi.md#useruseridget) | **GET** /user/{userId} | Retrieves a user
*UserApi* | [**userUserIdPut**](docs/UserApi.md#useruseridput) | **PUT** /user/{userId} | Edits a user information

<a name="documentation-for-models"></a>
## Documentation for Models

 - [io.swagger.client.models.InlineResponse500](docs/InlineResponse500.md)
 - [io.swagger.client.models.LoginBody](docs/LoginBody.md)
 - [io.swagger.client.models.LoginPostResponse](docs/LoginPostResponse.md)
 - [io.swagger.client.models.Pokemon](docs/Pokemon.md)
 - [io.swagger.client.models.PokemonAbilities](docs/PokemonAbilities.md)
 - [io.swagger.client.models.PokemonBody](docs/PokemonBody.md)
 - [io.swagger.client.models.User](docs/User.md)
 - [io.swagger.client.models.UserPostRequest](docs/UserPostRequest.md)

<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
