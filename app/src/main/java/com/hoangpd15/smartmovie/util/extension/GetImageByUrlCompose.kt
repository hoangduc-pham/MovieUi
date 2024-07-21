package com.hoangpd15.smartmovie.util.extension

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun MyImageComponent(url: String, contentDescription: String) {
    val painter = rememberImagePainter(
        data = url
    )

    androidx.compose.foundation.Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .height(200.dp)
            .width(140.dp),
        contentScale = ContentScale.Crop
    )
}