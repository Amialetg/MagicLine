package com.obrasocialsjd.magicline.models

import java.io.Serializable

/**
 * Created by hector on 27/06/18.
 */
data class ScheduleTextModel(val hour: String, val text: String):ScheduleGeneralModel(1), Serializable