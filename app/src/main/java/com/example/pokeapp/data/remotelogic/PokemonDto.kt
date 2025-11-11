package com.example.pokeapp.data.remotelogic

import com.google.gson.annotations.SerializedName

// DTOs (Data Transfer Objects) para a PokeAPI

data class PokemonResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeSlot>,
    // Para a dificuldade "Difícil", precisamos da espécie (que contém a região)
    val species: PokemonSpeciesRef
)

data class PokemonSprites(
    val other: PokemonOtherSprites
)

data class PokemonOtherSprites(
    // Mapeia o nome "official-artwork" do JSON para a nossa variável
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
    val url: String // URL para buscar os detalhes da espécie
)

// DTO para a segunda chamada (buscar a Região/Geração)
data class PokemonSpeciesResponse(
    val generation: PokemonGeneration
)

data class PokemonGeneration(
    val name: String // ex: "generation-i"
)