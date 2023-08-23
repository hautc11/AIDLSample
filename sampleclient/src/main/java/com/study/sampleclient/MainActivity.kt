package com.study.sampleclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.study.sampleaidl.ISampleAIDLServices

class MainActivity : AppCompatActivity() {

	private lateinit var iSampleAIDLServices: ISampleAIDLServices

	private val mConnection = object : ServiceConnection {
		override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
			iSampleAIDLServices = ISampleAIDLServices.Stub.asInterface(service)
			Log.d("##MainActivity-Client", "Connect to remote!")
		}

		override fun onServiceDisconnected(name: ComponentName?) {
			Log.d("##MainActivity-Client", "Disconnect from remote!")
		}

	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//`action` for Intent is the same as `Manifest.service.intent-filter.action.android:name` in ServerSide app.
		val intent = Intent("ISampleAIDLService").also {
			//This is package name of `SampleServices.kt` in server side.
			// If we don't set packet for intent it will throw `java.lang.IllegalArgumentException: Service Intent must be explicit`
			it.setPackage("com.study.sampleaidl")
		}

		bindService(intent, mConnection, BIND_AUTO_CREATE)

		findViewById<MaterialButton>(R.id.btnGetFromServer).setOnClickListener {
			findViewById<TextView>(R.id.tvResultResponseFromServerApp).text = iSampleAIDLServices.userName
		}
	}
}