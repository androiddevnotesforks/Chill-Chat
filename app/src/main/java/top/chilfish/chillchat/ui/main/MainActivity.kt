package top.chilfish.chillchat.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import top.chilfish.chillchat.BaseActivity
import top.chilfish.chillchat.provider.isLoggedIn
import top.chilfish.chillchat.ui.login.LoginActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel = ViewModelProvider(
                this,
                MainViewModelFactory(navController)
            )[MainViewModel::class.java]

            MainPage(
                viewModel = viewModel,
                navController = navController
            )
        }

        lifecycleScope.launch {
            isLoggedIn.collect {
                if (!it) {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        }
    }
}
