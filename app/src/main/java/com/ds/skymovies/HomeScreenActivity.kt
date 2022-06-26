package com.ds.skymovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ds.skymovies.databinding.ActivityHomeScreenBinding

/**
 * Created by Dinesh on 24/06/2022.
 */
class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}