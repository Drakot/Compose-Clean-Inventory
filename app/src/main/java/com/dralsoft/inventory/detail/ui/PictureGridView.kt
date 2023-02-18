package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PictureGridView(pictures: List<String>) {
    val context = LocalContext.current

    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp), content = {
        items(items = pictures) { hero ->
            ItemPicture(hero) {
                //TODO mostrar para seleccionar galeria o camara en bottomsheet
            }
        }
    }, contentPadding = PaddingValues(8.dp))

}

@Composable
fun ItemPicture(
    picture: String, modifier: Modifier = Modifier
        .width(200.dp), onItemSelected: (String) -> Unit
) {
    Card(border = BorderStroke(2.dp, Color.Black), modifier = modifier
        .clickable {
            onItemSelected(picture)
        }) {
        Column(modifier = Modifier.padding(0.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                model = picture,
                contentDescription = "Translated description of what the image contains"
            )
        }
    }
}