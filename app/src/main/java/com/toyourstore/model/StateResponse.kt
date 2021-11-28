package com.toyourstore.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class StateResponse {
    @SerializedName("states")
    @Expose
    var states: List<State>? = null

    class State : Serializable {
        var isSelected: Boolean = false

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
}