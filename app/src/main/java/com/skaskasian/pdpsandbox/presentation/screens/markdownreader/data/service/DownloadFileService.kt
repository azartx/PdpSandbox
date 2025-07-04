package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.data.service

import android.os.Environment
import android.util.Log
import androidx.annotation.WorkerThread
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadFileService {

    @WorkerThread
    fun downloadFile(url: String, filename: String): File {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            filename
        )
        val urlConnection = URL(url).openConnection()
        FileOutputStream(file).use { outputStream ->
            urlConnection.getInputStream().use { inputStream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                Log.d("FileDownloader", "Файл загружен и сохранен")
            }
        }
        return file
    }
}