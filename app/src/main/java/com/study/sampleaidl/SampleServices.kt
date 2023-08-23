package com.study.sampleaidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SampleServices : Service() {

	private val iSampleAIDLServices = object : ISampleAIDLServices.Stub() {
		override fun getUserName(): String {
			Log.d(TAG, "threadName: ${Thread.currentThread().name}")
			return (0..10).random().toString()
		}
	}

	override fun onBind(intent: Intent?): IBinder? {
		return iSampleAIDLServices.asBinder()
	}

	companion object {
		const val TAG = "##SampleServicesLog"
	}
}