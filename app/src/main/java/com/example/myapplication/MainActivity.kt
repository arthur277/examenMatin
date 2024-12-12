package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Bienvenue sur FinalExamApp!", Toast.LENGTH_LONG).show()
        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter()

        // Créez la base de données en utilisant Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "user-database"
        ).build()

// Utilisez le DAO pour insérer ou récupérer des données
        val userDao = db.userDao()

// Exemple d'insertion d'un utilisateur
        val user = User(id = 1, name = "John Doe")
        userDao.insert(user)

// Exemple de récupération de tous les utilisateurs
        val users = userDao.getAll()

// Exemple d'observation des utilisateurs dans une activité ou un fragment
        //userDao.getAll().observe(this, Observer { users ->
            // Mettez à jour l'UI avec la liste des utilisateurs
       // })

    }

}
fun notificationShow(context: Context) {
    // Récupère le service NotificationManager
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // ID du canal pour les notifications
    val channelId = "exam_channel"

    // Vérifier si la version Android est 8.0 (API 26) ou supérieure
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Créer un canal de notification pour Android 8.0 et plus
        val channel = NotificationChannel(
            channelId,
            "Exam Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // Enregistrez ce canal dans le système
        notificationManager.createNotificationChannel(channel)
    }

    // Créez la notification
    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Notification FinalExamApp")
        .setContentText("Bonjour, voici un exemple de notification.")
        .setSmallIcon(R.drawable.ic_notification) // Assurez-vous que cette icône existe
        .build()

    // Affiche la notification avec l'ID 1
    notificationManager.notify(1, notification)
}


