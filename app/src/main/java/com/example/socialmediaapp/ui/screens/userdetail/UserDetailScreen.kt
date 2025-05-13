package com.example.socialmediaapp.ui.screens.userdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialmediaapp.data.model.Todo
import com.example.socialmediaapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(userId: String?, onBackClick: () -> Unit) {
    var user by remember { mutableStateOf<User?>(null) }
    var todos by remember { mutableStateOf<List<Todo>>(emptyList()) }

    LaunchedEffect(userId) {
        user = fetchUserDetails(userId)
        todos = fetchTodos(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły użytkownika") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Powrót")
                    }
                }
            )
        }
    ) {
        if (user != null) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    with(user!!) {
                        Text("Imię i nazwisko: $name")
                        Text("Nazwa użytkownika: $username")
                        Text("Email: $email")
                        Text("Telefon: $phone")
                        Text("Strona WWW: $website")
                        Text("Firma: ${companyName}")
                        Text("Adres: $street $suite, $city, $zipcode")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Zadania:")
                    }
                }
                items(todos) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = it.completed, onCheckedChange = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(it.title)
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

suspend fun fetchUserDetails(userId: String?): User? {
    return withContext(Dispatchers.IO) {
        try {
            val json = URL("https://jsonplaceholder.typicode.com/users/$userId").readText()
            val obj = JSONObject(json)
            val address = obj.getJSONObject("address")
            val company = obj.getJSONObject("company")
            User(
                name = obj.getString("name"),
                username = obj.getString("username"),
                email = obj.getString("email"),
                phone = obj.getString("phone"),
                website = obj.getString("website"),
                companyName = company.getString("name"),
                street = address.getString("street"),
                suite = address.getString("suite"),
                city = address.getString("city"),
                zipcode = address.getString("zipcode"),
                id = obj.getInt("id")
            )
        } catch (e: Exception) {
            null
        }
    }
}

suspend fun fetchTodos(userId: String?): List<Todo> {
    return withContext(Dispatchers.IO) {
        try {
            val json = URL("https://jsonplaceholder.typicode.com/todos?userId=$userId").readText()
            val array = JSONArray(json)
            (0 until array.length()).map {
                val obj = array.getJSONObject(it)
                Todo(
                    title = obj.getString("title"),
                    completed = obj.getBoolean("completed")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}