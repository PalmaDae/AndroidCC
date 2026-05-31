package com.example.myapplication.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.viewmodel.SearchViewModel

@Composable
fun SearchApp(navController: NavController) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as MyApplication).appComponent

    val vm: SearchViewModel = viewModel(
        key = "SearchViewModel",
        initializer = { appComponent.getSearchViewModel() }
    )

    val uiState: SearchUiState by vm.uiState.collectAsState(initial = SearchUiState())

    SearchScreenContent(
        uiState = uiState,
        onSearch = { cityName -> vm.load(cityName.lowercase()) },
        onPointClick = { pointId -> navController.navigate("detail/$pointId") }
    )
}

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    onSearch: (String) -> Unit,
    onPointClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(Modifier.height(24.dp)) }

        item {
            GetCityField(onEnter = onSearch)
        }

        if (uiState.error != null) {
            item { Text(uiState.error) }
        }

        if (uiState.isLoading) {
            item { Text(stringResource(R.string.loading)) }
        }

        items(items = uiState.points, key = { it.id }) { point ->
            PointCard(point = point, onClick = { onPointClick(point.id) })
        }
    }
}

@Composable
fun PointCard(
    modifier: Modifier = Modifier,
    point: DonorPointModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(point.titleOfPoint)
            Text(point.addressOfPoint)
        }
    }
}

@Composable
fun GetCityField(onEnter: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.write_city)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onEnter(text) })
        )
    }
}