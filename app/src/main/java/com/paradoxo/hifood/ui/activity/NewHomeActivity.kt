package com.paradoxo.hifood.ui.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.paradoxo.hifood.R
import com.paradoxo.hifood.model.Store
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.Montserrat
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates

class NewHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val inTest = true
            if (inTest) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainScreen()
                }
            } else {
                PalletColorScreen()
            }
        }
    }


    @Composable
    private fun PalletColorScreen() {
        val defaultBackgroundColor = MaterialTheme.colorScheme.background
        val backgroundColor = remember { Animatable(defaultBackgroundColor) }

        HiFoodTheme(colorType = backgroundColor.value) {
            Box(
                Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                val context = LocalContext.current
                val drawbleList = listOf(
                    R.drawable.banner_hifood_vermelho,
                    R.drawable.banner_hifood_roxo,
                    R.drawable.banner_hifood_verde,
                    R.drawable.banner_hifood_amarelo,
                    R.drawable.banner_hifood_violeta
                )
                val bitmapsList = remember {
                    drawbleList.map { BitmapFactory.decodeResource(context.resources, it) }
                }

                val palletList =
                    remember { bitmapsList.map { Palette.from(it).generate() } }


                val listOfColorsList: List<List<Pair<Color, String>>> = remember {
                    palletList.map { pallete ->
                        val colorList: List<Pair<Color, String>> =
                            palleteToColorList(pallete)
                        colorList
                    }
                }

                var visibleItem by remember { mutableIntStateOf(0) }

                val lazyColumState = rememberLazyListState()

                LaunchedEffect(lazyColumState) {
                    snapshotFlow { lazyColumState.firstVisibleItemScrollOffset }
                        .collect {
                            Log.d("TAG", "onCreate: ${lazyColumState.firstVisibleItemIndex}")
                            visibleItem = lazyColumState.firstVisibleItemIndex
                        }
                }

                val currentColorList = listOfColorsList[visibleItem]

                LaunchedEffect(currentColorList) {
                    backgroundColor.animateTo(currentColorList[5].first.copy(alpha = 0.5f))
                }

                Column {
                    LazyRow(
                        state = lazyColumState
                    ) {
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        items(drawbleList) { drawble ->
                            Box(
                                Modifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(drawble),
                                    contentDescription = "HiFood Logo",
                                    modifier = Modifier
                                        .height(150.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }

                    Crossfade(
                        targetState = currentColorList,
                        label = "chage color list",
                        animationSpec = tween(500)
                    ) { currentColorListState ->
                        LazyColumn(
                            modifier = Modifier
                                .defaultMinSize(minHeight = 200.dp)
                                .fillMaxWidth()
                                .background(color = Color.Red),
                            content = {
                                items(currentColorListState) { color ->
                                    ColorItem(color = color.first, colorName = color.second)
                                }
                            })
                    }
                }
            }


            Row(
                Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceAround,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home_fill),
                    contentDescription = "ic_home_fill",
                    tint = backgroundColor.value,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(48.dp),
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_home_outlined),
                    contentDescription = "ic_home_outlined",
                    tint = backgroundColor.value,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(48.dp),
                )
            }
        }
    }
}

private fun palleteToColorList(palette: Palette): List<Pair<Color, String>> {

    val properties = listOf(
        "lightVibrantSwatch",
        "darkVibrantSwatch",
        "lightMutedSwatch",
        "darkMutedSwatch",
        "mutedSwatch",
        "vibrantSwatch"
    )


    val colorList = properties.map {
        val swatch = when (it) {
            "lightVibrantSwatch" -> palette.lightVibrantSwatch
            "darkVibrantSwatch" -> palette.darkVibrantSwatch
            "lightMutedSwatch" -> palette.lightMutedSwatch
            "darkMutedSwatch" -> palette.darkMutedSwatch
            "mutedSwatch" -> palette.mutedSwatch
            "vibrantSwatch" -> palette.vibrantSwatch
            else -> null
        }

        Pair(swatch?.let { currentColor -> Color(currentColor.rgb) }
            ?: Color.Black, it)
    }.toMutableList()
    return colorList
}


