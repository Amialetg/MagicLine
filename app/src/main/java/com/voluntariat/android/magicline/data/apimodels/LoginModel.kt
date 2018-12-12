package com.voluntariat.android.magicline.data.apimodels

import com.google.gson.annotations.SerializedName

data class LoginModel(

	@field:SerializedName("client")
	val loginModelClient: LoginModelClient? = null
)