package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")  // Nom de la table (optionnel, par défaut c'est le nom de la classe)
data class User(
    @PrimaryKey val id: Int,  // Cette propriété sera la clé primaire de la table
    val name: String  // La colonne pour stocker le nom de l'utilisateur
)