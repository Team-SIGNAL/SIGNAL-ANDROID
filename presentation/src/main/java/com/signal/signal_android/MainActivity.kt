package com.signal.signal_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.signal.signal_android.designsystem.foundation.SignalAndroidTheme

class MainActivity : ComponentActivity() {

    private var delay = 0L

    private lateinit var dbInitializer: DBInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbInitializer = DBInitializer(context = this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BackHandler(onBack = this::setBackHandler)
            SignalAndroidTheme {
                SignalApp()
            }
        }
    }

    private fun setBackHandler() {
        if (System.currentTimeMillis() > delay) {
            delay = System.currentTimeMillis() + 1000
            Toast.makeText(this, getText(R.string.back_handler_message), Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}
