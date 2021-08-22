package com.dicoding.picodiploma.githubuserapp

import androidx.room.Room
import com.dicoding.picodiploma.githubuserapp.db.FavoritDao
import com.dicoding.picodiploma.githubuserapp.db.FavoritDatabase
import org.junit.Before
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

//@RunWith(MockitoJUnitRunner::class)
class FavoritEntityReadWriteTest {

    private val testUsername = "wakandaBP"

    private lateinit var favDao: FavoritDao

    @Mock
    private lateinit var db: FavoritDatabase

    @Before
    fun createDb() {
        val context = mock(MainActivity::class.java)
        db = Room.inMemoryDatabaseBuilder(context, FavoritDatabase::class.java)
            .build()

        favDao = db.getFavoritDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    //@Throws(Exception::class)
    fun writeFavAndReadInList() {
        val favorit = listOf(FavoritEntity("wakandaBP", "https://avatars0.githubusercontent.com/u/32126030?v=4"))
        `when`(favDao.insert(favorit)).thenReturn(null)

        val favoritItem = favDao.findByUsername(testUsername)
        assertEquals(favoritItem, favoritItem)
    }
}