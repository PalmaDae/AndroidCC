package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.ui.theme.DonorCity

@Composable
fun SearchApp(
    modifier: Modifier = Modifier
) {
    PointCard(
        modifier = modifier,
        point = DonorPointModel(
            "РЦК",
            "ул Студенческая 8б"
        )
    )
}


@Composable
@Preview
fun SearchPrivew() {
    DonorCity {
        SearchApp()
    }
}

@Composable
fun PointCard(
    modifier: Modifier = Modifier,
    point: DonorPointModel
) {
    Card(
        modifier = modifier.
        size(width = (200.dp), height = 45.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(point.titleOfPoint)
                Text(point.addressOfPoint)
            }
        }
    }
}





@Composable
@Preview
fun SearchPrivewDark() {
    DonorCity(
        darkTheme = true
    ) {
        SearchApp()
    }
}

