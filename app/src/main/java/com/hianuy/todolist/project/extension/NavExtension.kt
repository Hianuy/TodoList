package com.hianuy.todolist.project.extension

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.hianuy.todolist.R


private val slideLeftOption = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_in_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_in_right)
    .build()


fun NavController.navigationWithAnimation(
    destinationId: Int,
    animation: NavOptions = slideLeftOption
) {
    this.navigate(destinationId, null, animation)

}

fun NavController.navigationWithAnimation(
    directions: NavDirections,
    animation: NavOptions = slideLeftOption
) {
    this.navigate(directions, animation)
}