package com.app.veggiefood.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Patterns
import android.widget.Toast
import com.app.veggiefood.R

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For 29 api or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
    // For below 29 api
    else {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

fun isValidFirstName(context: Context, edtFirstName: String): Boolean {
    var isValid = true
    if (edtFirstName.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_first_name),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidLastName(context: Context, edtLastName: String): Boolean {
    var isValid = true
    if (edtLastName.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_last_name),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}

fun isValidPhoneNumber(context: Context, edtPhoneNumber: String): Boolean {
    var isValid = true
    if (edtPhoneNumber.isEmpty() || edtPhoneNumber.length < 10) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_valid_phone_number),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidateEmail(context: Context, edtEmail: String): Boolean {
    var isValid = true
    if (edtEmail.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_email_address),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.toString().trim { it <= ' ' })
            .matches()
    ) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_valid_email_address),
            Toast.LENGTH_SHORT
        ).show()

        isValid = false
    }
    return isValid
}


fun isValidPassword(context: Context, edtPassword: String): Boolean {
    var isValid = true
    if (edtPassword.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_password),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidConfirmPassword(context: Context, edtConfirmPassword: String): Boolean {
    var isValid = true
    if (edtConfirmPassword.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_confirm_password),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidPasswordAndConfirmPassword(
    context: Context,
    edtPassword: String,
    edtConfirmPassword: String
): Boolean {
    var isValid = true
    if (edtPassword != edtConfirmPassword) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_password_confirm_password),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidAddress(context: Context, edtAddress: String): Boolean {
    var isValid = true
    if (edtAddress.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_enter_address),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidState(context: Context, edtState: String): Boolean {
    var isValid = true
    if (edtState.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_enter_state),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}

fun isValidCity(context: Context, edtCity: String): Boolean {
    var isValid = true
    if (edtCity.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_enter_city),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}

fun isValidPinCode(context: Context, edtPinCode: String): Boolean {
    var isValid = true
    if (edtPinCode.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_enter_pincode),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}

fun isValidOldPassword(context: Context, edtOldPassword: String): Boolean {
    var isValid = true
    if (edtOldPassword.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_old_password),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidNewPassword(context: Context, edtNewPassword: String): Boolean {
    var isValid = true
    if (edtNewPassword.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_new_password),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}


fun isValidConfirmNewPassword(context: Context, edtConfrimNewPassword: String): Boolean {
    var isValid = true
    if (edtConfrimNewPassword.isEmpty()) {
        Toast.makeText(
            context,
            context.getString(R.string.str_error_confirm_new_password),
            Toast.LENGTH_SHORT
        ).show()
        isValid = false
    }
    return isValid
}

fun isSelectPaymentMethod(context: Context, payment: String): Boolean {
    var isValid = true
    if (payment.isEmpty()) {
        Toast.makeText(context, "Please select payment method", Toast.LENGTH_SHORT).show()
        isValid = false
    }
    return isValid
}


