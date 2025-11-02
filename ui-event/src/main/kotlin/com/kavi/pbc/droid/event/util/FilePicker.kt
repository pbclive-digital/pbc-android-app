package com.kavi.pbc.droid.event.util

import android.app.Activity
import android.net.Uri
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream

object FilePickerUtil {
    fun handleOpenDocument(activity: Activity?, contentUri: Uri?): OpenFileResult {
        return if (contentUri != null) {
            val stream =
                try {
                    activity?.application?.contentResolver?.openInputStream(contentUri)
                } catch (exception: FileNotFoundException) {
                    return OpenFileResult.ErrorOpeningFile
                }

            if (stream != null) {
                val file = File(activity?.cacheDir, "cacheFileAppeal.csv")
                copyStreamToFile(stream, file)
                OpenFileResult.FileWasOpened(file)
            } else OpenFileResult.ErrorOpeningFile
        } else {
            OpenFileResult.ErrorOpeningFile
        }
    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}

sealed class OpenFileResult {
    object OpenFileWasCancelled : OpenFileResult()
    data class FileWasOpened(val file: File) : OpenFileResult()
    object ErrorOpeningFile : OpenFileResult()
    object DifferentResult : OpenFileResult()
}