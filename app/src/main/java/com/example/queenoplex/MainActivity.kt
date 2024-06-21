package com.example.queenoplex

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

data class Movie(val title: String, val imageUrl: String)

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var updateButton: Button
    private val movies = mutableListOf<Movie>()
    private val adapter = MovieAdapter(movies)

    // IDs gerados programaticamente
    private val recyclerViewId = View.generateViewId()
    private val updateButtonId = View.generateViewId()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Criar layout programaticamente
        val mainLayout = createMainLayout()

        setContentView(mainLayout)

        recyclerView = findViewById(recyclerViewId)
        updateButton = findViewById(updateButtonId)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadMovies()

        updateButton.setOnClickListener {
            updateMovies()
            showAnimation(updateButton)
        }
    }

    private fun createMainLayout(): ViewGroup {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val button = Button(this).apply {
            text = "Proximo filme"
            id = updateButtonId
            setBackgroundColor(Color.parseColor("#ff0000"))
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        val recyclerView = RecyclerView(this).apply {
            id = recyclerViewId
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        layout.addView(button)
        layout.addView(recyclerView)

        return layout
    }

    private fun loadMovies() {
        movies.addAll(listOf(
            Movie("Filme 1", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRfHd-mM9MgWeQNmyKNg-dKuNcCaNVP8EOstPbFLz-JaBdIZ8wA"),
            Movie("Filme 2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzaXRabbhkx9n8aY0IBrz6R3X_nD6L-H2nAYff9yE&usqp=CAE&s"),
            Movie("Filme 3", "https://lumiere-a.akamaihd.net/v1/images/tidalwave_payoff_poster_brazil_caf2354b.jpeg")
        ))
        adapter.notifyDataSetChanged()
    }

    private fun updateMovies() {
        movies.clear()
        movies.addAll(listOf(
            Movie("Filme 4", "https://cinema10.com.br/upload/filmes/filmes_16302_furiosa-poster-novo.jpg?default=poster"),
            Movie("Filme 5", "https://static.stealthelook.com.br/wp-content/uploads/2022/09/novos-filmes-que-eu-mal-posso-esperar-para-ver-esse-ano-avatar-the-way-of-the-water-20220908180112.jpg"),
            Movie("Filme 6", "https://lumiere-a.akamaihd.net/v1/images/encanto_ka_bpo_pay1_ee2c2e0c.jpeg?region=0%2C0%2C1080%2C1350")
        ))
        adapter.notifyDataSetChanged()
    }

    private fun showAnimation(button: Button) {
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 500
        fadeOut.fillAfter = true
        button.startAnimation(fadeOut)
    }
}

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImageView: ImageView = ImageView(itemView.context).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                200,
                300
            )
        }
        val movieTitleTextView: TextView = TextView(itemView.context).apply {
            id = View.generateViewId()
            textSize = 18f
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 0, 0, 0)
            }
        }

        init {
            (itemView as LinearLayout).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setPadding(16, 16, 16, 16)
                addView(movieImageView)
                addView(movieTitleTextView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LinearLayout(parent.context)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitleTextView.text = movie.title

        try {
            val typeface = ResourcesCompat.getFont(holder.itemView.context, R.font.custom_font)
            holder.movieTitleTextView.typeface = typeface
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        Picasso.get().load(movie.imageUrl).into(holder.movieImageView)
    }

    override fun getItemCount() = movies.size
}


//TESTANDO

