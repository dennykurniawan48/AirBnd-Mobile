package com.dennydev.airbnd.component.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Desk
import androidx.compose.material.icons.filled.PhotoCameraFront
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dennydev.airbnd.R
import com.dennydev.airbnd.model.response.property.Data
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/*
*
*  Inspired from : https://github.com/mazzouzi/collapsing-toolbar-with-parallax-effect-and-curved-motion-in-compose
*
 */

@Composable
fun Content(
    data: Data,
    headerHeight: Dp,
    scrollState: ScrollState
) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.verticalScroll(scrollState)) {
        Spacer(Modifier.height(headerHeight))
        Column(modifier= Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)) {
            Text("${data.property.area.name}, ${data.property.area.country.name}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(28.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
              Image(painter = painterResource(id = R.drawable.room), contentDescription = "", modifier=Modifier.width(60.dp))
              Spacer(modifier = Modifier.width(12.dp))
                Column() {
                  Text(text = "Room in a rental unit", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                  Text(text = "Your own room in a home, plus access to shared spaces.", style = MaterialTheme.typography.bodySmall)
              }  
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.house), contentDescription = "", modifier=Modifier.width(60.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column() {
                    Text(text = "Shared common spaces", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text(text = "You'll share parts of the home with the Host.", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.family), contentDescription = "", modifier=Modifier.width(60.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column() {
                    Text(text = "Large spaces", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text(text = "Designed for 4 adults and 4 childrens.", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.cancel), contentDescription = "", modifier=Modifier.width(60.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column() {
                    Text(text = "Changed your mind?", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text(text = "You're free to cancel within 48 hours.", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. ", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(28.dp))
            Text("Facility", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(){
                Icon(imageVector = Icons.Default.Wifi, contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))
                Text("Wifi")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(){
                Icon(imageVector = Icons.Default.RestaurantMenu, contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))
                Text("Kitchen")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(){
                Icon(imageVector = Icons.Default.PhotoCameraFront, contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))
                Text("Security Camera")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(){
                Icon(imageVector = Icons.Default.Desk, contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))
                Text("Work place")
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text("Maps", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            val location = LatLng(data.property.lat, data.property.long)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(location, 20f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = location),
                    title = data.property.name,
                    snippet = "Place to stay"
                )
            }
        }
    }
}