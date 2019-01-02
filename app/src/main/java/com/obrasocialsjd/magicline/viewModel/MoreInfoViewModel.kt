package com.obrasocialsjd.magicline.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.obrasocialsjd.magicline.data.MagicLineRepository
import com.obrasocialsjd.magicline.data.models.teams.TotalParticipantsDBModel


class MoreInfoViewModel(application : Application, private val repository: MagicLineRepository) : AndroidViewModel(application) {


    fun getTotalParticipants() : LiveData<TotalParticipantsDBModel> {
        return repository.getTeamsMarkers()
    }
}