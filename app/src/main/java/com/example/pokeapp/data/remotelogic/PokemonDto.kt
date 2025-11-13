package com.example.pokeapp.data.remotelogic

import com.google.gson.annotations.SerializedName

// DTOs (Data Transfer Objects) para a PokeAPI

data class PokemonResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeSlot>,

    val species: PokemonSpeciesRef
)

data class PokemonSprites(
    val other: PokemonOtherSprites
)

data class PokemonOtherSprites(

    @SerializedName("official-artwork")
    val officialArtwork: PokemonOfficialArtwork
)

data class PokemonOfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)

data class PokemonTypeSlot(
    val type: PokemonType
)

data class PokemonType(
    val name: String
)

data class PokemonSpeciesRef(
    val url: String
)


data class PokemonSpeciesResponse(
    val generation: PokemonGeneration
)

data class PokemonGeneration(
    val name: String
)