package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.ui.theme.DonorCity

@Composable
fun SearchApp(
    modifier: Modifier = Modifier,
    points: List<DonorPointModel> = emptyList()
) {
    var city by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            GetCityField { city = it }
        }

        val filtered = points.filter {
            it.titleOfPoint.contains(city, ignoreCase = true) ||
                    it.addressOfPoint.contains(city, ignoreCase = true)
        }

        items(filtered) { point ->
            PointCard(
                point = point,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun GetCityField(
    onNameChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onNameChange(it)
            },
            label = { Text("Введите город") },
            singleLine = true
        )
    }

}


@Composable
fun PointCard(
    modifier: Modifier = Modifier,
    point: DonorPointModel
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp),
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
@Preview
fun SearchPrivewDark() {
    DonorCity(
        darkTheme = true
    ) {
        SearchApp(
            points = listOf(
                DonorPointModel("Пункт 1", "Казань, ул. Пушкина 1"),
                DonorPointModel("Пункт 2", "Москва, Арбат 5")
            )
        )
    }
}

@Composable
@Preview
fun SearchPrivew() {
    DonorCity {
        SearchApp(
            points = listOf(
                DonorPointModel("Пункт 1", "Казань, ул. Пушкина 1"),
                DonorPointModel("Пункт 2", "Москва, Арбат 5")
            )
        )
    }
}

