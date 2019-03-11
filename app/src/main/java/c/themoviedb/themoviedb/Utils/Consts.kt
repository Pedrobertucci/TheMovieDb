package c.themoviedb.themoviedb.Utils

import android.app.Application

open class Consts: Application() {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val BASE_URL_IMAGES = "https://image.tmdb.org/t/p/w500"
        const val MOVIE = "MOVIE"
    }

}