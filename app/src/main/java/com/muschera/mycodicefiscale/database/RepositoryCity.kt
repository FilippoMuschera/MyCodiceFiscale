package com.muschera.mycodicefiscale.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RepositoryCity(private val daoCity: DaoCity) {

    var savedCF: LiveData<List<SavedCF>> = daoCity.getAllSavedCF()


    fun insertSavedCF(savedCF: SavedCF) {
        CoroutineScope(Dispatchers.IO).launch {
            daoCity.insertSavedCF(savedCF)
        }
    }

    fun getCity(filter1: String, filter2: String): City {
        return daoCity.getCity(filter1 = filter1, filter2 = filter2)
    }

    fun deleteSavedCF(savedCF: String) {
        CoroutineScope(Dispatchers.IO).launch {
            daoCity.deleteSavedCF(savedCF)
        }
    }


}