package c.themoviedb.themoviedb.Rest

import c.themoviedb.themoviedb.Models.Results
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {
    @GET("3/search/movie?api_key=83d01f18538cb7a275147492f84c3698&language=en-Us&page=1&include_adult=true")
    fun searchMovies(@Query("query") nameMovie: String): Observable<Results>
}


