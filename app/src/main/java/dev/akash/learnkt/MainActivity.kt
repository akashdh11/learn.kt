package dev.akash.learnkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.akash.learnkt.data.AuthManager
import dev.akash.learnkt.ui.AppNavigation

class MainActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager = AuthManager(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
        setContent {
            AppNavigation(authManager)
        }
    }
}
