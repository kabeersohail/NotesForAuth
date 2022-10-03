package com.example.note

/**
 * Create instances
 *
 * val fingerprintManager: FingerprintManagerCompat = FingerprintManagerCompat.from(requireContext())
 * val fm: FingerprintManager = requireActivity().getSystemService(FINGERPRINT_SERVICE) as FingerprintManager

 */

/**
 * Result codes
 *
 *  RESULT_OK = -1;
 *  RESULT_FIRST_USER = 1;
 *  RESULT_CANCELED = 0;
 *  FINISH_TASK_WITH_ACTIVITY = 2;
 */

/**
 *
 * Important Classes
 *
 * FingerprintManager (@Added API level 23 @deprecated API level 28)
 * FingerprintManagerCompat
 * BiometricManager
 * KeyguardManager
 * BiometricPrompt
 * PromptInfo
 *
 */

/**
 *
 * Important methods
 *
 * FingerprintManager#hasEnrolledFingerprints()
 * BiometricManager#canAuthenticate(int)
 */

/**
 *
 * Important Intents
 *
 * ACTION_SET_NEW_PASSWORD (@Added in API level 8)
 * ACTION_SET_NEW_PARENT_PROFILE_PASSWORD
 * ACTION_BIOMETRIC_ENROLL
 * ACTION_SECURITY_SETTINGS
 * ACTION_FINGERPRINT_ENROLL (@Added in API level 28 @deprecated in API level 30)
 *
 */

/**
 * Authenticators
 *
 * BIOMETRIC_STRONG
 * BIOMETRIC_WEAK
 * DEVICE_CREDENTIAL
 *
 */

/**
 *
 * AuthenticationStatus
 *
 * BIOMETRIC_SUCCESS,
 * BIOMETRIC_STATUS_UNKNOWN,
 * BIOMETRIC_ERROR_UNSUPPORTED,
 * BIOMETRIC_ERROR_HW_UNAVAILABLE,
 * BIOMETRIC_ERROR_NONE_ENROLLED,
 * BIOMETRIC_ERROR_NO_HARDWARE,
 * BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED
 *
 */

/**
 * keyguardManager.createConfirmDeviceCredentialIntent()
 */