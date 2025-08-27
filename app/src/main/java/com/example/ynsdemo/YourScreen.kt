package com.iherb.mobile.base.legacy.base

import android.app.Activity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.iherb.android.compose.ui.common.Divider
import com.iherb.android.compose.ui.common.iherbShadow
import com.iherb.android.compose.ui.theme.TopRoundedCorners8
import com.iherb.android.compose.ui.toDp
import com.iherb.mobile.app.R
import com.iherb.mobile.commons.utils.NonBusinessUtils
import kotlinx.coroutines.flow.MutableSharedFlow


class TabBarOverlay(private val activity: Activity, private val listener: OnTabClickListener) {
    private val composeView: ComposeView = ComposeView(activity)
    var selectedTab: MutableState<Int> = mutableIntStateOf(R.id.home_dest)
    var visible: MutableState<Boolean> = mutableStateOf(true)
    private var offset: Float = 0.0f
    private val offsetFlow = MutableSharedFlow<Float>()
    private val trigger = MutableSharedFlow<Long>()

    companion object {
        val TAB_BAR_HEIGHT = NonBusinessUtils.dpToPx(54)
        val TAB_BAR_HALF_HEIGHT = NonBusinessUtils.dpToPx(28)
    }

    suspend fun fling() {
        trigger.emit(System.currentTimeMillis())
    }

    suspend fun offsetTabBar(delta: Float){
        var value = offset + delta
        if(value > TAB_BAR_HEIGHT)
            value = TAB_BAR_HEIGHT.toFloat()
        else if(value < 0.0f){
            value = 0.0f
        }
        offsetFlow.emit(value)
    }

    init {
        (activity.window.decorView as ViewGroup).addView(
            composeView, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        )
        composeView.setContent {
            if(!(visible.value)) return@setContent

            val offsetAnimatable = remember { Animatable(0.0f) }

            LaunchedEffect(offsetFlow) {
                offsetFlow.collect {
                    offset = it
                    offsetAnimatable.snapTo(it)
                }
            }
            LaunchedEffect(trigger) {
                trigger.collect {
                    try {
                        offsetAnimatable.animateTo(
                            targetValue = if(offset > TAB_BAR_HALF_HEIGHT)
                                TAB_BAR_HEIGHT.toFloat()
                            else
                                0.0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessHigh
                            )
                        )

                        offset = offsetAnimatable.value
                    } catch (e: Exception){
                        e.printStackTrace()
                    }

                }
            }

            Box(
                modifier = Modifier
                    .navigationBarsPadding(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column (
                    modifier = Modifier
                        .offset(y = (offsetAnimatable.value).toInt().toDp())
                        .iherbShadow(TopRoundedCorners8, 16.dp)
                        .background(
                            color = colorResource(R.color.ColorBgWhite),
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                ){
                    contentComposer.value?.invoke()

                    Divider()
                    BottomBar(tabs, tabs.indexOfFirst{ it.id ==  selectedTab.value}, cartCount.value) {
                        listener.onTabClick(it)
                    }

                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }

    private val contentComposer = mutableStateOf<(@Composable () -> Unit)?>(null)
    fun setContent(content: @Composable () -> Unit) {
        contentComposer.value = content
    }
}