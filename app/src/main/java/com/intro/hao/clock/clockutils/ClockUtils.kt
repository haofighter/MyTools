package com.intro.project.clock.clockutils

import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.app.AlarmManager
import com.intro.hao.mytools.base.App


/**
 * Created by haozhang on 2018/1/15.
 */
class ClockUtils private constructor() {

    companion object {
        fun get(): ClockUtils {
            return Inner.clockUtils
        }
    }

    private object Inner {
        val clockUtils = ClockUtils()
    }


    var pendingIntents: MutableList<AlarmInfo> = mutableListOf<AlarmInfo>()

    fun addAlarmClock(alarmInfo: AlarmInfo) {
        var add: AlarmInfo? = null
        for (i in pendingIntents.indices) {
            if (pendingIntents.get(i).tag.equals(alarmInfo.tag)) {
                add = pendingIntents.set(i, alarmInfo)
            }
        }
        if (add == null) {
            pendingIntents.add(alarmInfo)
        }
    }

    fun removeAlarmClock(alarmInfo: AlarmInfo) {
        for (i in pendingIntents.indices) {
            if (pendingIntents.get(i).tag.equals(alarmInfo.tag)) {
                pendingIntents.removeAt(i)
                (App.instance.getSystemService(ALARM_SERVICE) as AlarmManager).cancel(pendingIntents.get(i).pendingIntent)
            }
        }
    }

}