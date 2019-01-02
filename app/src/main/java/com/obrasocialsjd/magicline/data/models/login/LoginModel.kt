package com.obrasocialsjd.magicline.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginModel(

	@field:SerializedName("client")
	val loginModelClient: LoginModelClient? = null
)