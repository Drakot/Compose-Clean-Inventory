package com.dralsoft.inventory.detail.ui

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun PictureGridView(
    pictures: List<Uri> = arrayListOf(),
    onItemSelected: (Uri) -> Unit,
    onRemovePicture: (Uri) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp), content = {
        item {
            AddPictureItem(onItemSelected)
        }
        items(items = pictures) { pic ->
            ItemPicture(pic, onItemSelected, onRemovePicture)
        }

    }, contentPadding = PaddingValues(end = 8.dp))
}

@Composable
fun AddPictureItem(onItemSelected: (Uri) -> Unit) {
    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(20.dp), modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(end = 8.dp, top = 8.dp)

    ) {
        Box(modifier = Modifier
            .padding(0.dp)
            .clickable {
                onItemSelected(Uri.EMPTY)
            }) {
            Icon(
                imageVector = Icons.Filled.AddAPhoto,
                tint = MaterialTheme.colors.primaryVariant,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }
    }
}

@Composable
fun ItemPicture(
    picture: Uri, onItemSelected: (Uri) -> Unit, onRemovePicture: (Uri) -> Unit
) {
    Card(
        border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(20.dp), modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(end = 8.dp, top = 8.dp)
    ) {
        Box(modifier = Modifier
            .padding(0.dp)
            .clickable {
                onItemSelected(picture)
            }) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,

                model = picture,
                contentDescription = "Translated description of what the image contains"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.35f))
            ) {

            }

            Icon(
                imageVector = Icons.Outlined.RemoveCircleOutline,
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        onRemovePicture(picture)
                    },

                )
        }
    }
}