@Composable
fun ColorItem(color: Color, colorName: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(
                color = color
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = colorName,
            color = if (colorName == "darkMutedSwatch") Color.White else Color.Black
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MainScreen() {
    val defaultBackgroundColor = MaterialTheme.colorScheme.background
    val backgroundColor = remember { Animatable(defaultBackgroundColor) }

    HiFoodTheme(colorType = backgroundColor.value) {
        Scaffold(
            bottomBar = {}
//            topBar = {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
//                        .safeDrawingPadding(),
//                    contentAlignment = Alignment.CenterEnd
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Text(
//                            text = "R.Teste, 123",
//                            fontSize = 16.sp,
//                            fontFamily = MontserratAlternates,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Icon(
//                            imageVector = Icons.Default.KeyboardArrowDown,
//                            contentDescription = "Expandir lista de endereções",
//                            tint = Color.Red,
//                        )
//                    }
//
//                    Box {
//                        Box(
//                            modifier = Modifier
//                                .size(28.dp),
//                            contentAlignment = Alignment.Center,
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Notifications,
//                                contentDescription = "Expandir lista de endereções",
//                                tint = Color.Red
//                            )
//                        }
//                        Box(
//                            modifier = Modifier
//                                .size(28.dp),
//                            contentAlignment = Alignment.TopEnd,
//                        ) {
//                            Badge {
//                                Text(text = "1")
//                            }
//                        }
//                    }
//                }
//            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues = paddingValues,
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "R.Teste, 123",
                            fontSize = 16.sp,
                            fontFamily = MontserratAlternates,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expandir lista de endereções",
                            tint = Color.Red,
                        )
                    }

                    Box {
                        Box(
                            modifier = Modifier
                                .size(28.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Expandir lista de endereções",
                                tint = Color.Red
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(28.dp),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            Badge {
                                Text(text = "1")
                            }
                        }
                    }
                }

                Box {
                    val context = LocalContext.current
                    val drawbleList = listOf(
                        R.drawable.banner_hifood_vermelho,
                        R.drawable.banner_hifood_roxo,
                        R.drawable.banner_hifood_verde,
                        R.drawable.banner_hifood_amarelo,
                        R.drawable.banner_hifood_violeta
                    )
                    val bitmapsList = remember {
                        drawbleList.map { BitmapFactory.decodeResource(context.resources, it) }
                    }

                    val palletList = remember { bitmapsList.map { Palette.from(it).generate() } }


                    val listOfColorsList: List<List<Pair<Color, String>>> = remember {
                        palletList.map { pallete ->
                            val colorList: List<Pair<Color, String>> =
                                palleteToColorList(pallete)
                            colorList
                        }
                    }

                    var visibleItemPager by remember { mutableIntStateOf(0) }
                    val currentColorList = listOfColorsList[visibleItemPager]

                    LaunchedEffect(currentColorList) {
                        backgroundColor.animateTo(currentColorList[5].first.copy(alpha = 0.5f))
                    }
                    val pageCount = drawbleList.size
                    val pagerState = rememberPagerState(pageCount = {
                        drawbleList.size
                    })

                    LaunchedEffect(pagerState.currentPage) {
                        visibleItemPager = pagerState.currentPage
                    }

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth(),
                    ) {
                        // ContentTypes(Modifier.padding(vertical = 16.dp))
                        GridContentTypes(Modifier.padding(8.dp))

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            pageSize = PageSize.Fixed(350.dp),
                        ) { page ->
                            Box {
                                Image(
                                    painter = painterResource(drawbleList[page]),
                                    contentDescription = "banner",
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .height(150.dp),
                                )
                            }
                        }

                        Row(
                            Modifier
                                .height(20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            repeat(pageCount) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val stores = listOf(
                            Store(name = "Andréa Doces e Salgados", image = R.drawable.produto_1),
                            Store(name = "Burger Rei", image = R.drawable.produto_1),
                            Store(name = "Mc Dugooes", image = R.drawable.produto_1),
                            Store(name = "StarButs", image = R.drawable.produto_1),
                            Store(name = "Sanches", image = R.drawable.produto_1),
                        )

                        // Titulo Cupom e entrega gratis
                        TextMoreContainer("Cupom e entrega gratis")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        ) { StoreList(stores.shuffled()) }


                        // ultimas lojas
                        TextMoreContainer("Ultimas lojas")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                        ) { StoreList(stores.shuffled()) }


//                        Crossfade(
//                            targetState = currentColorList,
//                            label = "chage color list",
//                            animationSpec = tween(500)
//                        ) { currentColorListState ->
//                            LazyColumn(
//                                modifier = Modifier
//                                    .defaultMinSize(minHeight = 200.dp)
//                                    .fillMaxWidth()
//                                    .background(color = Color.Red),
//                                content = {
//                                    items(currentColorListState) { color ->
//                                        ColorItem(color = color.first, colorName = color.second)
//                                    }
//                                })
//                        }

                    }
                }
            }
        }
    }
}

@Composable
fun GridContentTypes(modifier: Modifier = Modifier) {
    val types = listOf(
        Pair("Restaurantes", R.drawable.produto_1),
        Pair("Mercado", R.drawable.produto_1),
        Pair("Bebidas", R.drawable.produto_1),
        Pair("Shopping", R.drawable.produto_1),
    )

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(types) { item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(
                        color = Color(239, 44, 44, 255),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = item.first,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold,
                )

                Image(
                    painterResource(id = item.second),
                    contentDescription = "Imagem ${item.first}",
                    modifier = Modifier
                        .size(36.dp),
                )
            }
        }
    }
}

@Composable
private fun StoreList(stores: List<Store>) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(stores) { store ->
            Column(
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(store.image),
                    contentDescription = store.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = store.name,
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontFamily = MontserratAlternates,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun TextMoreContainer(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Ver mais",
            fontSize = 14.sp,
            fontFamily = MontserratAlternates,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun ContentTypes(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//                    color = Color(244, 244, 244, 255),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Restaurantes",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painterResource(id = R.drawable.produto_1),
                contentDescription = "Imagem comidas",
                modifier = Modifier
                    .size(42.dp),
            )
        }

        Column(
            modifier = Modifier
                .background(
                    color = Color(244, 244, 244, 255),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(id = R.drawable.produto_1),
                contentDescription = "Imagem bebidas",
                modifier = Modifier
                    .size(42.dp),
            )

            Text(
                text = "Bebidas",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Bold,
            )

        }

        Column(
            modifier = Modifier
                .background(
                    color = Color(244, 244, 244, 255),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(id = R.drawable.produto_1),
                contentDescription = "Imagem itens de compras",
                modifier = Modifier
                    .size(42.dp),
            )

            Text(
                text = "Shopping",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Bold,
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}