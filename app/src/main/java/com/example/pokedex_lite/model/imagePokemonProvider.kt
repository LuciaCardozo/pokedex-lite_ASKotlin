package com.example.pokedex_lite.model

class ImagePokemonProvider {
    companion object{
        fun addImage(url:String):String{
            for (image in images){
                if(url == image.name){
                    return image.url
                }
            }
            return "https://media-exp1.licdn.com/dms/image/C4D0BAQFjxJ1eO2Ct2g/company-logo_200_200/0/1611321134477?e=2159024400&v=beta&t=CbV4jaYH5PuNO0aVuBYTzqV22j8QnIqsCgXjO0jeiR0"
        }
        fun addImageEvolution(url:String):String{
            for (image in images){
                if(url == image.name){
                    return image.url
                }
            }
            return ""
        }
        private val images= listOf(
            ImgPokemon(
                name = "assets/bulbasaur.png",
                url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png"),
            ImgPokemon(
                name = "assets/ivysaur.png",
                url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/002.png"),
            ImgPokemon(
                name = "assets/venusaur.png",
                url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/003.png"),
            ImgPokemon(
                name = "assets/charmander.png",
                url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png"),
            ImgPokemon(
                name = "assets/charmaleon.png",
                url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/005.png"),
            ImgPokemon(
                name = "assets/charizard.png",
                url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/006.png")
        )

    }


}