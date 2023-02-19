package com.muschera.mycodicefiscale.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object CFModel {

    var res: MutableState<String> = mutableStateOf("XXXXXXXXXXXXXXXX")
    var lastName = ""
    var firstName = ""
    var sex = "M"
    var birthPlace = ""
    var distr = ""
    var day = ""
    var month = ""
    var year = ""


}