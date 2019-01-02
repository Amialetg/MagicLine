package com.voluntariat.android.magicline.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.voluntariat.android.magicline.data.MagicLineRepository
import com.voluntariat.android.magicline.data.models.donations.DonationsDBModel
import com.voluntariat.android.magicline.data.models.posts.PostsItem


class MagicLineViewModel(application : Application, private val repository: MagicLineRepository) : AndroidViewModel(application) {

    fun getPosts(lang : String) : LiveData<List<PostsItem>> {
        return repository.getPosts(lang)
    }

    fun getDonations() : LiveData<DonationsDBModel> {
        return repository.getDonations()
    }
}