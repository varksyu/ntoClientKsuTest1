package ru.myitschool.work.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.work.R
import ru.myitschool.work.ui.login.LoginDestination
import ru.myitschool.work.ui.login.LoginFragment
import ru.myitschool.work.ui.qr.scan.QrScanDestination
import ru.myitschool.work.ui.qr.scan.QrScanFragment


// НЕ ИЗМЕНЯЙТЕ НАЗВАНИЕ КЛАССА!
@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val prefs = getSharedPreferences("app", MODE_PRIVATE)
        val login = prefs.getString("login", null)

        if (login != null) {
            startProfileActivity()
        } else {
            startLoginActivity()
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        if (navHostFragment != null) {
            val navController = navHostFragment.navController
            navController.graph = navController.createGraph(
                startDestination = LoginDestination
            ) {
                fragment<LoginFragment, LoginDestination>()
                fragment<QrScanFragment, QrScanDestination>()
                //fragment<ProfileFragment, ProfileDestination>()


            }
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onSupportNavigateUp()
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        val popBackResult = if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            false
        }
        return popBackResult || super.onSupportNavigateUp()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
        finish() // Закрываем RootActivity, чтобы оно не оставалось в стеке
    }

    private fun startProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish() // Закрываем RootActivity
    }
}