package com.example.bangkitandroid

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun rectangularBoxTesting() {
        val rec = RectangularBox(3.0, 4.0, 5.0)
        val volume = rec.getVolume()
        assertEquals(60.0, volume, 0.01)
    }
}