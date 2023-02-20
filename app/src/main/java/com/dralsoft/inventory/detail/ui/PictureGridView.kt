package com.dralsoft.inventory.detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun PictureGridView(
    pictures: List<String> = arrayListOf(),
    onItemSelected: (String) -> Unit,
    onRemovePicture: (String) -> Unit
) {
    val context = LocalContext.current
//  //TODO mostrar para seleccionar galeria o camara en bottomsheet
    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp), content = {
        item {
            AddPictureItem(onItemSelected)
        }
        items(items = pictures) { hero ->
            ItemPicture(hero, onItemSelected, onRemovePicture)
        }

    }, contentPadding = PaddingValues(end = 8.dp))
}

@Composable
fun AddPictureItem(onItemSelected: (String) -> Unit) {
    Card(border = BorderStroke(1.dp, Color.LightGray), modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .padding(end = 8.dp)
        .clickable {
            onItemSelected("")
        }) {
        Box(modifier = Modifier.padding(0.dp)) {
            Icon(
                imageVector = Icons.Filled.AddAPhoto,
                tint =MaterialTheme.colors.primaryVariant,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}

@Composable
fun ItemPicture(
    picture: String, onItemSelected: (String) -> Unit, onRemovePicture: (String) -> Unit
) {
    Card(border = BorderStroke(1.dp, Color.LightGray), modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .padding(end = 8.dp)
        .clickable {
            onItemSelected(picture)
        }) {
        Box(modifier = Modifier.padding(0.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,

                model = picture,
                contentDescription = "Translated description of what the image contains"
            )

            Icon(
                imageVector = Icons.Filled.RemoveCircle,
                contentDescription = "",
                tint =MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .clickable {
                        onRemovePicture(picture)
                    },

            )
        }
    }
}