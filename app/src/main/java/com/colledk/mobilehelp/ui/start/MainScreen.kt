package com.colledk.mobilehelp.ui.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colledk.mobilehelp.domain.model.User
import timber.log.Timber
import java.util.Date

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val currentUiState = viewModel.uiState.collectAsState()

    MainScreenUserSegment(
        user = currentUiState.value.currentUser,
        users = currentUiState.value.users,
        saveCurrentUser = {
            viewModel.saveUser(
                user = User(
                    name = it.name,
                    password = it.password,
                    gender = it.gender,
                    birthday = it.birthday
                )
            )
        }
    )
}

@Composable
fun MainScreenUserSegment(user: User, users: List<User>, saveCurrentUser: (newUser: User) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Current user",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        }
        item { UserDisplay(user = user, saveCurrentUser = saveCurrentUser) }
        item {
            Text(
                text = "All users in database",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        }
        items(users) { u ->
            OtherUserDisplay(user = u)
        }
    }
}

@Composable
fun UserDisplay(user: User, saveCurrentUser: (newUser: User) -> Unit) {
    var name by remember {
        mutableStateOf(user.name)
    }
    var password by remember {
        mutableStateOf(user.password)
    }
    var birthday by remember {
        mutableStateOf(user.birthday)
    }
    var gender by remember {
        mutableStateOf(user.gender)
    }

    LaunchedEffect(key1 = user){
        name = user.name
        password = user.password
        birthday = user.birthday
        gender = user.gender
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(value = name, onValueChange = { name = it }, label = { Text(text = "Name") })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") })
        Button(onClick = { birthday = Date() }) {
            Text(text = "Update birthday")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = User.Gender.MALE.name)
            Switch(checked = gender == User.Gender.FEMALE, onCheckedChange = {
                gender = when (gender) {
                    User.Gender.MALE -> {
                        User.Gender.FEMALE
                    }
                    User.Gender.FEMALE -> {
                        User.Gender.MALE
                    }
                }
            })
            Text(text = User.Gender.FEMALE.name)
        }
        Button(onClick = {
            saveCurrentUser(
                User(
                    name = name,
                    password = password,
                    gender = gender,
                    birthday = birthday
                )
            )
        }) {
            Text(text = "Save information")
        }
    }
}

@Composable
fun OtherUserDisplay(user: User) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "User name: ${user.name}")
        Text(text = "User password: ${user.password}")
        Text(text = "User gender: ${user.gender}")
        Text(text = "User birthday: ${user.birthday}")
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        MainScreenUserSegment(user = User(), users = listOf()) {

        }
    }
}