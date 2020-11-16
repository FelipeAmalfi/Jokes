package com.jokes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Joke(
    @SerializedName("icon_url")
    @ColumnInfo(name="icon")
    val icon: String?,
    @ColumnInfo(name="id")
    @PrimaryKey
    val id: String ,
    @ColumnInfo(name="url")
    val url: String,
    @ColumnInfo(name="value")
    val value: String,
)