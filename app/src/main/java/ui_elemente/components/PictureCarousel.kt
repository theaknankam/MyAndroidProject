package ui_elemente.components

import androidx.compose.runtime.Composable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.carsharing_app.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun PictureCarousel() {
    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val name: String,
//        val price: String
    )

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.car1, "Mazda"),
            CarouselItem(1, R.drawable.car2, "Range Rover"),
            CarouselItem(2, R.drawable.car3, "Renault"),
            CarouselItem(3, R.drawable.car4,"Umzug"),
            CarouselItem(4, R.drawable.car5, "Mazda2"),
        )
    }
    HorizontalMultiBrowseCarousel(
    state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
            ) { i ->

        val item = items[i]

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column{
                Image(
                    painter = painterResource(item.imageResId),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

