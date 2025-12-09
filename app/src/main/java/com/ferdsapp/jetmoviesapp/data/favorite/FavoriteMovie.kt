package com.ferdsapp.jetmoviesapp.data.favorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo("title")
    val title: String?,

    @ColumnInfo("poster_path")
    val poster_path: String?,
): Parcelable