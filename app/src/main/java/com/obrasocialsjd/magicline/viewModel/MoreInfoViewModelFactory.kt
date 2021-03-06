package com.obrasocialsjd.magicline.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obrasocialsjd.magicline.data.MagicLineRepository


class MoreInfoViewModelFactory(val application: Application, val repository: MagicLineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoreInfoViewModel(application = application, repository = repository) as T
    }
}