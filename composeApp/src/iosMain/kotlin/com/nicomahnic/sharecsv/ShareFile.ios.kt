package com.nicomahnic.sharecsv

import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Foundation.writeToURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

object IOSShareFile {
    fun shareFile(): Int {
        val data = """
            Index,Customer Id,First Name,Last Name,Company,City,Country,Phone 1,Phone 2,Email,Subscription Date,Website
            1,DD37Cf93aecA6Dc,Sheryl,Baxter,Rasmussen Group,East Leonard,Chile,229.077.5154,397.884.0519x718,zunigavanessa@smith.info,2020-08-24,http://www.stephenson.com/
            2,1Ef7b82A4CAAD10,Preston,Lozano,Vega-Gentry,East Jimmychester,Djibouti,5153435776,686-620-1820x944,vmata@colon.com,2021-04-23,http://www.hobbs.com/
            3,6F94879bDAfE5a6,Roy,Berry,Murillo-Perry,Isabelborough,Antigua and Barbuda,+1-539-402-0259,(496)978-3969x58947,beckycarr@hogan.com,2020-03-25,http://www.lawrence.com/
            4,5Cef8BFA16c5e3c,Linda,Olsen,"Dominguez, Mcmillan and Donovan",Bensonview,Dominican Republic,001-808-617-6467x12895,+1-813-324-8756,stanleyblackwell@benson.org,2020-06-02,http://www.good-lyons.com/
            5,053d585Ab6b3159,Joanna,Bender,"Martin, Lang and Andrade",West Priscilla,Slovakia (Slovak Republic),001-234-203-0635x76146,001-199-446-3860x3486,colinalvarado@miles.net,2021-04-17,https://goodwin-ingram.com/
        """.trimIndent()

        val fileUrl = saveFile("prueba.csv", data)

        val activityViewController = UIActivityViewController(
            activityItems = listOf(fileUrl),
            applicationActivities = null
        )
        val application = UIApplication.sharedApplication
        application.keyWindow?.rootViewController?.presentViewController(
            activityViewController,
            animated = true,
            completion = null
        )
        return 1
    }

    @OptIn(BetaInteropApi::class)
    private fun saveFile(fileName: String, content: String): NSURL? {
        val fileManager = NSFileManager.defaultManager()

        // Get the documents directory URL
        val documentDirectory = fileManager.URLsForDirectory(
            directory = 9.toULong(),//NSSearchPathDirectory.NSDocumentDirectory, // Use NSDocumentDirectory
            inDomains = 1.toULong(), //NSSearchPathDomainMask.NSUserDomainMask
        ).first() as NSURL

        val fileURL = documentDirectory.URLByAppendingPathComponent(fileName)
        val data = NSString.create(string = content).dataUsingEncoding(NSUTF8StringEncoding)

        // Write the data to the file
        data?.writeToURL(url = fileURL!!, atomically = true)
        return fileURL!!
    }
}

actual fun shareFile(): Int = IOSShareFile.shareFile()