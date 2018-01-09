package com.pyoinsoo.kotlin.design.support

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule


/*
 * Created by pyoinsoo on 2018-01-07.
 * insoo.pyo@gmail.com
 *
 * Glide 4.4 구현
 */
@GlideModule
class GirlGroupGlideModule: AppGlideModule(){

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)

        val cacheCalculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = cacheCalculator.memoryCacheSize
        val defaultBitmapPoolSize = cacheCalculator.bitmapPoolSize

        //Memory 20% 더 사용
        val girlRAMCacheSize = (1.2 * defaultMemoryCacheSize).toLong()
        val girlBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toLong()

        builder?.setMemoryCache(LruResourceCache(girlRAMCacheSize))
        builder?.setBitmapPool(LruBitmapPool(girlBitmapPoolSize))

        /*val diskCacheSize = 1024 * 1024 * 100
        builder?.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSize))

        val downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath()
        builder?.setDiskCache(DiskLruCacheFactory(downloadDirectoryPath, "girlCache", diskCacheSize))*/
    }
}