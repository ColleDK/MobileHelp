package com.colledk.mobilehelp.data.remote

import com.colledk.mobilehelp.data.remote.model.UserRemote
import com.colledk.mobilehelp.data.toHashMap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserDatabaseRemote {

    suspend fun storeUser(user: UserRemote) = withContext(Dispatchers.IO){
        val db = Firebase.firestore

        db.collection("users")
            .add(user.toHashMap())
            .addOnSuccessListener {
                Timber.d("Success writing document ${it.id}")
            }
            .addOnFailureListener {
                Timber.e("Error writing document $it")
            }
            .addOnCompleteListener {
                Timber.w("Completed writing document ${it.isSuccessful}")
            }

    }
}