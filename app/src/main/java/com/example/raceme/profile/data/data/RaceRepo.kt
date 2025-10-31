package com.example.raceme.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RaceRepo(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun userRef() = db.collection("users").document(auth.uid!!)

    fun challengeDefs() = callbackFlow<List<ChallengeDef>> {
        val reg = db.collection("definitions").document("challenges").collection("items")
            .addSnapshotListener { s, e -> if (e==null && s!=null) trySend(s.toObjects(ChallengeDef::class.java)) }
        awaitClose { reg.remove() }
    }

    fun badgeDefs() = callbackFlow<List<BadgeDef>> {
        val reg = db.collection("definitions").document("badges").collection("items")
            .addSnapshotListener { s, e -> if (e==null && s!=null) trySend(s.toObjects(BadgeDef::class.java)) }
        awaitClose { reg.remove() }
    }

    fun runs() = callbackFlow<List<Run>> {
        val reg = userRef().collection("runs").orderBy("startAt", Query.Direction.DESCENDING)
            .addSnapshotListener { s, e -> if (e==null && s!=null) trySend(s.toObjects(Run::class.java)) }
        awaitClose { reg.remove() }
    }

    suspend fun addRun(run: Run) { userRef().collection("runs").add(run).await() }
}
