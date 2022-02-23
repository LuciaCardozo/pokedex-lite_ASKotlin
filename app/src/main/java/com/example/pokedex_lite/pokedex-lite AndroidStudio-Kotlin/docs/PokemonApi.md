# PokemonApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**pokemonGet**](PokemonApi.md#pokemonGet) | **GET** /pokemon | Retrieves all pokemons given a specific user
[**pokemonPost**](PokemonApi.md#pokemonPost) | **POST** /pokemon | Creates a new pokemon in the system
[**pokemonPut**](PokemonApi.md#pokemonPut) | **PUT** /pokemon | Edits a pokemon information in the system

<a name="pokemonGet"></a>
# **pokemonGet**
> kotlin.Array&lt;Pokemon&gt; pokemonGet(userId)

Retrieves all pokemons given a specific user

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = PokemonApi()
val userId : kotlin.String = userId_example // kotlin.String | 
try {
    val result : kotlin.Array<Pokemon> = apiInstance.pokemonGet(userId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PokemonApi#pokemonGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PokemonApi#pokemonGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **kotlin.String**|  |

### Return type

[**kotlin.Array&lt;Pokemon&gt;**](Pokemon.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="pokemonPost"></a>
# **pokemonPost**
> pokemonPost(body)

Creates a new pokemon in the system

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = PokemonApi()
val body : PokemonBody =  // PokemonBody | 
try {
    apiInstance.pokemonPost(body)
} catch (e: ClientException) {
    println("4xx response calling PokemonApi#pokemonPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PokemonApi#pokemonPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PokemonBody**](PokemonBody.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="pokemonPut"></a>
# **pokemonPut**
> pokemonPut(body)

Edits a pokemon information in the system

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = PokemonApi()
val body : Pokemon =  // Pokemon | 
try {
    apiInstance.pokemonPut(body)
} catch (e: ClientException) {
    println("4xx response calling PokemonApi#pokemonPut")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PokemonApi#pokemonPut")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Pokemon**](Pokemon.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

