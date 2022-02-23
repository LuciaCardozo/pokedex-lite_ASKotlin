/**
 * Pokedex API
 * API to provide access to the pokedex database
 *
 * OpenAPI spec version: 2.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.swagger.client.models


/**
 * 
 * @param id 
 * @param role 
 * @param pokemonCount 
 * @param firstName 
 * @param lastName 
 * @param username 
 * @param password 
 */
data class User (

    val id: String? = null,
    val role: Array<String>? = null,
    val pokemonCount: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val password: String? = null
) {
}