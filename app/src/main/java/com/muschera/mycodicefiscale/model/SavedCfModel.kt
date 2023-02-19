package com.muschera.mycodicefiscale.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.muschera.mycodicefiscale.database.DBCity
import com.muschera.mycodicefiscale.database.RepositoryCity
import com.muschera.mycodicefiscale.database.SavedCF

class SavedCfModel(application: Application) {
    val allSavedCF: LiveData<List<SavedCF>>
    val repositoryCity: RepositoryCity

    init {
        val db = DBCity.getInstance(application)
        val dao = db.cityDAO()
        repositoryCity = RepositoryCity(dao)

        allSavedCF = repositoryCity.savedCF

    }
}