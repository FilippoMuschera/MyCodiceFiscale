package com.muschera.mycodicefiscale.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class City {
    var description: String = ""
    var abbreviation: String = ""

    @PrimaryKey
    var code: String = ""


}
