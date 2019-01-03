package com.obrasocialsjd.magicline.data
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.obrasocialsjd.magicline.data.api.MagicLineAPI
import com.obrasocialsjd.magicline.data.models.donations.Donations
import com.obrasocialsjd.magicline.data.models.donations.DonationsDBModel
import com.obrasocialsjd.magicline.data.models.posts.PostsItem
import com.obrasocialsjd.magicline.data.models.teams.Markers
import com.obrasocialsjd.magicline.data.models.teams.TeamsDBModel
import com.obrasocialsjd.magicline.data.models.teams.TotalParticipantsDBModel
import com.obrasocialsjd.magicline.db.MagicLineDB
import com.obrasocialsjd.magicline.db.dao.DonationsDAO
import com.obrasocialsjd.magicline.db.dao.PostDao
import com.obrasocialsjd.magicline.db.dao.TeamsMarkersDAO
import com.obrasocialsjd.magicline.utils.*


class MagicLineRepositoryImpl(database: MagicLineDB?)
    : MagicLineRepository {

    private lateinit var mPostDao: PostDao
    private lateinit var donationsDAO: DonationsDAO
    private lateinit var teamsMarkersDAO: TeamsMarkersDAO
    private val mAllPosts: LiveData<List<PostsItem>>
    private val donations: LiveData<DonationsDBModel>

    init {
        val db = database
        if (db != null) {
            mPostDao = db.postDao()
            donationsDAO = db.donationsDAO()
            teamsMarkersDAO = db.teamsMarkersDAO()
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
                    mPostDao.nukeTable()
                    mPostDao.insertList(posts)
                }
            }))

        return mPostDao.getAllPosts()
    }

    override fun getDonations(): LiveData<DonationsDBModel> {
        MagicLineAPI.service.donations().enqueue(callback(
            {
                result ->
                if (result.isSuccessful) {
                    var donations = result.body()?.donations
                    if (donations != null && donations.barcelona?.amount != null) {
                        insertDonationsDBModelFromAPI(donations)
                    }
                }
            }
        ))

        return donationsDAO.getDonations()
    }

    private fun insertDonationsDBModelFromAPI(donations : Donations) {
        donationsDAO.insert(DonationsDBModel(0,
                                                donations.valencia?.amount,
                                                donations.barcelona?.amount,
                                                donations.mallorca?.amount))
    }

    override fun getTeamsMarkers(): LiveData<TotalParticipantsDBModel> {
        MagicLineAPI.service.markerTeams().enqueue(callback({
            result ->
            if (result.isSuccessful) {
                var teamMarkers = result.body()?.markers
                if (teamMarkers != null) {
                    teamsMarkersDAO.nukeTable()
                    insertTeamMarkers(teamMarkers)
                }
            }
        }))

        return teamsMarkersDAO.getParticipantsByCity(getFlavor())
    }

    private fun insertTeamMarkers(teamMarkers : Markers) {
        when (getFlavor()) {
            BARCELONA -> {
                var markers = teamMarkers.barcelona
                if (markers != null) {
                    teamsMarkersDAO.insert(TeamsDBModel(0, BARCELONA, markers.jsonMember1?.modalityText, markers.jsonMember1?.companies, markers.jsonMember1?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, BARCELONA, markers.jsonMember2?.modalityText, markers.jsonMember2?.companies, markers.jsonMember2?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, BARCELONA, markers.jsonMember3?.modalityText, markers.jsonMember3?.companies, markers.jsonMember3?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, BARCELONA, markers.jsonMember4?.modalityText, markers.jsonMember4?.companies, markers.jsonMember4?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, BARCELONA, markers.jsonMember5?.modalityText, markers.jsonMember5?.companies, markers.jsonMember5?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, BARCELONA, markers.jsonMember6?.modalityText, markers.jsonMember6?.companies, markers.jsonMember6?.particulars))
                }
            }
            MALLORCA -> {
                var markers = teamMarkers.mallorca
                if (markers != null) {
                    teamsMarkersDAO.insert(TeamsDBModel(0, MALLORCA, markers.jsonMember1?.modalityText, markers.jsonMember1?.companies, markers.jsonMember1?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, MALLORCA, markers.jsonMember2?.modalityText, markers.jsonMember2?.companies, markers.jsonMember2?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, MALLORCA, markers.jsonMember3?.modalityText, markers.jsonMember3?.companies, markers.jsonMember3?.particulars))
                }
            }
            VALENCIA -> {
                var markers = teamMarkers.valencia
                if (markers != null) {
                    teamsMarkersDAO.insert(TeamsDBModel(0, VALENCIA, markers.jsonMember1?.modalityText, markers.jsonMember1?.companies, markers.jsonMember1?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, VALENCIA, markers.jsonMember2?.modalityText, markers.jsonMember2?.companies, markers.jsonMember2?.particulars))
                    teamsMarkersDAO.insert(TeamsDBModel(0, VALENCIA, markers.jsonMember3?.modalityText, markers.jsonMember3?.companies, markers.jsonMember3?.particulars))
                }
            }
        }
    }

}