package com.paradoxo.hifood.extensions

import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.paradoxo.hifood.R


fun ImageView.tentaCarregarImagem(url: String? = null){

    val imageLoaderGif = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    load(url, imageLoaderGif) {
        fallback(R.drawable.erro)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}