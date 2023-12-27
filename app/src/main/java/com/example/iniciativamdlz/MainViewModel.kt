package com.example.iniciativamdlz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _navigateTo = MutableLiveData<Action>()
    val navigateTo: LiveData<Action> get() =  _navigateTo

    sealed class Action {
        class EncuestaSatisfactionAction:Action()
        class CifrasControlAction: Action()
        class ReporteIncidentesAction: Action()
    }
}