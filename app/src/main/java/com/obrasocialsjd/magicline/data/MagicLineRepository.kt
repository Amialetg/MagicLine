package com.obrasocialsjd.magicline.data

import androidx.lifecycle.LiveData
import com.obrasocialsjd.magicline.data.models.donations.DonationsDBModel
import com.obrasocialsjd.magicline.data.models.posts.PostsItem
import com.obrasocialsjd.magicline.data.models.teams.TotalParticipantsDBModel

interface MagicLineRepository {

    fun authenticate(
            username: String,
            password: String,
            onResult: (loginResult: Result<String>) -> Unit
    )

    fun getPosts(lang: String) : LiveData<List<PostsItem>>

    fun getDonations() : LiveData<DonationsDBModel>

    fun getTeamsMarkers() : LiveData<TotalParticipantsDBModel>
}
