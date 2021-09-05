package com.example.githubuser2.ui.views.reminder

import android.app.AlarmManager
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubuser2.R

class ReminderPreference : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER_KEY: String
    private lateinit var isReminder: SwitchPreference
    private val alarmReceiver by lazy { AlarmReceiver() }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummary()

    }

    private fun setSummary() {
        val sharePref = preferenceManager.sharedPreferences
        isReminder.isChecked = sharePref.getBoolean(REMINDER_KEY, false)
    }

    private fun init() {
        REMINDER_KEY = getString(R.string.reminder_key)
        isReminder = findPreference<SwitchPreference>(REMINDER_KEY) as SwitchPreference
    }

    override fun onSharedPreferenceChanged(sharePref: SharedPreferences?, key: String?) {
        when (key) {
            REMINDER_KEY -> {
                isReminder.isChecked = sharePref?.getBoolean(REMINDER_KEY, false) == true
                setUpReminder(isReminder.isChecked)
            }
        }
    }

    private fun setUpReminder(checked: Boolean) {
        if (checked) {
            setupAlarm()
        } else {
            canceAlarm()
        }
    }

    private fun setupAlarm() {
        if (!alarmReceiver.isRepeatAlarmSet(requireContext())) {
            alarmReceiver.setRepeatingAlarm(requireContext())
        }
    }

    private fun canceAlarm() {
        alarmReceiver.cancelRepeatAlarm(requireContext())
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


}