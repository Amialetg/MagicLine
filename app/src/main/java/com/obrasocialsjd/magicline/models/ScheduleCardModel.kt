package com.obrasocialsjd.magicline.models

import java.io.Serializable

data class ScheduleCardModel(
        val hour : String,
        val title : String,
        val subtitle : String,
        val body : String,
        val detailModel : DetailModel,
        val thisType : Int,
        val isSelected: Boolean,
        val listener: (DetailModel) -> Unit
): ScheduleGeneralModel(thisType), Serializable