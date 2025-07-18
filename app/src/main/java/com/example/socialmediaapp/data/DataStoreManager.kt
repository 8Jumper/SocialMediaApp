package com.example.socialmediaapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val NAME_KEY = stringPreferencesKey("user_name")
        val SURNAME_KEY = stringPreferencesKey("user_surname")
        val IMAGE_PATH_KEY = stringPreferencesKey("profile_picture_path")
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveSurname(surname: String) {
        context.dataStore.edit { preferences ->
            preferences[SURNAME_KEY] = surname
        }
    }

    suspend fun saveImagePath(path: String) {
        context.dataStore.edit { preferences ->
            preferences[IMAGE_PATH_KEY] = path
        }
    }

    suspend fun getName(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[NAME_KEY]
    }

    suspend fun getSurname(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[SURNAME_KEY]
    }

    suspend fun getImagePath(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[IMAGE_PATH_KEY]
    }

    suspend fun getLikedPosts(): Set<String> {
        val preferences = context.dataStore.data.first()
        return preferences[stringSetPreferencesKey("liked_posts")] ?: emptySet()
    }

    suspend fun isPostLiked(postId: Int): Boolean {
        return getLikedPosts().contains(postId.toString())
    }

    suspend fun togglePostLike(postId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[stringSetPreferencesKey("liked_posts")] ?: emptySet()
            val updated = if (current.contains(postId.toString())) {
                current - postId.toString()
            } else {
                current + postId.toString()
            }
            preferences[stringSetPreferencesKey("liked_posts")] = updated
        }
    }

}