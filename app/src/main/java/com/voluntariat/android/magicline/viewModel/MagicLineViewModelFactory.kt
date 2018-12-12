package com.voluntariat.android.magicline.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voluntariat.android.magicline.data.MagicLineRepository


class MagicLineViewModelFactory(val repository: MagicLineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MagicLineViewModel(repository = repository) as T
    }
}