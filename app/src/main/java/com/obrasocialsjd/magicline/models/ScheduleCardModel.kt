package com.obrasocialsjd.magicline.models

data class ScheduleCardModel(
        val hour : String,
        val title : String,
        val subtitle : String,
        val description : String,
        val detailModel : DetailModel,
        val thisType : Int,
        val isSelected: Boolean
): ScheduleGeneralModel(thisType)