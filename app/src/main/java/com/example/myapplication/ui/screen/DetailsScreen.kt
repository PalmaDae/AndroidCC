package com.example.myapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.DonorCity

@Composable
fun DetailsApp() {

}






@Composable
@Preview
fun DetailsPreview() {
    DonorCity {
        DetailsApp()
    }
}
@Composable
@Preview
fun DetailsPreviewDark() {
    DonorCity(
        darkTheme = true
    ) {
        DetailsApp()
    }
}


