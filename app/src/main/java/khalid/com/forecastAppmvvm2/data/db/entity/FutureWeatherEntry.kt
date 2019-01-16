package khalid.com.forecastAppmvvm2.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices = [Index(value = ["date"],unique = true)])
data class FutureWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val date: String,
    @Embedded
    val day: Day
)