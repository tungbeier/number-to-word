package io.github.tungbeier.language

import io.github.tungbeier.language.exception.UnsupportedNumberFormat
import kotlin.math.abs

const val TEN = 10L
const val TWENTY = 20L
const val HUNDRED = 100L
const val THOUSAND = 1000L
const val MILLION = 1_000_000L
const val BILLION = 1_000_000_000L

const val WORD_HUNDRED = "hundred"
const val WORD_THOUSAND = "thousand"
const val WORD_MILLION = "million"

/** **English numbering rules**
 * - Digits from zero to nine are specific words, as well as numbers from ten to twelve,
 * namely zero [0], one [1], two [2], three [3], four [4], five [5], six [6], seven [7],
 * eight [8], nine [9], ten [10], eleven [11], and twelve [12].
 * - From thirteen to nineteen, the numbers are formed from the digits three to nine,
 * adding the -teen suffix at the end: thirteen [13], fourteen [14], fifteen [15],
 * sixteen [16], seventeen [17], eighteen [18], and nineteen [19].
 * - The tens are formed by adding the -(t)y suffix at the end of the multiplier digit root,
 * with the exception of ten: ten [10], twenty [20], thirty [30], forty [40] (and not forty),
 * fifty [50], sixty [60], seventy [70], eighty [80], and ninety [90].
 * - From twenty-one to ninety-nine, the tens and units are joined with a hyphen.
 * - All the three-digit numbers are constructed by stating the hundreds,
 * then adding the and word, then the tens and the digits
 * (e.g.: two hundred and sixty-five [265]). Using the coordination and is a matter of choice,
 * as whereas some writers prefer using it, The Chicago Manual of Style’s preference is to omit it.
 * - Hundred (100), thousand (1,000) and million (1,000,000) are always singular
 * (e.g.: six hundred and thirty-five [635]).
 * - When directly added to hundred and thousand, the and word is added before tens
 * and units (e.g.: seven hundred and three [703], or five thousand and two [5,002]).
 */
class EnglishConverter {
    private val zeroToNineteen = arrayOf(
        "zero", "one", "two", "three", "four", "five", "six", "seven",
        "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
        "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    )

    private val tens = arrayOf(
        "", "",
        "twenty", "thirty", "forty", "fifty",
        "sixty", "seventy", "eighty", "ninety"
    )

    /**
     * Convert a number to its corresponding word.
     *
     * @param number as Long
     * @return the corresponding word to the given number
     */
    fun asWord(number: Long): String {
        val absoluteNumber = abs(number)

        if (absoluteNumber >= BILLION) {
            throw UnsupportedNumberFormat("The number $number is not yet supported")
        }

        return when {
            absoluteNumber < TWENTY -> zeroToNineteen[absoluteNumber.toInt()]
            absoluteNumber < HUNDRED -> getTens(absoluteNumber)
            absoluteNumber < THOUSAND -> getHundreds(absoluteNumber)
            else -> getWordOverThousands(absoluteNumber)
        }
    }

    private fun getTens(number: Long): String {
        val base = number / TEN
        val rest = number % TEN
        return tens[base.toInt()].plus(
            if (rest == 0L) "" else "-".plus(zeroToNineteen[rest.toInt()])
        )
    }

    private fun getHundreds(number: Long): String {
        val base = number / HUNDRED
        val rest = number % HUNDRED
        return zeroToNineteen[base.toInt()].plus(
            " $WORD_HUNDRED".plus(
                if (rest == 0L) "" else " and ".plus(asWord(rest))
            )
        )
    }

    private fun getWordOverThousands(number: Long): String {
        val baseAsWord = if (number < MILLION) WORD_THOUSAND else WORD_MILLION
        val base = if (baseAsWord == WORD_THOUSAND) THOUSAND else MILLION
        val nextLowerBaseNumber = if (baseAsWord == WORD_THOUSAND) HUNDRED else THOUSAND

        val rest = number % base
        val restAsWord = when {
            rest == 0L -> ""
            rest < nextLowerBaseNumber -> " and ".plus(asWord(rest))
            else -> ", ".plus(asWord(rest))
        }

        return asWord(number / base).plus(" $baseAsWord").plus(restAsWord)
    }
}

