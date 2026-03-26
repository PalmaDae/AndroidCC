package com.example.myapplication.data.model

data class DonorResponse(
    val count: Int,
    val results: List<DonorPointModel>
)

data class DonorPointDto(
    val title: String,
    val address: String
) {
    fun toModel() = DonorPointModel(titleOfPoint = title, addressOfPoint = address)
}