package com.voluntariat.android.magicline.data
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.voluntariat.android.magicline.data.api.MagicLineAPI
import com.voluntariat.android.magicline.data.models.donations.Donations
import com.voluntariat.android.magicline.data.models.donations.DonationsDBModel
import com.voluntariat.android.magicline.data.models.posts.PostsItem
import com.voluntariat.android.magicline.db.MagicLineDB
import com.voluntariat.android.magicline.db.dao.DonationsDAO
import com.voluntariat.android.magicline.db.dao.PostDao
import com.voluntariat.android.magicline.utils.callback


class MagicLineRepositoryImpl(database: MagicLineDB?)
    : MagicLineRepository {

    private lateinit var mPostDao: PostDao
    private lateinit var donationsDAO: DonationsDAO
    private val mAllPosts: LiveData<List<PostsItem>>
    private val donations: LiveData<List<DonationsDBModel>>

    init {
        val db = database
        if (db != null) {
            mPostDao = db.postDao()
            donationsDAO = db.donationsDAO()
        }
        mAllPosts = mPostDao.getAllPosts()
        donations = donationsDAO.getDonations()
    }

    private class InsertAsyncTask internal constructor(private val asyncPostDao: PostDao) : AsyncTask<PostsItem, Void, Void>() {

        override fun doInBackground(vararg params: PostsItem): Void? {
            return null
        }
    }

    fun insert(postItem: PostsItem) {
        InsertAsyncTask(mPostDao).execute(postItem)
    }


    override fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit) {

        MagicLineAPI.accessToken = null
        MagicLineAPI.service.oAuthLogin(username, password).enqueue(callback(
            { result ->
                if (result.isSuccessful) {
                    result.body()?.loginModelClient?.accessToken?.let { accessToken ->
                        MagicLineAPI.accessToken = accessToken
                        onResult(Result.Success(accessToken))
                    } ?: onResult(Result.Failure(Throwable("Unexpected response")))
                } else {
                    onResult(Result.Failure(Throwable(result.errorBody().toString())))
                }
            }, { error ->
                onResult(Result.Failure(error))
            }
        ))
    }

    override fun getPosts() : LiveData<List<PostsItem>> {
        MagicLineAPI.service.posts().enqueue(callback(
            { result ->
                if (result.isSuccessful) {
                    var posts = result.body()?.posts as ArrayList<PostsItem>
                    Log.d("###", posts.toString())
                    mPostDao.nukeTable()
                    mPostDao.insertList(posts)
                }
            }))

        return mPostDao.getAllPosts()
    }

    override fun getDonations(): LiveData<List<DonationsDBModel>> {
        MagicLineAPI.service.donations().enqueue(callback(
            {
                result ->
                if (result.isSuccessful) {
                    var donations = result.body()?.donations as Donations

                    insertDonationsDBModelFromAPI(donations)
                }
            }
        ))

        return donationsDAO.getDonations()
    }

    private fun insertDonationsDBModelFromAPI(donations : Donations) {
        donationsDAO.insert(DonationsDBModel(0,
                                                donations.valencia?.amount,
                                                donations.bml?.amount,
                                                donations.barcelona?.amount,
                                                donations.mallorca?.amount))
    }
}