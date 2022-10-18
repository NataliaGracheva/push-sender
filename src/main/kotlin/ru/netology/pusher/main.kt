package ru.netology.pusher

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import token
import java.io.FileInputStream


fun main() {
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()

    FirebaseApp.initializeApp(options)

    val message = Message.builder()
        .putData("action", "LIKE")
        .putData("content", """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 2,
          "postAuthor": "Netology"
        }""".trimIndent())
        .setToken(token)
        .build()

    val response  = FirebaseMessaging.getInstance().subscribeToTopic(mutableListOf(token), "new_post_of_user_1")

    val newPostMessage = Message.builder()
        .putData("action", "NEW_POST")
        .putData("content", """{
          "userId": 1,
          "userName": "Vasiliy",
          "postContent": "some text some text some text some text some text some text some text some text some text"
        }""".trimIndent())
        .setTopic("new_post_of_user_1")
        .build()

    val anotherNewPostMessage = Message.builder()
        .putData("action", "NEW_POST")
        .putData("content", """{
          "userId": 123,
          "userName": "Some user",
          "postContent": "some text some text some text some text some text some text some text some text some text"
        }""".trimIndent())
        .setTopic("new_post_of_user_123")
        .build()

    FirebaseMessaging.getInstance().send(message)
    FirebaseMessaging.getInstance().send(newPostMessage)
    FirebaseMessaging.getInstance().send(anotherNewPostMessage)
}
