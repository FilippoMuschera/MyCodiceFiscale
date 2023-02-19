package com.muschera.mycodicefiscale.ui.theme

import android.app.Application
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.muschera.mycodicefiscale.R
import com.muschera.mycodicefiscale.codicefiscale.CodiceFiscaleController
import com.muschera.mycodicefiscale.model.SavedCfModel
import kotlinx.coroutines.delay




@Composable
fun SplashScreenAnimation(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1500)
        //ScreenRouter.navigateTo(1)
        navController.navigate("CFLayout")

    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.logo2), contentDescription = "logo")
    }


}

