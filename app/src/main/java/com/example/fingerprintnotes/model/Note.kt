package com.example.fingerprintnotes.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "Notes"
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Body")
    val body: String?,

):Serializable
