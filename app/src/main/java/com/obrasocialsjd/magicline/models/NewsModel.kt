package com.obrasocialsjd.magicline.models

data class NewsModel(val title: String? = "", val subtitle: String? = "", val detailModel: DetailModel, val listener: (NewsModel) -> Unit)