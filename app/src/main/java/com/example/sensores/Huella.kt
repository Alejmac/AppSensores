package com.example.sensores

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Message
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

class Huella : AppCompatActivity() {
    private var  cancellationSignal :CancellationSignal? = null
    private lateinit var btn_authenticate : Button

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
    get() =
        @RequiresApi(Build.VERSION_CODES.P)
        object :BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("error con la autentification : $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("autentificacion exitosa ")
                startActivity(Intent(this@Huella,secreta::class.java))
            }
        }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huella)

        checkBiometrySuport()

        btn_authenticate= findViewById(R.id.btn_authenticate)
        btn_authenticate.setOnClickListener {
                val biometricPrompt:BiometricPrompt =  BiometricPrompt.Builder(this)
                    .setTitle("Se esta detectando la huella ")
                    .setSubtitle("colocar el dedo justo en el sensor tactilar ")
                    .setDescription("app de autentificacion")
                    .setNegativeButton("cancelado" ,this.mainExecutor,DialogInterface.OnClickListener{dialog,whitch->
                        notifyUser("autentificacion can")
                    }).build()
            biometricPrompt.authenticate(getCancellationSignal(),mainExecutor,authenticationCallback)
        }
    }
    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun getCancellationSignal():CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("la autentificacino fue cancelada por el usuario")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometrySuport():Boolean {
        val keyguardManager :KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE)as KeyguardManager

        if (!keyguardManager.isKeyguardSecure){
            notifyUser("fallo el check")
            return false
        }
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            notifyUser("el permiso  no es valido ")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_PRINTING)){
            true
        }else true
    }
}