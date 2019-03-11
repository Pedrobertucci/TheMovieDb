package c.themoviedb.themoviedb.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import c.themoviedb.themoviedb.Adapters.MoviesAdapter
import c.themoviedb.themoviedb.Models.Movies
import c.themoviedb.themoviedb.Models.Results
import c.themoviedb.themoviedb.Rest.ServiceApi
import c.themoviedb.themoviedb.Utils.RxSearchObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import c.themoviedb.themoviedb.R


class SearchActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    private var moviesList: ArrayList<Movies>? = null
    private var moviesAdapter: MoviesAdapter? = null

    private lateinit var serviceApi: ServiceApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCompositeDisposable = CompositeDisposable()
        serviceApi = ServiceApi()

        initRecyclerView()
        initSearchView()
        verifyItems()
    }

    private fun initRecyclerView() {
        recyclerViewMovies.layoutManager = GridLayoutManager(this, 3)
    }

    private fun initSearchView() {
        mCompositeDisposable?.add(
            RxSearchObservable()
                .fromView(searchView)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(this::getMovies, this::handleError)
        )
    }

    private fun getMovies(movie: String) {
        Log.d("MOVIES", "Will perform API search: $movie")
        mCompositeDisposable?.add(
            serviceApi
                .serviceInterface
                .searchMovies(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(results: Results) {
        Log.d("MOVIES", "Receive response with ${results.movies.size} movies!")
        verifyItems()
        moviesList = ArrayList(results.movies)
        moviesAdapter = MoviesAdapter(this, moviesList!!)
        recyclerViewMovies.adapter = moviesAdapter
    }

    private fun handleError(error: Throwable) {
        Log.e("MOVIES", "Receive error ${error.localizedMessage}")
        if(error is HttpException) {
            if(error.code() == 422) {
                moviesList!!.clear()
                recyclerViewMovies.recycledViewPool.clear()
                txt_search.text = getString(R.string.message_no_movie)
                txt_search.visibility = View.VISIBLE
            }
        }
    }

    private fun verifyItems() {
        Thread(Runnable {
            this@SearchActivity.runOnUiThread {
                if(this.moviesList == null || this.moviesList!!.size == 0){
                    this.txt_search.text = getString(R.string.message_search_movie)
                    this.txt_search.visibility = View.VISIBLE
                } else {
                    this.txt_search.visibility = View.GONE
                }
            }
        }).start()
    }

    override fun onResume() {
        super.onResume()
        verifyItems()
    }

    override fun onRestart() {
        super.onRestart()
        verifyItems()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
