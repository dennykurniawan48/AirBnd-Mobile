package com.dennydev.airbnd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlertLogout(
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = { onConfirm() }, title = {
        Text("Are you sure")
    }, confirmButton = {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = {
                onCancel()
            }) {
                Text("Cancel")
            }
            TextButton(onClick = {
                onConfirm()
            }) {
                Text("Confirm")
            }
        }
    }, text = {
        Text(text = "You will sign-out from your account.")
    })
}