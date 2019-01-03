package com.obrasocialsjd.magicline.data.models.posts

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun listOfPostImagesToString(list: List<PostImageItem>?): String? = list?.let { Gson().toJson(list) }

    @TypeConverter fun stringToListOfPostImages(stringOfList: String?): List<PostImageItem>? =
            stringOfList?.let { Gson().fromJson(stringOfList, Array<PostImageItem>::class.java).toList() }


}