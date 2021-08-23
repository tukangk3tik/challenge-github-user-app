package com.dicoding.picodiploma.githubuserapp.utils.alarm

import android.content.Context
import androidx.core.content.edit
import com.dicoding.picodiploma.githubuserapp.R

class AlarmStateManager(val context: Context) {
    companion object {
        private const val PREFS_NAME = "alarm_state"
        private const val TIME_SET = "time_set"
        private const val ALARM_STATUS = "alarm_status"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setAlarm(time: String) {
        preferences.edit {
            putString(TIME_SET, time)
            putBoolean(ALARM_STATUS, true)
        }
    }

    fun getAlarmDetail(): AlarmStateModel {
        val time = preferences.getString(TIME_SET, context.resources.getString(R.string.label_waktu_reminder)).toString()
        val status = preferences.getBoolean(ALARM_STATUS, false)

        return AlarmStateModel(time, status)
    }

    fun turnOffAlarm(){
        preferences.edit {
            putBoolean(ALARM_STATUS, false)
        }
    }
}