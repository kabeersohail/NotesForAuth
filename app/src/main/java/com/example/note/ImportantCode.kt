package com.example.note

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.fragment.app.Fragment

class ImportantCode : Fragment() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fingerprintManager: FingerprintManager = requireActivity().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        importantMethodsInFingerprintManager(fingerprintManager)

        val fingerprintManagerCompat: FingerprintManagerCompat = FingerprintManagerCompat.from(requireContext())
        importantMethodsInFingerprintManagerCompat(fingerprintManagerCompat)

        val keyguardManager = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        importantMethodsForKeyguardManager(keyguardManager)
        createConfirmCredentialIntent(keyguardManager)

        fingerPrintEnrollmentIntroductionForPixel24()
        requestUseBiometricPermission()
        requestUseFingerPrintPermission()
        requestHardwareFingerprintPermission()
        checkSelfPermission(Manifest.permission.USE_FINGERPRINT)
        checkSelfPermission(Manifest.permission.USE_BIOMETRIC)
        otherWayToRequestPermission(Manifest.permission.USE_FINGERPRINT)
        otherWayToRequestPermission(Manifest.permission.USE_BIOMETRIC)
        actionSetNewPassword()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun importantMethodsForKeyguardManager(keyguardManager: KeyguardManager) {
        keyguardManager.isKeyguardLocked
        keyguardManager.isKeyguardSecure
        keyguardManager.isDeviceLocked
        keyguardManager.isDeviceSecure
    }

    private val onConfirmDeviceCredential = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            log("success")
        }
    }

    private fun createConfirmCredentialIntent(keyguardManager: KeyguardManager){
        val confirmDeviceCredentialIntent: Intent = keyguardManager.createConfirmDeviceCredentialIntent("Title", "description")
        onConfirmDeviceCredential.launch(confirmDeviceCredentialIntent)
    }

    private val fingerprintManagerCompatCallback: FingerprintManagerCompat.AuthenticationCallback = object : FingerprintManagerCompat.AuthenticationCallback(){
        override fun onAuthenticationFailed() { super.onAuthenticationFailed() }
        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) { super.onAuthenticationError(errMsgId, errString) }
        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) { super.onAuthenticationSucceeded(result) }
        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) { super.onAuthenticationHelp(helpMsgId, helpString) }
    }

    private val fingerprintAuthenticationCallback = @RequiresApi(Build.VERSION_CODES.M)
    object : FingerprintManager.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) { super.onAuthenticationError(errorCode, errString) }
        override fun onAuthenticationFailed() { super.onAuthenticationFailed() }
        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) { super.onAuthenticationSucceeded(result) }
        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) { super.onAuthenticationHelp(helpCode, helpString) }
    }


    private fun importantMethodsInFingerprintManagerCompat(fingerprintManagerCompat: FingerprintManagerCompat) {
        fingerprintManagerCompat.hasEnrolledFingerprints()
        fingerprintManagerCompat.isHardwareDetected
        fingerprintManagerCompat.authenticate(null, 0, null, fingerprintManagerCompatCallback,null)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun importantMethodsInFingerprintManager(fingerprintManager: FingerprintManager) {
        fingerprintManager.hasEnrolledFingerprints()
        fingerprintManager.isHardwareDetected
        fingerprintManager.authenticate(null, null, 0, fingerprintAuthenticationCallback,null)
    }


    private fun otherWayToRequestPermission(permission: String) = ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 5);


    /**
     *
     * On API level 24 ->
     * Permission Denial: starting Intent { cmp=com.android.settings/.fingerprint.FingerprintEnrollIntroduction }
     * from ProcessRecord{351bbc1 3635:com.example.newbiometriclibrary/u0a78} (pid=3635, uid=10078) not exported from uid 1000
     *
     * on API level 25 -> same issue as others
     *
     * in API level 26 ->
     * Permission Denial: starting Intent { cmp=com.android.settings/.fingerprint.FingerprintEnrollIntroduction }
     * from ProcessRecord{59513fa 4228:com.example.newbiometriclibrary/u0a83} (pid=4228, uid=10083) not exported from uid 1000
     *
     * in API level 27 ->
     * Permission Denial: starting Intent { cmp=com.android.settings/.fingerprint.FingerprintEnrollIntroduction }
     * from ProcessRecord{c1ec1c1 4469:com.example.newbiometriclibrary/u0a80} (pid=4469, uid=10080) not exported from uid 1000
     *
     * in API level 28 -> launches finger print enroll
     *
     */
    private fun fingerPrintEnrollmentIntroductionForPixel24() {
        val componentName = ComponentName("com.android.settings", "com.android.settings.fingerprint.FingerprintEnrollIntroduction")
        val intent = Intent().apply { component = componentName }
        startActivity(intent)
    }

    private fun checkSelfPermission(permission: String) =
        ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED


    private val useFingerprint = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) log("granted") else log("not granted")
    }

    private fun requestUseFingerPrintPermission() = useFingerprint.launch(Manifest.permission.USE_FINGERPRINT)

    private val useBiometric = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) log("granted") else log("not granted")
    }

    private fun requestUseBiometricPermission() = useBiometric.launch(Manifest.permission.USE_BIOMETRIC)

    private val hardwareFingerprint = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) log("granted") else log("not granted")
    }

    private fun requestHardwareFingerprintPermission() = hardwareFingerprint.launch("android.hardware.fingerprint")

    private fun log(message: String) {
        Log.d("TAG", message)
    }

    private val onsetNewPassword = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK || it.resultCode == Activity.RESULT_FIRST_USER){
            log("onSetNewPassword success")
        } else if(it.resultCode == Activity.RESULT_CANCELED){
            log("onsetNewPassword cancelled")
        }
    }

    /**
     * Added in API level 8
     *
     * Shows non-bio metric lock screen settings on Pixel API 24
     * Shows fingerprint + Pattern, PIN, Password on pixel API 25
     * Shows fingerprint + Pattern, PIN, Password on pixel API 26
     * Shows fingerprint + Pattern, PIN, Password on pixel API 27

     *
     */
    private fun actionSetNewPassword(){
        val setNewPasswordIntent = Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD)
        onsetNewPassword.launch(setNewPasswordIntent)
    }

    /**
     * Error on API 28
     *
     * Permission Denial: starting Intent { cmp=com.android.settings/.password.ChooseLockGeneric } from
     * ProcessRecord{fd95140 7182:com.example.newbiometriclibrary/u0a86} (pid=7182, uid=10086) not exported from uid 1000
     */
    private fun chooseLockGeneric(){
        val componentName = ComponentName("com.android.settings", "com.android.settings.password.ChooseLockGeneric")
        val intent = Intent().apply { component = componentName }
        startActivity(intent)
    }

    /**
     * shows non-biometric enroll on API 28
     */
    private fun setupChooseLockGeneric(){
        val componentName = ComponentName("com.android.settings", "com.android.settings.password.SetupChooseLockGeneric")
        val intent = Intent().apply { component = componentName }
        startActivity(intent)
    }

    /**
     * On API level 28 -> No Activity found to handle Intent { act=android.settings.FINGERPRINT_SETTINGS }
     */

    private fun fingerprintSettingsIntent(){
        val intent = Intent("android.settings.FINGERPRINT_SETTINGS")
        startActivity(intent)
    }

    /**
     * Added in api level 28
     * deprecated in api level 30
     */
    private fun possibilitiesForAPI28() {
        log("possibilitiesForAPI28()")
        log("ACTION_BIOMETRIC_ENROLL not supported on API ${Build.VERSION.SDK_INT} , supports from API 30 ")

        val fingerprintEnrollIntent = Intent(Settings.ACTION_FINGERPRINT_ENROLL)
        log("Launching ACTION_FINGERPRINT_ENROLL")
        onFingerprintEnrollResult.launch(fingerprintEnrollIntent)
    }

    private val onFingerprintEnrollResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            log("onFingerprintEnrollResult")
        } else {
            log("onFingerprintEnrollResult cancelled")
        }
    }

}