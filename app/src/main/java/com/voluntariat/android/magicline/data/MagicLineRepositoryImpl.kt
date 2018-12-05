package com.voluntariat.android.magicline.data

import com.voluntariat.android.magicline.Utils.callback
import com.voluntariat.android.magicline.data.api.MagicLineAPI
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class MagicLineRepositoryImpl(private val service: MagicLineAPI.MagicLineService)
    : MagicLineRepository {

    val accessToken = "8b6c79ccbb67a56cf698bc8852c39c207d941c3e"

    override fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit) {
        //val userData = hashMapOf("username" to username, "password" to password)
        /*val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build()*/
        /*val usernamePart = RequestBody.create(MediaType.parse("text/plain"), username)
        val passwordPart = RequestBody.create(MediaType.parse("text/plain"), password)*/

        service.oAuthLogin(username, password).enqueue(callback(
            { result ->
                if (result.isSuccessful) {
                    result.body()?.loginModelClient?.accessToken?.let { accessToken ->
                        onResult(Result.Success(accessToken))
                    } ?: onResult(Result.Failure(Throwable("Unexpected response")))
                } else {
                    onResult(Result.Failure(Throwable(result.errorBody().toString())))
                }
            }, { error ->
                onResult(Result.Failure(error))
            }
        ))

        /*service.posts(accessToken).enqueue(callback(
                { result ->
                    if (result.isSuccessful) {
                        result.body()
                    } else {
                        onResult(Result.Failure(Throwable(result.errorBody().toString())))
                    }
                }, { error ->
            onResult(Result.Failure(error))
        }
        ))*/

        /*val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "username=apiml&password=4p1ml2018")
        val request = Request.Builder()
                .url("http://www.andreurm.cat/ws/oAuthLogin")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()

        Thread(Runnable {
            val response = client.newCall(request).execute()
            response.body()
        }).start()*/

        /*val stringBuffer = StringBuffer("")
        var bufferedReader: BufferedReader? = null
        try {
            val httpClient : HttpCl = HttpClient()
            val httpGet = HttpGet()

            val uri = URI("http://sample.campfirenow.com/rooms.xml")
            httpGet.setURI(uri)
            httpGet.addHeader(BasicScheme.authenticate(
                    UsernamePasswordCredentials("user", "password"),
                    HTTP.UTF_8, false))

            val httpResponse = httpClient.execute(httpGet)
            val inputStream = httpResponse.getEntity().getContent()
            bufferedReader = BufferedReader(InputStreamReader(
                    inputStream))

            var readLine = bufferedReader!!.readLine()
            while (readLine != null) {
                stringBuffer.append(readLine)
                stringBuffer.append("\n")
                readLine = bufferedReader!!.readLine()
            }
        } catch (e: Exception) {
            // TODO: handle exception
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader!!.close()
                } catch (e: IOException) {
                    // TODO: handle exception
                }

            }
        }
        stringBuffer.toString()*/
    }


}