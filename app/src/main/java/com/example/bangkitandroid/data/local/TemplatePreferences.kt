package com.example.bangkitandroid.data.local

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

class TemplatePreferences private constructor(private val dataStore: DataStore<Preferences>) {
}