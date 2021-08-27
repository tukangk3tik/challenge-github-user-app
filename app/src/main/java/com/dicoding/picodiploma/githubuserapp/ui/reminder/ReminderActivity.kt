package com.dicoding.picodiploma.githubuserapp.ui.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityReminderBinding
import com.dicoding.picodiploma.githubuserapp.utils.alarm.AlarmReceiver
import com.dicoding.picodiploma.githubuserapp.utils.alarm.AlarmStateManager
import kotlinx.android.synthetic.main.activity_reminder.*
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity(), View.OnClickListener, TimePickerFragment.DialogTimeListener {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var alarmStateManager: AlarmStateManager

    companion object {
        private const val TIME_PICKER_TAG = "TimePicker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSetTime.setOnClickListener(this)
        binding.switchAlarm.setOnClickListener(this)

        alarmStateManager = AlarmStateManager(this)
        val dataState = alarmStateManager.getAlarmDetail()

        binding.timeReminder.text = dataState.time
        if (dataState.status) {
            binding.switchAlarm.isChecked = true
            binding.tvStatusAlarm.text = resources.getString(R.string.turn_off_alarm)
        }

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_set_time -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_TAG)
            }
            R.id.switch_alarm -> {
                val status = binding.switchAlarm.isChecked

                if (status) {
                    //cancel available alarm
                    if (alarmReceiver.isAlarmSet(this)){
                        alarmReceiver.cancelAlarm(this)
                    }

                    //set new alarm
                    val time = binding.timeReminder.text.toString()
                    alarmStateManager.setAlarm(time)
                    alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.APP_NAME, time)

                    binding.tvStatusAlarm.text = resources.getString(R.string.turn_off_alarm)
                    Toast.makeText(this, resources.getString(R.string.alarm_enable_msg), Toast.LENGTH_SHORT).show()
                } else {
                    alarmStateManager.turnOffAlarm()
                    alarmReceiver.cancelAlarm(this)

                    val message = resources.getString(R.string.alarm_disable_msg)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    binding.tvStatusAlarm.text = resources.getString(R.string.turn_on_alarm)
                }
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.timeReminder.text = dateFormat.format(calendar.time)

        if (alarmStateManager.getAlarmDetail().status){
            val time = dateFormat.format(calendar.time).toString()

            alarmReceiver.cancelAlarm(this)
            alarmStateManager.setAlarm(time)
            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.APP_NAME, time)

            Toast.makeText(this, resources.getString(R.string.alarm_set), Toast.LENGTH_SHORT).show()
        }
    }
}