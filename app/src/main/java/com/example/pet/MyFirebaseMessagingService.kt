package com.example.pet

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM message received
        // You can customize the notification handling here
    }

    override fun onNewToken(token: String) {
        // Handle new FCM token generation
    }
}