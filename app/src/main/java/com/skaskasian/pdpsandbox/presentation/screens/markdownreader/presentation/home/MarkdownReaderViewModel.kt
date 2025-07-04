package com.skaskasian.pdpsandbox.presentation.screens.markdownreader.presentation.home

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.webkit.URLUtil
import com.skaskasian.pdpsandbox.presentation.screens.markdownreader.data.repository.MarkdownFileRepository
import com.skaskasian.pdpsandbox.presentation.screens.markdownreader.utils.UiStateObserver
import com.skaskasian.pdpsandbox.presentation.screens.markdownreader.utils.SimpleViewModel
import java.io.File

class MarkdownReaderViewModel(
    private val repository: MarkdownFileRepository = MarkdownFileRepository()
) : SimpleViewModel<HomeState, HomeErrorState> {

    private var state = HomeState()

    private var currentDownloadingThread: Thread? = null

    private var observer: UiStateObserver<HomeState, HomeErrorState>? = null

    override fun subscribeOnState(observer: UiStateObserver<HomeState, HomeErrorState>) {
        this.observer = observer
        observer.onChanged(state)
    }

    fun onDownloadFileClick(url: String, mainLooper: Looper) {
        if (!URLUtil.isValidUrl(url)) {
            observer?.onError(HomeErrorState.IncorrectUrlError)
            return
        }

        observer?.onChanged(state.copy(isLoading = true))

        currentDownloadingThread = Thread {
            val result = repository.downloadFile(url, url.substringAfterLast('/'))
            Handler(mainLooper).post { handleDownloadFileResult(result) }
        }.apply { start() }
    }

    private fun handleDownloadFileResult(result: Result<File>) {
        result
            .onSuccess { downloadedFile ->
                observer?.onChanged(state.copy(files = state.files + downloadedFile.path))
            }
            .onFailure { exception ->
                observer?.onError(HomeErrorState.DownloadError)
            }
    }

    override fun unsubscribe() {
        observer = null
        if (currentDownloadingThread?.isAlive == true) {
            currentDownloadingThread?.interrupt()
            currentDownloadingThread = null
        }
    }
}