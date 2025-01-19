package com.plcoding.streamchatapp

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator


/**
 * Created by Abhinay on 19/01/25.
 *
 *
 */

fun NavController.navigateSafely(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if(action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)

    }

}