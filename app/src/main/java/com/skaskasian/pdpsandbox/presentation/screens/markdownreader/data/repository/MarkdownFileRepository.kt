package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.data.repository

import androidx.annotation.WorkerThread
import com.skaskasian.pdpsandbox.presentation.screens.markdownreader.data.service.DownloadFileService
import java.io.File

class MarkdownFileRepository(
    private val downloadFileService: DownloadFileService = DownloadFileService()
) {

    @WorkerThread
    fun downloadFile(url: String, filename: String): Result<File> = runCatching {
        downloadFileService.downloadFile(url, filename)
    }
}