package com.muschera.mycodicefiscale

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.muschera.mycodicefiscale.codicefiscale.CodiceFiscaleController
import com.muschera.mycodicefiscale.model.SavedCfModel
import com.muschera.mycodicefiscale.ui.theme.CFLayout
import com.muschera.mycodicefiscale.ui.theme.MyCodiceFiscaleTheme
import com.muschera.mycodicefiscale.ui.theme.SavedCFLayout
import com.muschera.mycodicefiscale.ui.theme.SplashScreenAnimation

class MainActivity : ComponentActivity() {

    companion object {
        var onMainScreen: Boolean = true

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this







        setContent {
            MyCodiceFiscaleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0XFFFFFEF2)


                ) {

                    AppNavigator(context = context)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (onMainScreen) {
            finish()
        } else {
            onMainScreen = true
            super.onBackPressed()
        }

    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun AppNavigator(context: Context) {

        val savedCfModel = SavedCfModel(LocalContext.current.applicationContext as Application)
        val listOfSavedCF by savedCfModel.allSavedCF.observeAsState(listOf())
        val cfController = CodiceFiscaleController(savedCfModel.repositoryCity)


        val navController = rememberAnimatedNavController()

        AnimatedNavHost(
            navController = navController,
            startDestination = "SplashScreen",
            builder = {
                composable(route = "CFLayout",
                    enterTransition = {

                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(200))


                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(200))
                    }) {
                    CFLayout(
                        cfController = cfController,
                        repositoryCity = savedCfModel.repositoryCity,
                        context = context,
                        navController = navController,


                        )
                }
                composable(route = "SavedCFLayout",
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(200))
                    },
                    enterTransition = {

                        slideInHorizontally(
                            initialOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(200))


                    }) {
                    SavedCFLayout(
                        list = listOfSavedCF,
                        context = context,
                        repositoryCity = savedCfModel.repositoryCity
                    )
                }
                composable("SplashScreen",
                    exitTransition = { fadeOut(animationSpec = tween(200)) }

                ) { SplashScreenAnimation(navController = navController) }
            })
    }
}

