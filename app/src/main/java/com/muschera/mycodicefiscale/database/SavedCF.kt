package com.muschera.mycodicefiscale.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SavedCF {
    @PrimaryKey
    var cf: String = ""
    var firstName: String = ""
    var lastName: String = ""
}