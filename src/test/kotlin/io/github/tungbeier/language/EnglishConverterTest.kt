package io.github.tungbeier.language

import io.github.tungbeier.language.exceptions.UnsupportedNumberFormat
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnglishConverterTest {

    private val converter = EnglishConverter()

    @Test
    fun `Expect exception when number is not yet supported`() {
        val exception = assertFailsWith<UnsupportedNumberFormat> {
            converter.asWord(1_000_000_000_000_000_000)
        }

        assertEquals("The number 1000000000000000000 is not yet supported", exception.message)
    }

    @Test
    fun `Test negative numbers`() {
        assertEquals("twenty-one", converter.asWord(-21L))
    }

    @Test
    fun `Test numbers between 0 and 19`() {
        hashMapOf(
            0 to "zero", 1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five",
            6 to "six", 7 to "seven", 8 to "eight", 9 to "nine", 10 to "ten", 11 to "eleven",
            12 to "twelve", 13 to "thirteen", 14 to "fourteen", 15 to "fifteen",
            16 to "sixteen", 17 to "seventeen", 18 to "eighteen", 19 to "nineteen"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number.toLong())) }
    }

    @Test
    fun `Test numbers between 20 and 99`() {
        hashMapOf(
            20 to "twenty", 30 to "thirty", 40 to "forty", 50 to "fifty",
            60 to "sixty", 70 to "seventy", 80 to "eighty", 90 to "ninety",
            21 to "twenty-one", 32 to "thirty-two", 43 to "forty-three",
            54 to "fifty-four", 65 to "sixty-five", 76 to "seventy-six",
            87 to "eighty-seven", 98 to "ninety-eight"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number.toLong())) }
    }

    @Test
    fun `Test numbers between 100 and 999`() {
        hashMapOf(
            101 to "one hundred and one",
            210 to "two hundred and ten",
            321 to "three hundred and twenty-one",
            432 to "four hundred and thirty-two",
            543 to "five hundred and forty-three",
            654 to "six hundred and fifty-four",
            765 to "seven hundred and sixty-five",
            876 to "eight hundred and seventy-six",
            987 to "nine hundred and eighty-seven"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number.toLong())) }
    }

    @Test
    fun `Test numbers between 1000 and 999_999`() {
        hashMapOf(
            1001 to "one thousand and one",
            2010 to "two thousand and ten",
            3211 to "three thousand, two hundred and eleven",
            45364 to "forty-five thousand, three hundred and sixty-four",
            962453 to "nine hundred and sixty-two thousand, four hundred and fifty-three"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number.toLong())) }
    }

    @Test
    fun `Test numbers between 1_000_000 and 999_999_999`() {
        hashMapOf(
            1_000_021 to "one million and twenty-one",
            2_176_045 to "two million, one hundred and seventy-six thousand and forty-five",
            75_426_328 to "seventy-five million, four hundred and twenty-six thousand, three hundred and twenty-eight",
            974_531_248 to "nine hundred and seventy-four million, five hundred and thirty-one thousand, two hundred and forty-eight"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number.toLong())) }
    }
}