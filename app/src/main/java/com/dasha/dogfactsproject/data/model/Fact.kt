package com.dasha.dogfactsproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fact (
    @PrimaryKey
    val fact : String
        )