# SecurityApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**loginPOST**](SecurityApi.md#loginPOST) | **POST** /login | Allow a user to be autenticated

<a name="loginPOST"></a>
# **loginPOST**
> LoginPostResponse loginPOST(body)

Allow a user to be autenticated

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = SecurityApi()
val body : LoginBody =  // LoginBody | 
try {
    val result : LoginPostResponse = apiInstance.loginPOST(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling SecurityApi#loginPOST")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling SecurityApi#loginPOST")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LoginBody**](LoginBody.md)|  | [optional]

### Return type

[**LoginPostResponse**](LoginPostResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

