package com.furkankurt.kotlin_foodbook_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class Food(
    @ColumnInfo(name="isim")
    @SerializedName("isim")
    val foodName:String?,

    @ColumnInfo(name="kalori")
    @SerializedName("kalori")
    val foodCalories:String?,

    @ColumnInfo(name="karbonhidrat")
    @SerializedName("karbonhidrat")
    val foodCarbonhidrat:String?,

    @ColumnInfo(name="protein")
    @SerializedName("protein")
    val foodProtein:String?,

    @ColumnInfo(name="yag")
    @SerializedName("yag")
    val foodYag:String?,

    @ColumnInfo(name="gorsel")
    @SerializedName("gorsel")
    val foodImage:String?

                ) {

    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}