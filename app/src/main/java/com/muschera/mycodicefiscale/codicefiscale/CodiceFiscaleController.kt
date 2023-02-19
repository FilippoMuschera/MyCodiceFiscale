package com.muschera.mycodicefiscale.codicefiscale

import android.util.Log
import com.muschera.mycodicefiscale.database.City
import com.muschera.mycodicefiscale.database.RepositoryCity
import com.muschera.mycodicefiscale.model.CFModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CodiceFiscaleController(val repositoryCity: RepositoryCity) {


    val VOWELS = "AEIOU"
    val CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ"
    val MONTHS = "ABCDEHLMPRST"
    val CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val ODDVALUES = listOf(
        1, 0, 5, 7, 9, 13, 15, 17, 19,
        21, 1, 0, 5, 7, 9, 13, 15, 17,
        19, 21, 2, 4, 18, 20, 11, 3, 6,
        8, 12, 14, 16, 10, 22, 25, 24, 23
    )
    val EVENVALUES = listOf(
        0, 1, 2, 3, 4, 5, 6, 7, 8,
        9, 0, 1, 2, 3, 4, 5, 6, 7,
        8, 9, 10, 11, 12, 13, 14, 15, 16,
        17, 18, 19, 20, 21, 22, 23, 24, 25
    )

    fun getNameCF(name: String): String {
        var vowels = ""
        var cons = ""

        val nameToUpper = name.uppercase()

        for (c in nameToUpper) {
            if (c in VOWELS) vowels += c
            if (c in CONSONANTS) cons += c
        }


        if (cons.length > 3)
            return "" + cons[0] + cons[2] + cons[3]
        if (cons.length == 3)
            return cons
        return (cons + vowels + "XXX").substring(0, 3)
    }

    fun getLastNameCF(lastName: String): String {

        var vowels = ""
        var cons = ""

        val lastNameUpper = lastName.uppercase()

        for (c in lastNameUpper) {
            if (c in VOWELS) vowels += c
            if (c in CONSONANTS) cons += c
        }




        return ("" + cons + vowels + "XXX").substring(
            0,
            3
        ) //Per il cognome sono la 1,2,3 cons quindi non servono
        //gli if come nel caso del nome


    }

    fun getBirthYear(birthYear: String): String {

        if (birthYear.isEmpty()) return "XXXX"

        if (birthYear.length >= 4) {
            return birthYear.substring(birthYear.length - 2)
        } else {
            return "" + "XXXX".substring(birthYear.length, 3) + birthYear
        }

    }

    fun getBirthMonth(month: String): String {
        if (month == "") return "X"

        val intMonth = month.toInt()
        return if (intMonth < 1 || intMonth > 12) {
            Log.e("MYCF", "Invalid month")
            "X"
        } else MONTHS[intMonth - 1].toString()
    }

    fun getBirthDay(day: String): String {

        if (day == "") return "XX"

        return if (CFModel.sex.uppercase() == "M") day
        else {
            (day.toInt() + 40).toString()
        }

    }

    fun getCityCode(cityS: String, dist: String, repositoryCity: RepositoryCity) {


        CoroutineScope(Dispatchers.IO).launch {
            val cityTrimmed = cityS.trim()
            val city: City = repositoryCity.getCity(cityTrimmed + "%", dist.uppercase() + "%")

            val res =
                if (city == null || city.code == "") "XXXX" //city == null si dovrebbe poter verificare nel caso in cui il db
                // non trova una corrispondenza
                else city.code


            CFModel.res.value = (CFModel.res.value.substring(0, 11) + res)

            CFModel.res.value += getControlCode(CFModel.res.value)


        }

    }


    fun getControlCode(firstPart: String): String {

        var code = 0

        firstPart.forEachIndexed { index, c ->
            val pos = CHARACTERS.indexOf(c)

            code += if (index % 2 == 0)
                ODDVALUES[pos]
            else
                EVENVALUES[pos]


        }


        return CHARACTERS[code % 26 + 10].toString()

    }

    fun getCF() {
        CFModel.res.value =
            getLastNameCF(CFModel.lastName) + CFModel.res.value.substring(3) //(CFModel.res.value + getLastNameCF("Muschera"))
        CFModel.res.value = CFModel.res.value.substring(
            0,
            3
        ) + getNameCF(CFModel.firstName) + CFModel.res.value.substring(6) //(CFModel.res.value + getNameCF("Filippo"))
        CFModel.res.value = CFModel.res.value.substring(
            0,
            6
        ) + getBirthYear(CFModel.year) + CFModel.res.value.substring(8)//(CFModel.res.value + getBirthYear("1999"))
        CFModel.res.value = CFModel.res.value.substring(
            0,
            8
        ) + getBirthMonth(CFModel.month) + CFModel.res.value.substring(9)//(CFModel.res.value + getBirthMonth("12"))
        CFModel.res.value = CFModel.res.value.substring(
            0,
            9
        ) + getBirthDay(CFModel.day) + CFModel.res.value.substring(11) //(CFModel.res.value + getBirthDay("19"))
        getCityCode(CFModel.birthPlace, CFModel.distr, repositoryCity)
    }


}