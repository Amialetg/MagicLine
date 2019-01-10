package com.obrasocialsjd.magicline.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.obrasocialsjd.magicline.data.MagicLineRepository
import com.obrasocialsjd.magicline.data.models.donations.DonationsDBModel
import com.obrasocialsjd.magicline.data.models.posts.PostsItem
import com.obrasocialsjd.magicline.data.models.teams.TotalParticipantsDBModel


class MagicLineViewModel(application : Application, private val repository: MagicLineRepository) : AndroidViewModel(application) {

    fun getPosts(lang : String) : LiveData<List<PostsItem>> {
        return repository.getPosts(lang)
    }

    fun getDonations() : LiveData<DonationsDBModel> {
        return repository.getDonations()
    }

    fun getTotalParticipants() : LiveData<TotalParticipantsDBModel> {
        return repository.getTeamsMarkers()
    }
}