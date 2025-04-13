package com.nxxr.spaces.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

sealed class AuthState {
    object Autheticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Autheticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun login(email:String, password: String){

        if(email.isBlank() || password.isBlank()){
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    _authState.value = AuthState.Autheticated
                }else{
                    _authState.value = AuthState.Error(it.exception?.message ?: "Something Went Wrong")
                }
            }
    }
    fun signUp(email:String, password: String, confirmPassword: String){

        if(email.isBlank() || password.isBlank() || password.length < 6 || confirmPassword.isBlank() || confirmPassword != password ){
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    _authState.value = AuthState.Autheticated
                }else{
                    _authState.value = AuthState.Error(it.exception?.message ?: "Something Went Wrong")
                }
            }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


}
