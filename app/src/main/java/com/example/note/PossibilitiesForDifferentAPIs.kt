package com.example.note

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.fragment.app.Fragment

/**
 * API 24
 */

class PossibilitiesForDifferentAPIs: Fragment() {

    /**
     * @Added in API 23
     * @Deprecated in API 28
     */
    lateinit var fingerprintManager: FingerprintManager

    private val fingerprintAuthenticationCallback = @RequiresApi(Build.VERSION_CODES.M)
    object : FingerprintManager.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) { super.onAuthenticationError(errorCode, errString) }
        override fun onAuthenticationFailed() { super.onAuthenticationFailed() }
        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) { super.onAuthenticationSucceeded(result) }
        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) { super.onAuthenticationHelp(helpCode, helpString) }
    }

    /**
     * May be works prior to API 23
     */
    lateinit var fingerprintManagerCompat: FingerprintManagerCompat

    private val fingerprintManagerCompatCallback: FingerprintManagerCompat.AuthenticationCallback = object : FingerprintManagerCompat.AuthenticationCallback(){
        override fun onAuthenticationFailed() { super.onAuthenticationFailed() }
        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) { super.onAuthenticationError(errMsgId, errString) }
        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) { super.onAuthenticationSucceeded(result) }
        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) { super.onAuthenticationHelp(helpMsgId, helpString) }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fingerprintManager = requireActivity().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        fingerprintManager.hasEnrolledFingerprints()
        fingerprintManager.isHardwareDetected
        fingerprintManager.authenticate(null, null, 0, fingerprintAuthenticationCallback,null)

        fingerprintManagerCompat = FingerprintManagerCompat.from(requireContext())
        fingerprintManagerCompat.hasEnrolledFingerprints()
        fingerprintManagerCompat.isHardwareDetected
        fingerprintManagerCompat.authenticate(null, 0, null, fingerprintManagerCompatCallback,null)

    }
}