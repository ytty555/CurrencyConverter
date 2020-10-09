package ru.okcode.currencyconverter.model.processor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.okcode.currencyconverter.ui.basechooser.TextProcessorImpl

@RunWith(Parameterized::class)
class TextProcessorImplTest(
    private val type: Type,
    private val inputValue: String,
    private val operand: Any,
    private val expected: Any
) {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sut: TextProcessorImpl
    private val observer: Observer<String> = mock()

    @Before
    fun setUp() {
        sut = TextProcessorImpl()
        sut.displayValue.observeForever(observer)
    }

    @After
    fun afterSetUp() {
        sut.displayValue.removeObserver(observer)
    }

    enum class Type {
        DIGIT_PRESS,
        COMMA_PRESS,
        ERASE_PRESS,
        SET_VALUE,
        GET_NUMBER
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                // DIGIT_PRESS -------------------------------------------------
                arrayOf(Type.DIGIT_PRESS, "1", 0, "10"), // type, inputValue, operand, resultValue
                arrayOf(Type.DIGIT_PRESS, "0", 0, "0"),
                arrayOf(Type.DIGIT_PRESS, "0", 5, "5"),
                arrayOf(Type.DIGIT_PRESS, "0,", 0, "0,0"),
                arrayOf(Type.DIGIT_PRESS, "55", -1, "55"),
                arrayOf(Type.DIGIT_PRESS, "55", 10, "55"),
                // SET_VALUE -------------------------------------------------
                arrayOf(Type.SET_VALUE, "555", "10", "10"),
                arrayOf(Type.SET_VALUE, "0", "10", "10"),
                // COMMA_PRESS -------------------------------------------------
                arrayOf(Type.COMMA_PRESS, "0", ",", "0,"),
                arrayOf(Type.COMMA_PRESS, "1", ",", "1,"),
                arrayOf(Type.COMMA_PRESS, "1,", ",", "1,"),
                arrayOf(Type.COMMA_PRESS, "1,0", ",", "1,0"),
                // ERASE_PRESS -------------------------------------------------
                arrayOf(Type.ERASE_PRESS, "1,0", "", "1,"),
                arrayOf(Type.ERASE_PRESS, "555", "", "55"),
                arrayOf(Type.ERASE_PRESS, "1", "", "0"),
                arrayOf(Type.ERASE_PRESS, "0", "", "0"),
                arrayOf(Type.ERASE_PRESS, "0,", "", "0"),
                arrayOf(Type.ERASE_PRESS, "5,", "", "5"),
                // GET_NUMBER -------------------------------------------------
                arrayOf(Type.GET_NUMBER, "1", "", 1f),
                arrayOf(Type.GET_NUMBER, "1,", "", 1f),
                arrayOf(Type.GET_NUMBER, "15,0", "", 15f),
                arrayOf(Type.GET_NUMBER, "15,00", "", 15f),
                arrayOf(Type.GET_NUMBER, "15,000000", "", 15f),
                arrayOf(Type.GET_NUMBER, "15,005", "", 15.005f),
                arrayOf(Type.GET_NUMBER, "0", "", 1f),
                arrayOf(Type.GET_NUMBER, "0,", "", 1f),
                arrayOf(Type.GET_NUMBER, "0,0", "", 1f),
                arrayOf(Type.GET_NUMBER, "0,0000", "", 1f),
            )
        }
    }

    @Test
    fun setDisplayValueTest() {
        Assume.assumeTrue(type == Type.SET_VALUE)

        sut.setDisplayValue(operand as String)
        val actual = sut.displayValue.value
        assertEquals("setDisplayValueTest", expected, actual)
    }

    @Test
    fun pressDigit() {
        Assume.assumeTrue(type == Type.DIGIT_PRESS)

        sut.setDisplayValue(inputValue)
        sut.pressDigit(operand as Int)
        val actual = sut.displayValue.value
        assertEquals("pressDigit error", expected as String, actual)
    }

    @Test
    fun pressComma() {
        Assume.assumeTrue(type == Type.COMMA_PRESS)

        sut.setDisplayValue(inputValue)
        sut.pressComma()
        val actual = sut.displayValue.value
        assertEquals("pressComma error", expected as String, actual)
    }

    @Test
    fun pressErase() {
        Assume.assumeTrue(type == Type.ERASE_PRESS)

        sut.setDisplayValue(inputValue)
        sut.pressErase()
        val actual = sut.displayValue.value
        assertEquals("pressErase error", expected as String, actual)
    }

    @Test
    fun getNumberValue() {
        Assume.assumeTrue(type == Type.GET_NUMBER)

        sut.setDisplayValue(inputValue)
        val actual: Float = sut.getNumberValue()
        assertEquals("pressErase error", expected as Float, actual)
    }
}