package com.cnting.cstartup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cnting.cstartup.task.Task1
import com.cnting.cstartup.task.Task2
import com.cnting.cstartup.task.Task3
import com.cnting.cstartup.task.Task4
import com.cnting.cstartup.task.Task5
import com.cnting.cstartup.ui.theme.CStartUpTheme
import com.cnting.startup.StartupManager
import com.cnting.startup.TaskSort

/**
 * 学习 https://juejin.cn/post/7151597415768784933
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CStartUpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

//        StartupManager.Builder().setAllTask(
//            mutableListOf(
//                Task1(),
//                Task2(),
//                Task3(),
//                Task4(),
//                Task5()
//            )
//        ).build().start(this).await()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CStartUpTheme {
        Greeting("Android")
    }
}