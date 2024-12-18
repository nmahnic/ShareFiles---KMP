package com.nicomahnic.sharecsv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.vinceglb.filekit.core.FileKit
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var page by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    val name = "rowa_${Clock.System.now().epochSeconds}.csv"
    val data = listOf(
        "Id,Nombre,Ciudad,Pais",
        "1,Juan, Bueons Aires, Argentina",
        "2,Pedro, Madrid, España",
        "3,Lucas, Paris, Francia",
        "4,Mateo, Bruselas, Belgica"
    )

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { shareFile(data.joinToString("\n"), name) }) {
                Text("Generate random file")
            }
            Button(onClick = {
                coroutineScope.launch {
                    val file = FileKit.pickFile(
                        type = PickerType.File(extensions = listOf("csv")),
                        mode = PickerMode.Single,
                        title = "Pick a file"
                    )
                    file?.readBytes()?.let { bytes ->
                        val characters = bytes.map { it.toUByte().toInt() }
                        page = charactersToCsvHeader(characters)
                    }
                }
            }) {
                Text("Pick File")
            }
            LazyColumn {
                items(page) {
                    Text(it.joinToString(","))
                }
            }
        }
    }
}

fun charactersToCsvHeader(characters: List<Int>): List<List<String>> {
    var item = listOf<Char>()
    var row = listOf<String>()
    val page = mutableListOf<List<String>>()

    characters.forEach { ascii ->
        val char = ascii.toChar()
        when (ascii) {
            10 -> {
                println("NAMG: ENTER $row")
                row = row.plus(item.joinToString(""))
                item = emptyList()
                page.add(row)
                row = emptyList()
            }

            59, 44 -> {
                row = row.plus(item.joinToString(""))
                item = emptyList()
            }

            else -> {
                if (ascii != 13) {
                    item = item.plus(char)
                }
            }
        }
    }
    page.add(row)
    println("NAMG: page $page")
    return page
}
