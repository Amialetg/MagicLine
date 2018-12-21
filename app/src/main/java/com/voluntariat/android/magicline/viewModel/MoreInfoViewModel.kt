package com.voluntariat.android.magicline.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.voluntariat.android.magicline.data.MagicLineRepository
import com.voluntariat.android.magicline.data.models.teams.TotalParticipantsDBModel


class MoreInfoViewModel(application : Application, private val repository: MagicLineRepository) : AndroidViewModel(application) {


    fun getTotalParticipants() : LiveData<TotalParticipantsDBModel> {
        return repository.getTeamsMarkers()
    }
}