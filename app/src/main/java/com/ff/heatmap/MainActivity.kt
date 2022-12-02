package com.ff.heatmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ff.heatmap.heatmap.HeatMap
import com.ff.heatmap.heatmap.HeatMapOverlay
import com.google.android.material.snackbar.Snackbar
import com.ff.heatmap.heatmap.WeightedLatLng
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var screenWidth = 0
    private var screenHeight = 0
    private var imageView: ImageView? = null
    private var heatMap: HeatMap? = null
    private var heatMapOverlay: HeatMapOverlay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image)
        val fab1 = findViewById<View>(R.id.fab1)
        val fab2 = findViewById<View>(R.id.fab2)
        fab1.setOnClickListener { view ->
            Snackbar.make(view, "regenerate heatmap data", Snackbar.LENGTH_LONG)
                .setAction("ok") {
                    val data = generateHeatMapData()
                    heatMap!!.setWeightedData(data)
                    imageView!!.setImageBitmap(heatMap!!.generateMap())
                }.show()
        }
        fab2.setOnClickListener { view ->
            Snackbar.make(view, "Regenerate HeatMapOverlay Data", Snackbar.LENGTH_LONG)
                .setAction("ok") {
                    val data = generateHeatMapData()
                    heatMapOverlay!!.setWeightedData(data)
                    imageView!!.setImageBitmap(heatMapOverlay!!.generateMap())
                }.show()
        }
        measureScreen()
        val data = generateHeatMapData()
        heatMap =
            HeatMap.Builder().weightedData(data).radius(35).width(screenWidth).height(screenHeight)
                .build()
        heatMapOverlay = HeatMapOverlay.Builder().weightedData(data).radius(35).width(screenWidth)
            .height(screenHeight).build()
        imageView!!.setImageBitmap(heatMap?.generateMap())
    }

    private fun measureScreen() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
    }

    private fun generateHeatMapData(): List<WeightedLatLng> {
        val data: MutableList<WeightedLatLng> = ArrayList()
        for (i in 0..999) {
            data.add(
                WeightedLatLng(
                    (Math.random() * screenWidth).toInt(), (Math.random() * screenHeight).toInt(),
                    Math.random() * 100
                )
            )
        }
        return data
    }
}