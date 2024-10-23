package com.nicomahnic.sharecsv

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import java.io.File


object AndroidShareFile {

    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun shareFile(): Int {
        val data = """
            Index,Customer Id,First Name,Last Name,Company,City,Country,Phone 1,Phone 2,Email,Subscription Date,Website
            1,DD37Cf93aecA6Dc,Sheryl,Baxter,Rasmussen Group,East Leonard,Chile,229.077.5154,397.884.0519x718,zunigavanessa@smith.info,2020-08-24,http://www.stephenson.com/
            2,1Ef7b82A4CAAD10,Preston,Lozano,Vega-Gentry,East Jimmychester,Djibouti,5153435776,686-620-1820x944,vmata@colon.com,2021-04-23,http://www.hobbs.com/
            3,6F94879bDAfE5a6,Roy,Berry,Murillo-Perry,Isabelborough,Antigua and Barbuda,+1-539-402-0259,(496)978-3969x58947,beckycarr@hogan.com,2020-03-25,http://www.lawrence.com/
            4,5Cef8BFA16c5e3c,Linda,Olsen,"Dominguez, Mcmillan and Donovan",Bensonview,Dominican Republic,001-808-617-6467x12895,+1-813-324-8756,stanleyblackwell@benson.org,2020-06-02,http://www.good-lyons.com/
            5,053d585Ab6b3159,Joanna,Bender,"Martin, Lang and Andrade",West Priscilla,Slovakia (Slovak Republic),001-234-203-0635x76146,001-199-446-3860x3486,colinalvarado@miles.net,2021-04-17,https://goodwin-ingram.com/
        """.trimIndent()
        val file2 = createFile(data)

        try {
            (Uri.fromFile(file2)).also { println("NAMG: uri $it") }
            (Uri.fromFile(file2)).also { println("NAMG: uri $it") }
            (Uri.fromFile(file2)).also { println("NAMG: uri $it") }

            val file3 = File(application.filesDir, "prueba_rowa.csv")
            val uri3 = getUriForFile(application, "com.nicomahnic.sharecsv.provider", file3)

            println("NAMG: uri3: $uri3")

            val shareIntent = Intent()
            shareIntent.setAction(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri3)
            shareIntent.setType("file/csv")
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            application.startActivity(shareIntent)
        } catch (e: Exception) {
            println("NAMG: exception ${e.message}")
            e.printStackTrace()
        }
        return 1
    }

    private fun createFile(data: String): File {
        val csvFile = File(application.filesDir, "prueba_rowa.csv")
        csvFile.createNewFile()
        csvFile.writeText(data)
        return csvFile
    }

}

actual fun shareFile(): Int = AndroidShareFile.shareFile()