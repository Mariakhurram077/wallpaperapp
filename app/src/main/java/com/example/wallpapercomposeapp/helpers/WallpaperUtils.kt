package com.example.wallpapercomposeapp.helpers

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
object WallpaperUtils {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    fun downloadWallpaper(imageUrl: String?, context: Context){
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(imageUrl)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadManager.enqueue(request)
        Toast.makeText(context, "Downloading Wallpaper ..... ", Toast.LENGTH_SHORT).show()
    }
     fun shareWallpaper(bitmap: Bitmap,context: Context) {
        val wallpaperFile: File? =
            try {
                val wallpaperPath = File(context.getExternalFilesDir(null), "images")
                wallpaperPath.mkdirs()
                File(wallpaperPath, "Wallpaper.png").apply {
                    FileOutputStream(this).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }

        wallpaperFile?.let {
            val uri = FileProvider.getUriForFile(context, context.packageName, it)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            try {
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            } catch (e: java.lang.Exception) {
                //handle exception if any
            }
            val openChooser = Intent.createChooser(shareIntent, "Share Wallpaper")

            //getting uri permission
            val resInfoList = context.packageManager.queryIntentActivities(
                openChooser,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                context.grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            context.startActivity(openChooser)
        }
    }

     fun convertUrlToBitmapImage(imageUrl: String): Bitmap? {
        var bitmap: Bitmap? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(imageUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
        return bitmap
    }

    fun setWallpaperOnHomeScreen(imageUrl: String, context: Context) {
     //   progressBar?.visibility = View.VISIBLE // Show progress bar
        mainScope.launch {
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    Glide.with(context)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get()
                }
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                Toast.makeText(context, "Wallpaper set successfully on home screen", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show()
            } finally {
                //progressBar?.visibility = View.GONE // Hide progress bar
            }
        }
    }

    fun setWallpaperOnLockScreen(imageUrl: String, context: Context) {
     //   progressBar?.visibility = View.VISIBLE // Show progress bar
        mainScope.launch {
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    Glide.with(context)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get()
                }
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                Toast.makeText(context, "Wallpaper set successfully on lock screen", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to set wallpaper on lock screen", Toast.LENGTH_SHORT).show()
            } finally {
              //  progressBar?.visibility = View.GONE // Hide progress bar
            }
        }
    }

    fun setWallpaperOnBoth(imageUrl: String, context: Context) {
        //progressBar?.visibility = View.VISIBLE // Show progress bar
        mainScope.launch {
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    Glide.with(context)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get()
                }
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                withContext(Dispatchers.IO) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
                Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to set Wallpaper", Toast.LENGTH_SHORT).show()
            } finally {
             //   progressBar?.visibility = View.GONE // Hide progress bar
            }
        }
    }
}