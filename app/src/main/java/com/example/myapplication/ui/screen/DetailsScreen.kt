package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.data.model.DonorPointDetailModel
import com.example.myapplication.di.SearchViewModelFactory

@Composable
fun DetailsApp(point: DonorPointDetailModel) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(text = point.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text(text = point.address)
            Spacer(Modifier.height(12.dp))
        }

        item {
            Text(stringResource(R.string.schedule))
        }
        items(point.schedule) { s ->
            Text("${s.dow}: ${s.start} - ${s.end}")
        }

        item { Spacer(Modifier.height(12.dp)) }

        item { Spacer(Modifier.height(12.dp)) }

        item { Text(stringResource(R.string.deficit_blood)) }
        items(point.bloodStatus.filter { it.value == "need" }.keys.toList()) { bg ->
            Text(bg.uppercase())
        }

        item { Spacer(Modifier.height(12.dp)) }

        items(point.phoneNumbers) { ph ->
            Text("${stringResource(R.string.phone)}: ${ph.phone} ${ph.comment}")
        }

        item {
            point.site?.let { Text("${stringResource(R.string.site)}: $it") }
            point.email?.let { Text("${stringResource(R.string.email)}: $it") }
            point.parserUrl?.let { Text("${stringResource(R.string.source)}: $it") }
        }
    }
}

@Composable
fun DetailScreen(
    pointId: Int
) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as MyApplication).appComponent
    val factory = appComponent.getSearchViewModelFactory()

    val viewModel = remember(pointId) {
        factory.createForDetail(pointId)
    }

    val pointDetail = viewModel.currentPointDetail

    if (pointDetail == null) {
        Text(stringResource(R.string.loading))
    } else {
        DetailsApp(point = pointDetail)
    }
}