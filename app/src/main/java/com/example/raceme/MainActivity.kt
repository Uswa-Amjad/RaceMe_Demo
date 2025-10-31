package com.example.raceme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.raceme.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        FirebaseAuth.getInstance().signInAnonymously() // dev only

        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        // 1) Get NavController from the NavHostFragment (NOT from bottomNav)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2) Wire the bottom bar to that controller
        b.bottomNav.setupWithNavController(navController)
    }
}
