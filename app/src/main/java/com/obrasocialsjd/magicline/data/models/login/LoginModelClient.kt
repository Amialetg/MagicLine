package com.obrasocialsjd.magicline.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginModelClient(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("expires_in")
	val expiresIn: String? = null
)