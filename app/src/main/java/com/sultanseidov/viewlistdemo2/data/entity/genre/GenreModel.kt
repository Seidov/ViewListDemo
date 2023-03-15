package com.sultanseidov.viewlistdemo2.data.entity.genre

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sultanseidov.viewlistdemo2.util.Constants

@Entity(tableName = Constants.GENRES_TABLE)
data class GenreModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)