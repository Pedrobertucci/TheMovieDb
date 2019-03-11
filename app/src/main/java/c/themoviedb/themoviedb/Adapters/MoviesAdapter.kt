package c.themoviedb.themoviedb.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import c.themoviedb.themoviedb.Layouts.MovieActivity
import c.themoviedb.themoviedb.Models.Movies
import c.themoviedb.themoviedb.R
import c.themoviedb.themoviedb.Utils.Consts
import com.squareup.picasso.Picasso

class MoviesAdapter (private val context: Context, private val movieList: ArrayList<Movies>)
    : RecyclerView.Adapter<ResultViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_movie, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        Picasso.with(context)
            .load(Consts.BASE_URL_IMAGES+movieList[position].poster)
            .into(holder.imageMovie)

        holder.imageMovie.setOnClickListener {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(Consts.MOVIE, movieList[position])
            context.startActivity(intent)
        }

    }
}