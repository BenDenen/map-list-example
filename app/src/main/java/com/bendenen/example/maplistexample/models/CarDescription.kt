package com.bendenen.example.maplistexample.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.squareup.moshi.Json

/**
 *
 * Model for a car description. Example:
{  
   "id":"WMWSW31030T222518",
   "modelIdentifier":"mini",
   "modelName":"MINI",
   "name":"Vanessa",
   "make":"BMW",
   "group":"MINI",
   "color":"midnight_black",
   "series":"MINI",
   "fuelType":"D",
   "fuelLevel":0.7,
   "transmission":"M",
   "licensePlate":"M-VO0259",
   "latitude":48.134557,
   "longitude":11.576921,
   "innerCleanliness":"REGULAR",
   "carImageUrl":"https://de.drive-now.com/static/drivenow/img/cars/mini.png"
}
 * IMPORTANT: By default all fields are mandatory
 */

@Entity
@TypeConverters(CarDescription.Converters::class)
data class CarDescription(@PrimaryKey val id: String,
                          val modelIdentifier: String,
                          val modelName: String,
                          val name: String,
                          val make: String,
                          val group: String,
                          val color: String,
                          val series: String,
                          val fuelType: FuelType,
                          val fuelLevel: Double,
                          val transmission: Transmission,
                          val licensePlate: String,
                          val latitude: Double,
                          val longitude: Double,
                          val innerCleanliness: Cleanliness,
                          val carImageUrl: String) {

    enum class FuelType(val serverKey: String) {

        @Json(name = DIESEL_TYPE_KEY)
        DIESEL(FuelType.DIESEL_TYPE_KEY),
        @Json(name = PETROL_TYPE_KEY)
        PETROL(FuelType.PETROL_TYPE_KEY),
        @Json(name = ELECTRIC_TYPE_KEY)
        ELECTRIC(FuelType.ELECTRIC_TYPE_KEY);

        companion object {
            const val DIESEL_TYPE_KEY = "D"
            const val PETROL_TYPE_KEY = "P"
            const val ELECTRIC_TYPE_KEY = "E"
        }
    }

    enum class Transmission(val serverKey: String) {

        @Json(name = MANUAL_KEY)
        MANUAL(Transmission.MANUAL_KEY),
        @Json(name = AUTOMATIC_KEY)
        AUTOMATIC(Transmission.AUTOMATIC_KEY);


        companion object {
            const val MANUAL_KEY = "M"
            const val AUTOMATIC_KEY = "A"
        }
    }

    enum class Cleanliness(val serverKey: String) {

        @Json(name = REGULAR_KEY)
        REGULAR(Cleanliness.REGULAR_KEY),
        @Json(name = CLEAN_KEY)
        CLEAN(Cleanliness.CLEAN_KEY),
        @Json(name = VERY_CLEAN_KEY)
        VERY_CLEAN(Cleanliness.VERY_CLEAN_KEY);

        companion object {
            const val REGULAR_KEY = "REGULAR"
            const val CLEAN_KEY = "CLEAN"
            const val VERY_CLEAN_KEY = "VERY_CLEAN"
        }
    }

    class Converters {

        // FuelType
        @TypeConverter
        fun fromFuelType(type: FuelType): String = type.serverKey

        @TypeConverter
        fun toFuelType(serverKey: String): FuelType = FuelType.values().first { it.serverKey == serverKey }

        // Transmission
        @TypeConverter
        fun fromTransmission(transmission: Transmission): String = transmission.serverKey

        @TypeConverter
        fun toTransmission(serverKey: String): Transmission = Transmission.values().first { it.serverKey == serverKey }

        // Cleanliness
        @TypeConverter
        fun fromCleanliness(cleanliness: Cleanliness): String = cleanliness.serverKey

        @TypeConverter
        fun toCleanliness(serverKey: String): Cleanliness = Cleanliness.values().first { it.serverKey == serverKey }
    }


}

