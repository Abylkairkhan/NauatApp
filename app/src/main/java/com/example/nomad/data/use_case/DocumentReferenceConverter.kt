package com.example.nomad.data.use_case

import androidx.room.TypeConverter
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object DocumentReferenceConverter {

    @TypeConverter
    fun documentReferenceToString(documentReference: DocumentReference): String {
        return documentReference.path
    }

    @TypeConverter
    fun stringToDocumentReference(path: String): DocumentReference {
        return FirebaseFirestore.getInstance().document(path)
    }

}