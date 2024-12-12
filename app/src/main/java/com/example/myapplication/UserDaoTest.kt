
package com.example.myapplication

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    // Initialisation avant chaque test
    @Before
    fun setUp() {
        // Création d'une base de données en mémoire
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()
        userDao = database.userDao()
    }

    // Nettoyage après chaque test
    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser_correctlyStoresData() = runBlocking {
        // Insertion d'un utilisateur
        val user = User(1, "John Doe")
        userDao.insert(user)

        // Récupération de l'utilisateur par ID
        val retrievedUser = userDao.getUserById(1)

        // Vérification que l'utilisateur inséré est bien récupéré
        assertEquals("John Doe", retrievedUser.name)
    }

    @Test
    fun insertMultipleUsers_correctlyStoresData() = runBlocking {
        // Insertion de plusieurs utilisateurs
        val user1 = User(2, "Jane Doe")
        val user2 = User(3, "Alice Smith")
        userDao.insert(user1)
        userDao.insert(user2)

        // Récupération de tous les utilisateurs
        val users = getValue(userDao.getAll()) // Observer LiveData de manière synchrone

        // Vérification que la liste contient les utilisateurs insérés
        assertEquals(2, users.size)
        assertEquals("Jane Doe", users[0].name)
        assertEquals("Alice Smith", users[1].name)
    }

    // Fonction utilitaire pour observer LiveData de manière synchrone
    fun <T> getValue(liveData: LiveData<T>): T {
        var data: T? = null
        val observer = Observer<T> { t -> data = t }
        liveData.observeForever(observer)
        return data as T
    }
}
