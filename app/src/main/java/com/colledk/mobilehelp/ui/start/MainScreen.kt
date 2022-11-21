package com.colledk.mobilehelp.ui.start

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colledk.mobilehelp.domain.model.User
import java.util.Date

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val currentUiState = viewModel.uiState.collectAsState()

    MainScreenUserSegment(
        user = currentUiState.value.currentUser,
        saveCurrentUser = {
            viewModel.saveUser(
                user = User(
                    name = "Karl",
                    password = "something",
                    gender = User.Gender.FEMALE,
                    birthday = Date()
                )
            )
        }
    )
}

@Composable
fun MainScreenUserSegment(user: User, saveCurrentUser: () -> Unit) {
    Box(modifier = Modifier
        .width(350.dp)
        .background(color = MaterialTheme.colors.onPrimary, shape = RoundedCornerShape(20.dp))){
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "User name: ${user.name}")
            Text(text = "User password: ${user.password}")
            Text(text = "User gender: ${user.gender}")
            Text(text = "User birthday: ${user.birthday}")
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = saveCurrentUser) {
                Text(text = "Save user info")
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        MainScreenUserSegment(user = User()) {

        }
    }
}