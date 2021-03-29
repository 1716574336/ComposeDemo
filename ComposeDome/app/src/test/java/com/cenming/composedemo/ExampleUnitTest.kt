package com.cenming.composedemo

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
		this.testData()
	}

	private fun testData(){
		val (one, two, three) = TestData("paramOne", 2){ 3.0f }
		println(one)
		println(two)
		println(three.invoke())
	}
}

data class TestData(
	val paramOne : String,
	val paramTwo: Int,
	val paramThree : () -> Float
)