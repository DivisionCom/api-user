package com.example.apiuser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assertions.assertEquals(4, 2 + 2)
    }

    @Test
    fun `WHEN 2 plus 2 EXPECT 4`() {
        Assertions.assertEquals(4, 2 + 2)
    }
}
