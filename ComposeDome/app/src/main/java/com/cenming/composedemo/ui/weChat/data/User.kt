package com.cenming.composedemo.ui.weChat.data

import androidx.annotation.DrawableRes
import com.cenming.composedemo.R

data class User constructor(
  val id: String,
  val name: String,
  @DrawableRes val avatar: Int,
  val remarks: String = "",
  var addState: AddState = AddState.Default,
  var accountNum: String = "",
  var phone: String = "13000000000"
) {
  companion object {
    val Me: User = User(
      "rengwuxian", "扔物线-朱凯",
      R.drawable.avatar_rengwuxian,
      accountNum = "VX123456789"
    )
  }
}


enum class AddState {
  Default,
  Add,
  Added,
  Expired
}