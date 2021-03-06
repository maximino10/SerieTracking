package com.example.serietracking
import android.content.Intent
import android.graphics.Canvas
import kotlinx.android.synthetic.main.activity_main.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity


import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.HttpConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide

//data class Capitulo(val title: String, val year: Int, val image: String)

//TODO: ESta impoementacion del callback no deberia ir aca, pero se hizo aca para probar que la conexion con la BD funcione bien.
class MainActivity : AppCompatActivity(), Callback<SeasonModel> {
    private val capitulos = mutableListOf<Capitulo>()

    val viewAdapter = ListAdapter(capitulos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("log","iniciando")


        val viewManager = LinearLayoutManager(this)

        list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior

            layoutManager = viewManager
            // set the custom adapter to the RecyclerView
            adapter = viewAdapter
        }


        Glide.with(this).load("https://image.tmdb.org/t/p/w500/iflq7ZJfso6WC7gk9l1tD3unWK.jpg").into(imageView)
        initSwipe()
        ApiClient.apiInterface.getSeasonOfGOT(HttpConstants.API_KEY).enqueue(this)
//        ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(this)
    }


    private fun initSwipe() {

     //   recyclerView = findViewById(R.id.card_recycler_view) as RecyclerView

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

            //    val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
//                    val width = height / 3

                    if (dX > 0) {

                        Log.d("log","AAA")

                    //    p.color = Color.parseColor("#388E3C")
                     //   val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                     //   c.drawRect(background, p)
                     ////   icon = BitmapFactory.decodeResource(resources, R.drawable.ic_edit_white)
                     //   val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                     //   c.drawBitmap(icon, null, icon_dest, p)
                    } else {


                        Log.d("log","BBB")
                      //  p.color = Color.parseColor("#D32F2F")
                     //   val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                      //  c.drawRect(background, p)
                      //  icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                      //  val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                       // c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }




            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                Log.d("log","FGF")
                if (direction == ItemTouchHelper.LEFT) {

                    capitulos.removeAt(position)
                    viewAdapter.notifyDataSetChanged();

                    //adapter!!.removeItem(position)
                } else {
                 //   removeView()
                   // edit_position = position
                  //  alertDialog!!.setTitle("Edit Name")
                  //  et_name!!.setText(names[position])
                  //  alertDialog!!.show()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(list_recycler_view)
        }

    override fun onResponse(call: Call<SeasonModel>?, response: Response<SeasonModel>?) {
        if (response!!.isSuccessful) {

           for (item: SeasonModel.Episode in response.body().episodes!!) {

             capitulos.add(Capitulo(item.name.toString(),"",item.episodeNumber.toString()))
            }

            viewAdapter.notifyDataSetChanged();
        }
    }

    override fun onFailure(call: Call<SeasonModel>?, t: Throwable?) {
        print("error")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
