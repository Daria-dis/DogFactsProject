package com.dasha.dogfactsproject.data.model

import com.google.gson.annotations.SerializedName


class Facts (
    @SerializedName("facts")
    var facts: List<String>,
    @SerializedName("success")
    var status : Boolean
)