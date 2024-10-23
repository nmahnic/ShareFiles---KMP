package com.nicomahnic.sharecsv

object IOSShareFile {
    fun shareFile(): Int {
        return 1
    }
}

actual fun shareFile(): Int = IOSShareFile.shareFile()