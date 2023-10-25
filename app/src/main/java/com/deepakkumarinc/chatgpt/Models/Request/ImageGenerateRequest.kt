package com.deepakkumarinc.chatgpt.Models.Request

data class ImageGenerateRequest(
    val n: Int,
    val prompt: String,
    val size: String
)