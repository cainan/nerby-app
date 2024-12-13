package com.cso.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cso.nearby.data.model.Market
import com.cso.nearby.ui.screen.HomeScreen
import com.cso.nearby.ui.screen.MarketDetailsScreen
import com.cso.nearby.ui.screen.SplashScreen
import com.cso.nearby.ui.screen.WelcomeScreen
import com.cso.nearby.ui.screen.route.Home
import com.cso.nearby.ui.screen.route.Splash
import com.cso.nearby.ui.screen.route.Welcome
import com.cso.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }

                    composable<Welcome> {
                        WelcomeScreen(
                            onNavigateToHome = {
                                navController.navigate(Home)
                            }
                        )
                    }

                    composable<Home> {
                        HomeScreen(onNavigateToMarketDetails = {
                            navController.navigate(it)
                        })
                    }

                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()
                        MarketDetailsScreen(
                            market = selectedMarket,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}