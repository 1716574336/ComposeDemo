package com.cenming.composedemo.data


/**
 * Created by Cenming on 2021/3/4.
 * 功能:包含值或异常的泛型类
 * 1, Sealed class（密封类） 是一个有特定数量子类的类，看上去和枚举有点类似，所不同的是，
 * 		在枚举中，我们每个类型只有一个对象（实例）；而在密封类中，同一个类可以拥有几个对象。
 * 2, Sealed class（密封类）的所有子类都必须与密封类在同一文件中
 * 3, Sealed class（密封类）的子类的子类可以定义在任何地方，并不需要和密封类定义在同一个文件中
 * 4, Sealed class（密封类）没有构造函数，不可以直接实例化，只能实例化内部的子类
 *
 * out 泛型用于函数的返回值
 * in  泛型用于函数的参数
 */
sealed class Result<out R> {

	data class Success<out T>(val data: T) : Result<T>()
	data class Error(val exception: Exception) : Result<Nothing>()
}

/**
 * 如果[Result]的类型为[Result.Success]且保存为非空的[Result.Success.data]，则为true。
 */
val Result<*>.succeeded
	get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
	return (this as? Result.Success<T>)?.data ?: fallback
}