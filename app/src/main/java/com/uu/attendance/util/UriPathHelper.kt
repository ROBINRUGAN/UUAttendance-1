package com.uu.attendance.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object URIPathHelper {
    fun getPath(context: Context, uri: Uri): String? {
        var path = getPathFromLocalUri(context, uri)
        if (path == null) {
            path = getPathFromRemoteUri(context, uri)
        }
        return path
    }

    private fun getPathFromLocalUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null) ?: return null
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(columnIndex)
        cursor.close()
        return s
    }

    private fun getPathFromRemoteUri(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        val fileName = getFileName(contentResolver, uri) ?: "temp_file"
        var copyFile: File? = null
        try {
            copyFile = createTemporalFileFrom(contentResolver.openInputStream(uri), fileName)
            return copyFile!!.path
        } catch (e: Exception) {
            e.printStackTrace()
            if (copyFile != null && copyFile.exists()) {
                copyFile.delete()
            }
            return null
        }
    }

    private fun getFileName(contentResolver: ContentResolver?, uri: Uri?): String? {
        if (contentResolver == null || uri == null) return null
        var fileName: String? = ""
        try {
            val fileCursor =
                contentResolver.query(
                    uri!!, arrayOf(OpenableColumns.DISPLAY_NAME), null,
                    null,
                    null
                )
            if (fileCursor != null && fileCursor.moveToFirst()) {
                val cIndex =
                    fileCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                if (cIndex > -1) {
                    fileName = fileCursor.getString(cIndex)
                }
            }
            fileCursor?.close()
        } catch (e: Exception) {

            e.printStackTrace()
            fileName = ""
        }

        return fileName

    }

    @Throws(IOException::class)
    private fun createTemporalFileFrom(inputStream: InputStream?, name: String): File? {
        var targetFile: File? = null
        if (inputStream == null) return targetFile
        var read: Int = 0
        val buffer = ByteArray(8 * 1024)
        targetFile = File.createTempFile(name, ".tmp")
        FileOutputStream(targetFile).use { outputStream ->
            while (true) {
                read = inputStream.read(buffer)
                if (read == -1) {
                    break;
                }
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
        }
        return targetFile
    }
}