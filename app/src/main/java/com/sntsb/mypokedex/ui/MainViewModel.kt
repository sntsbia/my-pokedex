package com.sntsb.mypokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _texto = MutableLiveData("Olá, mundo!")
    val texto: LiveData<String> = _texto

}