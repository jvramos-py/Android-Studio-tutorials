package com.example.composersample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composersample.ui.theme.ComposerSampleTheme
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.composersample.ui.theme.SampleData
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import com.example.composersample.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposerSampleTheme {
                Conversation(SampleData.ConversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

//We keep track if the message is expanded or not in this variable
var isExpanded by remember {
    mutableStateOf(false)
}

val test: Int = if (isExpanded) Int.MAX_VALUE else 1

@Composable
fun MessageCard(msg: Message) {
    //surfaceColor will be updated gradually from one color to other
    val surfaceColor by animateColorAsState(
        if (isExpanded) {
            Grey
        } else {
            Grey
        }
    )

    //Add padding around the message.
   Row (modifier = Modifier.padding(all = 8.dp)) {
       Image(
           painter = painterResource(id = R.drawable.ic_launcher_foreground),
           contentDescription = null,
           modifier = Modifier
               .size(50.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
       )

       //add a horizontal space between the image and the column
       Spacer(modifier = Modifier.width(8.dp))
       //We toggle the isExpanded variable when we click on this Column
       Column (modifier = Modifier.clickable { isExpanded = !isExpanded }){
           Text(
               text = msg.author,
               color = MaterialTheme.colors.secondaryVariant,
               style = MaterialTheme.typography.subtitle2
           )
            Spacer(modifier = Modifier.height(4.dp))

           Surface(
               shape = MaterialTheme.shapes.medium,
               elevation = 1.dp,
               //SurfaceColor color will be change here:
                color = surfaceColor,
               //animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
           ) {
               Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    //if the message is expanded, we display all its contents. Otherwise we only display the first line.
                    maxLines = test,
                    style = MaterialTheme.typography.body2
               )
           }
       }
   }
}


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

@Preview
@Composable 
fun PreviewConversation() {
    ComposerSampleTheme {
        Conversation(SampleData.ConversationSample)
    }
}




