package com.example.myapplication.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.viewmodel.SearchViewModel

@Composable
fun SearchApp(
    navController: NavController
) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as MyApplication).appComponent
    val vm = remember { appComponent.getSearchViewModel() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(Modifier.height(24.dp)) }

        item {
            GetCityField { cityName ->
                val slug = cityName.lowercase()
                vm.load(slug)
            }
        }

        if (vm.error != null) item { Text(vm.error ?: "") }
        if (vm.isLoading) item { Text(stringResource(R.string.loading)) }

        items(vm.points) { point ->
            PointCard(point = point, navController = navController)
        }
    }
}

@Composable
fun PointCard(
    modifier: Modifier = Modifier,
    point: DonorPointModel,
    navController: NavController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("detail/${point.id}")
            },
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
fun GetCityField(
    onEnter: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,
        contentAlignment = Alignment.Center
    ) {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.write_city)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onEnter(text) }
            )
        )
    }
}