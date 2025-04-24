package com.nxxr.spaces.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

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

    fun currentUserId() = auth.currentUser?.uid.toString()

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

    fun signUp(
        username: String,
        email:String,
        password: String,
        confirmPassword: String){

        if(email.isBlank() || password.isBlank() || password.length < 6 || confirmPassword.isBlank() || confirmPassword != password ){
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    val user = auth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _authState.value = AuthState.Autheticated
                            } else {
                                _authState.value = AuthState.Error(updateTask.exception?.message ?: "Profile update failed")
                            }
                        }
                } else {
                    _authState.value = AuthState.Error(it.exception?.message ?: "Signup failed")
                }
            }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun getCurrentUserDetails(): List<String>{
        val name = auth.currentUser?.displayName.toString()
        val email = auth.currentUser?.email.toString()
        val id = auth.currentUser?.uid.toString()

        return listOf(name, email, id)
    }


}
