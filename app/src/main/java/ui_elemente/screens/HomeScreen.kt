package ui_elemente.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.carsharing_app.R
import ui_elemente.navigation.Topbar
import ui_elemente.components.Category
import ui_elemente.components.CategoryCard
import ui_elemente.components.PictureCarousel
@Composable
fun HomeScreen(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {

        Topbar("Home Page", navController)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "banner"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Kategorien")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(320.dp)
        ) {
            items(Category.entries) { category ->
                CategoryCard(
                    category = category,
                    navController = navController,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Unsere Angebote")

        PictureCarousel()

        Spacer(modifier = Modifier.height(24.dp))
    }
}