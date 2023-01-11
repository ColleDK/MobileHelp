package com.colledk.mobilehelp.data.remote

import com.colledk.mobilehelp.data.mapToDomain
import com.colledk.mobilehelp.data.remote.model.UserRemote
import com.colledk.mobilehelp.data.toHashMap
import com.colledk.mobilehelp.data.toUserRemote
import com.colledk.mobilehelp.domain.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
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

    suspend fun getUser(id: String): UserRemote = withContext(Dispatchers.IO){
        val db = Firebase.firestore

        val user = db.collection("users").document(id).get().await().data?.toMap()?.toUserRemote() ?: UserRemote()

        return@withContext user
    }

    suspend fun getUsers(): List<UserRemote> = withContext(Dispatchers.IO) {
        val db = Firebase.firestore

        val users = mutableListOf<UserRemote>()

        db.collection("users").get().await().documents.forEach {
            users.add(it.data?.toMap()?.toUserRemote() ?: UserRemote())
        }

        return@withContext users
    }

    suspend fun listenToUsersFlow(): Flow<List<UserRemote>> = callbackFlow {
        val db = Firebase.firestore

        val collection = db.collection("users")

        val subscription = collection.addSnapshotListener { snapshot, error ->
            val result = when(snapshot){
                null -> {
                    listOf<UserRemote>()
                }
                else -> {
                    val users = mutableListOf<UserRemote>()
                    snapshot.documents.forEach {
                        users.add(it.data?.toMap()?.toUserRemote() ?: UserRemote())
                    }
                    users
                }
            }
            trySend(
                result
            )
        }

        awaitClose { subscription.remove() }
    }
}