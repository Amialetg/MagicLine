package com.voluntariat.android.magicline.data

interface MagicLineRepository {
    fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit
    )
}
