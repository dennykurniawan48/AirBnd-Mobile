package com.dennydev.airbnd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.IndeterminateCheckBox
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertGuess(
    adult: Int,
    children: Int,
    onIncreaseAdult: ()-> Unit,
    onDecreaseAdult: ()-> Unit,
    onIncreaseKid: ()-> Unit,
    onDecreaseKid: ()-> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(onDismissRequest = { onConfirm() }, title = {
        Text("Add Guest")
    }, confirmButton = {
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text("Confirm")
            }
        }
    }, text = {
        Column(){
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier=Modifier.fillMaxWidth()){
                Text(text = "Adult")
                Row(verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = { onDecreaseAdult() }) {
                        Icon(imageVector = Icons.Default.IndeterminateCheckBox, contentDescription ="" )
                    }
                    Text(adult.toString())
                    IconButton(onClick = { onIncreaseAdult() }) {
                        Icon(imageVector = Icons.Default.AddBox, contentDescription = "")
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier=Modifier.fillMaxWidth()){
                Text(text = "Children")
                Row(verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = { onDecreaseKid() }) {
                        Icon(imageVector = Icons.Default.IndeterminateCheckBox, contentDescription ="" )
                    }
                    Text(children.toString())
                    IconButton(onClick = { onIncreaseKid() }) {
                        Icon(imageVector = Icons.Default.AddBox, contentDescription = "")
                    }
                }
            }
        }
    })
}