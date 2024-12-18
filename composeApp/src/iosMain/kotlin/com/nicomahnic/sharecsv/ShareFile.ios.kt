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
    fun shareFile(data: String, name: String): Int {
        val fileUrl = saveFile(name, data)

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

actual fun shareFile(data: String, name: String): Int = IOSShareFile.shareFile(data, name)