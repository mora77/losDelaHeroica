package com.example.losdelaheroica.addSong.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.losdelaheroica.addSong.ui.model.SongModel
import com.example.losdelaheroica.core.composable.CommonButton
import com.example.losdelaheroica.core.composable.CommonTextField
import com.example.losdelaheroica.ui.theme.*

@Composable
fun SongScreen(songViewModel: SongViewModel) {

    val showDialog by songViewModel.showDialog.observeAsState(initial = false)
    val context: Context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        AddSongDialog(
            show = showDialog,
            onDismiss = { songViewModel.closeDialog() },
            onSongAdded = { songViewModel.onSongAdd(it) },
            context = context
        )
        FabAddSong(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), songViewModel
        )
        SongsList(Modifier.align(Alignment.Center), songViewModel)
    }
}

@Composable
fun SongsList(modifier: Modifier, songViewModel: SongViewModel) {
    val songList: List<SongModel> = songViewModel.songList
    if (songList.isEmpty()) {
        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Aún no tienes canciones",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = textTitleWhite
            )
            Text(
                text = "Comenzar a agregar",
                fontSize = 12.sp,
                color = fluorescentBlue,
                modifier = Modifier.clickable { songViewModel.openDialog() },
                textDecoration = TextDecoration.Underline
            )
        }
    } else {
        LazyColumn {
            items(songList, key = { it.id }) { song ->
                SongItem(songModel = song, songViewModel = songViewModel)
            }
        }
    }
}

@Composable
fun SongItem(songModel: SongModel, songViewModel: SongViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    songViewModel.onSongSelected(songModel)
                }, onLongPress = {
                    songViewModel.deleteSong(songModel)
                })
            },
        elevation = 8.dp,
        backgroundColor = if (songModel.selected) fluorescentBlueDisable else jacartaColor
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                text = songModel.songName,
                fontWeight = FontWeight.Bold,
                color = textTitleWhite
            )
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = "Tono de ${songModel.songTone}",
                fontWeight = FontWeight.Bold,
                color = textTitleWhite
            )
        }
    }
}

@Composable
fun FabAddSong(modifier: Modifier, songViewModel: SongViewModel) {
    FloatingActionButton(
        onClick = {
            songViewModel.openDialog()
        },
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Filled.PlaylistAdd,
            contentDescription = "add song"
        )
    }
}

@Composable
fun AddSongDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onSongAdded: (SongModel) -> Unit,
    context: Context
) {
    var songName by remember { mutableStateOf("") }
    var songTone by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(fluorescentBlueDisable)
                    .padding(18.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Añadir Canción",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textTitleWhite
                )
                Spacer(modifier = Modifier.size(18.dp))
                CommonTextField(
                    value = songName,
                    onTextChanged = { songName = it },
                    placeHolder = "Nombre de la canción"
                )
                Spacer(modifier = Modifier.size(18.dp))
                CommonTextField(
                    value = songTone,
                    onTextChanged = { songTone = it },
                    placeHolder = "Tono de la canción"
                )
                Spacer(modifier = Modifier.size(18.dp))
                CommonButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        if (songName != "" && songTone != "") {
                            val song = SongModel(
                                songName = songName,
                                songTone = songTone
                            )
                            onSongAdded(song)
                            songName = ""
                            songTone = ""
                        } else {
                            Toast.makeText(
                                context,
                                "Por favor llenar los campos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    buttonEnabled = true,
                    text = "Añadir Canción"
                )
            }
        }
    }
}
