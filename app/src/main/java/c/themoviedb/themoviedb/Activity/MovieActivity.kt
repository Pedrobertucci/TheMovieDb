package c.themoviedb.themoviedb.Activity

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import c.themoviedb.themoviedb.Models.Movies
import c.themoviedb.themoviedb.R
import c.themoviedb.themoviedb.Utils.Consts
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import java.text.SimpleDateFormat
import java.util.*


class MovieActivity : AppCompatActivity() {

    lateinit var movie: Movies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val bundle: Bundle? = intent.extras

        bundle?.let {
            bundle.apply {
                movie = getSerializable("MOVIE") as Movies
            }
        }

        Picasso.with(this)
            .load(Consts.BASE_URL_IMAGES+movie.poster)
            .into(imagePoster)

        nameMovie.text = movie.title
        overview.text = movie.overview
        validateDate(movie.date)
    }

    private fun validateDate(dateMovie: Date) {
        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)

        if(dateMovie.year == Date().year) {
            date.setTextColor(resources.getColor(R.color.text_red))
            date.setTypeface(null, Typeface.BOLD)
        }

        date.text = simpleDateFormat.format(dateMovie)
    }
}
