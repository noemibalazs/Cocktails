package com.noemi.android.cocktails

import android.content.SharedPreferences
import android.os.Build
import com.nhaarman.mockito_kotlin.mock
import com.noemi.android.cocktails.preferences.PreferencesRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.NullPointerException

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class PreferenceRepositoryUnitTest {

    private lateinit var preferencesRepository: PreferencesRepository
    private val sharedPreferences: SharedPreferences = mock()
    private val cocktailID = 12

    @Before
    fun setUp() {
        preferencesRepository = PreferencesRepository(sharedPreferences)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldPass() {
        preferencesRepository.cocktailId = cocktailID
        val expected = preferencesRepository.cocktailId
        assertEquals(expected, cocktailID)
    }

    @Test(expected = NullPointerException::class)
    fun testShouldFailed() {
        preferencesRepository.cocktailId = cocktailID
        val expected = preferencesRepository.cocktailId
        assertNotEquals(expected, cocktailID - 1)
    }
}