package com.example.losdelaheroica.addSong.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.losdelaheroica.addSong.ui.model.Rhythm
import com.example.losdelaheroica.addSong.ui.model.SongModel
import com.example.losdelaheroica.addSong.ui.utils.ListToUSe
import com.example.losdelaheroica.addSong.ui.utils.Utils
import com.example.losdelaheroica.core.composable.CommonButton
import com.example.losdelaheroica.core.composable.CommonTextField
import com.example.losdelaheroica.ui.theme.*

@Composable
fun SongScreen(songViewModel: SongViewModel) {
    val showDialog by songViewModel.showDialog.observeAsState(initial = false)
    val context: Context = LocalContext.current
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<SongUiState>(
        initialValue = SongUiState.Loading,
        key1 = lifeCycle,
        key2 = songViewModel
    ) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            songViewModel.uiState.collect {
                value = it
            }
        }
    }

    when (uiState) {
        is SongUiState.Error -> {}
        SongUiState.Loading -> {
            CircularProgressIndicator()
        }
        is SongUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddSongDialog(
                    show = showDialog,
                    onDismiss = { songViewModel.closeDialog() },
                    onSongAdded = { songViewModel.onSongAdd(it) },
                    context = context,
                    songViewModel = songViewModel
                )
                SongsList(
                    Modifier.align(Alignment.Center),
                    (uiState as SongUiState.Success).songList,
                    songViewModel
                )
                FabAddSong(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp), songViewModel
                )
            }
        }
    }
}

@Composable
fun SongsList(modifier: Modifier, songList: List<SongModel>, songViewModel: SongViewModel) {
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
                detectTapGestures(onPress = {
                    songViewModel.onSongSelected(songModel)
                }, onLongPress = {
                    songViewModel.deleteSong(songModel)
                })
            },
        elevation = 8.dp,
        backgroundColor = if (songModel.selected) fluorescentBlueDisable else jacartaColor
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp),
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
                    text = "Tono de ${Utils.numberToTone(songModel.songTone)}",
                    fontWeight = FontWeight.Bold,
                    color = textTitleWhite
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 2.dp),
                    text = "Tipo:  ${Utils.songsToString(songModel.rhythm)}",
                    fontWeight = FontWeight.Bold,
                    color = textTitleWhite
                )
            }
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
    context: Context,
    songViewModel: SongViewModel
) {
    var songName by remember { mutableStateOf("") }
    var songTone by remember { mutableStateOf(0) }
    var rhythmSelected: Rhythm by remember { mutableStateOf(Rhythm.Nortena) }
    var expanded by remember { mutableStateOf(false) }
    var expandedTone by remember { mutableStateOf(false) }
    val listRhythm = Rhythm::class.sealedSubclasses.mapNotNull { it.objectInstance }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(shape = RoundedCornerShape(10.dp), elevation = 8.dp) {
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



                    Column(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = Utils.numberToTone(songTone),
                            onValueChange = { songTone = Utils.toneToNumber(it) },
                            enabled = false,
                            readOnly = true,
                            modifier = Modifier
                                .clickable { expandedTone = true }
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(17.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = textTitleWhite,
                                backgroundColor = jacartaColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        DropdownMenu(
                            expanded = expandedTone,
                            onDismissRequest = { expandedTone = false },
                            modifier = Modifier
                                .clickable { expandedTone = true }
                                .fillMaxWidth()
                        ) {
                            ListToUSe.toneNumbers.forEach { tone ->
                                DropdownMenuItem(onClick = {
                                    expandedTone = false
                                    songTone = tone
                                }) {
                                    Text(text = Utils.numberToTone(tone))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(18.dp))

                    Column(Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = Utils.songsToString(rhythmSelected),
                            onValueChange = {
                                rhythmSelected = Utils.stringToRhythm(it)
                            },
                            enabled = false,
                            readOnly = true,
                            modifier = Modifier
                                .clickable { expanded = true }
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(17.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = textTitleWhite,
                                backgroundColor = jacartaColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .clickable { expanded = true }
                                .fillMaxWidth()
                        ) {
                            listRhythm.forEach { rhythm ->
                                DropdownMenuItem(onClick = {
                                    expanded = false
                                    rhythmSelected = rhythm
                                }) {
                                    Text(text = Utils.songsToString(rhythm))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(18.dp))
                    CommonButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            if (songName != "" && songTone != 0) {
                                val song = SongModel(
                                    songName = songName,
                                    songTone = songTone,
                                    rhythm = rhythmSelected
                                )
                                onSongAdded(song)
                                songName = ""
                                songTone = 0
                                rhythmSelected = Rhythm.Nortena
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
}
