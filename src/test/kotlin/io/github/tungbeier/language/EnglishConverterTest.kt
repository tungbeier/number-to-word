package io.github.tungbeier.language

import kotlin.test.Test
import kotlin.test.assertEquals

class EnglishConverterTest {

    private val converter = EnglishConverter()

    @Test
    fun negativeNumbers() {
        assertEquals("zero", converter.asWord(-0))
        assertEquals("twenty-one", converter.asWord(-21))
    }

    @Test
    fun zeroToNineteen() {
        for (i in 0..19) {
            assertEquals(converter.zeroToNineteen[i], converter.asWord(i))
        }
    }

    @Test
    fun tens() {
        for (i in 2..9) {
            assertEquals(converter.tens[i], converter.asWord(i * 10))
        }

        hashMapOf(
            21 to "twenty-one",
            32 to "thirty-two",
            43 to "forty-three",
            54 to "fifty-four",
            65 to "sixty-five",
            76 to "seventy-six",
            87 to "eighty-seven",
            98 to "ninety-eight"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number)) }
    }

    @Test
    fun hundreds() {
        for (i in 1..9) {
            assertEquals(
                converter.zeroToNineteen[i] + " hundred",
                converter.asWord(i * 100)
            )
        }

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
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number)) }
    }

    @Test
    fun thousands() {
        for (i in 1..9) {
            assertEquals(
                converter.zeroToNineteen[i] + " thousand",
                converter.asWord(i * 1000)
            )
        }

        hashMapOf(
            1001 to "one thousand and one",
            2010 to "two thousand and ten",
            3211 to "three thousand, two hundred and eleven",
            45364 to "forty-five thousand, three hundred and sixty-four",
            962453 to "nine hundred and sixty-two thousand, four hundred and fifty-three"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number)) }
    }

    @Test
    fun millions() {
        for (i in 1..9) {
            assertEquals(
                converter.zeroToNineteen[i] + " million",
                converter.asWord(i * 1_000_000)
            )
        }

        hashMapOf(
            1_000_021 to "one million and twenty-one",
            2_176_045 to "two million, one hundred and seventy-six thousand and forty-five",
            75_426_328 to "seventy-five million, four hundred and twenty-six thousand, three hundred and twenty-eight",
            974_531_248 to "nine hundred and seventy-four million, five hundred and thirty-one thousand, two hundred and forty-eight"
        ).forEach { (number, word) -> assertEquals(word, converter.asWord(number)) }
    }
}