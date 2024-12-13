package com.objetivobolso.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DefaultGoalItem(

    var goalId: Int,
    @StringRes var goalTitle: Int,
    @DrawableRes var goalImg: Int

)
