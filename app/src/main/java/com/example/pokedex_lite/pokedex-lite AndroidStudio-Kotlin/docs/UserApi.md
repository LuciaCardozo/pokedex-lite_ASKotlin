# UserApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**userGet**](UserApi.md#userGet) | **GET** /user | Retrieves all users that match the criteria
[**userPost**](UserApi.md#userPost) | **POST** /user | Creates a new user
[**userUserIdGet**](UserApi.md#userUserIdGet) | **GET** /user/{userId} | Retrieves a user
[**userUserIdPut**](UserApi.md#userUserIdPut) | **PUT** /user/{userId} | Edits a user information

<a name="userGet"></a>
# **userGet**
> kotlin.Array&lt;User&gt; userGet()

Retrieves all users that match the criteria

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UserApi()
try {
    val result : kotlin.Array<User> = apiInstance.userGet()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userGet")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.Array&lt;User&gt;**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="userPost"></a>
# **userPost**
> User userPost(body)

Creates a new user

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UserApi()
val body : UserPostRequest =  // UserPostRequest | 
try {
    val result : User = apiInstance.userPost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserPostRequest**](UserPostRequest.md)|  | [optional]

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="userUserIdGet"></a>
# **userUserIdGet**
> User userUserIdGet(userId)

Retrieves a user

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UserApi()
val userId : kotlin.String = userId_example // kotlin.String | 
try {
    val result : User = apiInstance.userUserIdGet(userId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userUserIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userUserIdGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **kotlin.String**|  |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="userUserIdPut"></a>
# **userUserIdPut**
> userUserIdPut(userId, body)

Edits a user information

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = UserApi()
val userId : kotlin.String = userId_example // kotlin.String | 
val body : User =  // User | 
try {
    apiInstance.userUserIdPut(userId, body)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userUserIdPut")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userUserIdPut")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **kotlin.String**|  |
 **body** | [**User**](User.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

