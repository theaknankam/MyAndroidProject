package ui_elemente.components

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getUserName(uid: String): String {
    return try {
        val doc = FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .await()
        doc.getString("name") ?: "Unknown Driver"
    } catch (e: Exception) {
        "Unknown Driver"
    }
}