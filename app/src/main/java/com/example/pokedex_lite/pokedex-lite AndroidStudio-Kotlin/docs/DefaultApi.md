# DefaultApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**statusGet**](DefaultApi.md#statusGet) | **GET** /status | is server running?

<a name="statusGet"></a>
# **statusGet**
> kotlin.String statusGet()

is server running?

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = DefaultApi()
try {
    val result : kotlin.String = apiInstance.statusGet()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#statusGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#statusGet")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/string

