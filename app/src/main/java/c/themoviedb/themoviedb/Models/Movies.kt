package c.themoviedb.themoviedb.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Results(
    @SerializedName("results") val movies: List<Movies>
)

data class Movies(
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("release_date") val date: Date
) : Serializable