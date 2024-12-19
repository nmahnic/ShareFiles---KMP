package com.nicomahnic.sharecsv

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import java.io.File


object AndroidShareFile {

    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    private const val WHATSAPP_BASE_URL = "http://api.whatsapp.com/send"
    private const val WHATSAPP_PHONE_PARAM = "?phone="
    private const val WHATSAPP_TEXT_PARAM = "&text="

    fun openWhatsapp(data: String, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
            val url = buildWhatsappUri(phoneNumber, data)
            intent.data = Uri.parse(url)
            application.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildWhatsappUri(phone: String, message: String) =
        WHATSAPP_BASE_URL + WHATSAPP_PHONE_PARAM + phone + WHATSAPP_TEXT_PARAM + message

    fun shareFile(data: String, name: String): Int {
        val file2 = createFile(data, name)

        try {
            (Uri.fromFile(file2)).also { println("NAMG: uri $it") }
            (Uri.fromFile(file2)).also { println("NAMG: uri $it") }
            (Uri.fromFile(file2)).also { println("NAMG: uri $it") }

            val file3 = File(application.filesDir, name)
            val uri3 = getUriForFile(application, "com.nicomahnic.sharecsv.provider", file3)

            println("NAMG: uri3: $uri3")

            val shareIntent = Intent()
            shareIntent.setAction(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri3)
            shareIntent.setType("text/csv")
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val chooserIntent = Intent.createChooser(shareIntent, null)
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            application.startActivity(chooserIntent)
        } catch (e: Exception) {
            println("NAMG: exception ${e.message}")
            e.printStackTrace()
        }
        return 1
    }

    private fun createFile(data: String, name: String): File {
        val csvFile = File(application.filesDir, name)
        csvFile.createNewFile()
        csvFile.writeText(data)
        return csvFile
    }

}

actual fun shareFile(data: String, name: String): Int = AndroidShareFile.shareFile(data, name)

actual fun openWhatsapp(data: String, phoneNumber: String) = AndroidShareFile.openWhatsapp(data, phoneNumber)