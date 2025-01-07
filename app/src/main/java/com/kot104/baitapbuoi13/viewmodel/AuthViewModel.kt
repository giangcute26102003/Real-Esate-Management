package com.kot104.baitapbuoi13.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private var resetPasswordError: String? = null

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        if(!isValidEmail(email)){
            _authState.value = AuthState.Error("Invalid email")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(getFirebaseAuthErrorMessage(task.exception))
                }
            }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(emailRegex.toRegex())
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        if(!isValidEmail(email)){
            _authState.value = AuthState.Error("Invalid email")
            return
        }
        if(password.length < 8){
            _authState.value = AuthState.Error("Password must have at least 8 characters.")
            return
        }else if (!Pattern.compile("[A-Z]").matcher(password).find()){
            _authState.value = AuthState.Error("Password must have at least 1 uppercase character.")
            return
        }  else if (!Pattern.compile("[^a-zA-Z0-9\\s]").matcher(password).find()){
            _authState.value = AuthState.Error("Password must have at least 1 special character.")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(getFirebaseAuthErrorMessage(task.exception))
                }
            }
    }
    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated


    }

    // Xử lý lỗi cụ thể từ FirebaseAuthException
    private fun getFirebaseAuthErrorMessage(exception: Exception?): String {
        return when ((exception as? FirebaseAuthException)?.errorCode) {
            "ERROR_INVALID_EMAIL" -> "The email address is badly formatted."
            "ERROR_WRONG_PASSWORD" -> "The password is incorrect. Please try again."
            "ERROR_USER_NOT_FOUND" -> "No account found with this email. Please sign up first."
            "ERROR_USER_DISABLED" -> "This account has been disabled."
            "ERROR_EMAIL_ALREADY_IN_USE" -> "The email address is already in use by another account."
            "ERROR_WEAK_PASSWORD" -> "The password is too weak. Please choose a stronger password."
            "ERROR_OPERATION_NOT_ALLOWED" -> "Email/password sign-in is disabled."
            else -> exception?.message ?: "An unknown error occurred."
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.sendPasswordResetEmail(email).await()
                _authState.value = AuthState.Unauthenticated
            } catch (e: Exception) {
                val errorCode = (e as FirebaseAuthException).errorCode
                resetPasswordError = when(errorCode){
                    "ERROR_INVALID_EMAIL" -> "Email không đúng định dạng"
                    "ERROR_USER_NOT_FOUND" -> "Tài khoản không tồn tại"
                    else -> "Khôi phục mật khẩu thất bại"
                }
                _authState.value = AuthState.Error(resetPasswordError!!)
            }
        }
    }
}

// Trạng thái xác thực
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
