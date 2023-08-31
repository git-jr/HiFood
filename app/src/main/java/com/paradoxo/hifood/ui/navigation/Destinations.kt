package com.paradoxo.hifood.ui.navigation

import com.paradoxo.hifood.R


sealed class Destinations(val route: String, val resourceId: Pair<Int, Int>? = null) {
    object Home : Destinations("Início", Pair(R.drawable.ic_home_fill, R.drawable.ic_home_outlined))

    object Search :
        Destinations("Busca", Pair(R.drawable.ic_search_fill, R.drawable.ic_search_outlined))

    object Request :
        Destinations("Pedidos", Pair(R.drawable.ic_request_fill, R.drawable.ic_request_outlined))

    object Profile :
        Destinations("Perfil", Pair(R.drawable.ic_profile_fill, R.drawable.ic_profile_outlined))
}

val screenItems = listOf(
    Destinations.Home,
    Destinations.Search,
    Destinations.Request,
    Destinations.Profile
)