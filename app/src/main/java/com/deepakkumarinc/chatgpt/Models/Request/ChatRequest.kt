package com.deepakkumarinc.chatgpt.Models.Request

data class ChatRequest(
    val max_tokens: Int,
    val model: String,
    val prompt: String,
    val temperature: Double
)