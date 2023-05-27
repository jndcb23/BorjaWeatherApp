package com.example.borjaweatherapp.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateFormat
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.math.roundToInt

object CommonHelper {
    // decrypycode
    var passkeyreq: String = AppConstants.PASSKEYREQ
    fun decrypt(encryptedString: String?): String {
        val decoded: ByteArray
        var string = ""
        try {
            val iv = byteArrayOf(
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00
            )

            val key = SecretKeySpec(passkeyreq.toByteArray(StandardCharsets.UTF_8), "DESede")

            @SuppressLint("GetInstance") val cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
            decoded = Base64.decode(encryptedString, Base64.DEFAULT)
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decrypted = cipher.doFinal(decoded)
            string = String(decrypted)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return string
    }

    //encrypt code
    fun encrypt(unencryptedString: String): String? {
        var encryptedString: String? = null
        var encrypo: String? = null
        try {
            val iv = byteArrayOf(
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00
            )
            val key = SecretKeySpec(passkeyreq.toByteArray(StandardCharsets.UTF_8), "DESede")

            @SuppressLint("GetInstance") val cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val result = cipher.doFinal(unencryptedString.toByteArray(StandardCharsets.UTF_8))
            encryptedString = Base64.encodeToString(result, 0)
            encrypo = encryptedString
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return encrypo
    }

    fun kelvinToCelsius(kelvinValue: Double): Int {
        return (kelvinValue - 273.15).roundToInt()
    }

    fun utcTimeStampToLocal(utcTimeStamp: Int, timeZone: Int, formatTime: String = "hh:mm a"): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date =
                DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(utcTimeStamp.toLong()))
            val dateTime = ZonedDateTime.parse(date)

            val timezone = timeZone / 3600
            var timeZoneString = ""
            timeZoneString = if (timezone < 0) {
                "-$timezone"
            } else {
                "+$timezone"
            }

            return dateTime.withZoneSameInstant(ZoneId.of("GMT$timeZoneString"))
                .format(DateTimeFormatter.ofPattern(formatTime))
        } else {
            var date = ""
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            cal.timeInMillis = utcTimeStamp * 1000L
            date = DateFormat.format(formatTime, cal.timeInMillis).toString()
            val formatter = SimpleDateFormat(formatTime)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(date)
            val dateFormatter = SimpleDateFormat(formatTime)
            dateFormatter.timeZone = TimeZone.getDefault()
            return dateFormatter.format(value)
        }
    }

    fun stringToTime(timeValue: String): Boolean {
        val values = timeValue.split(" ")
        val time = values[0].split(":")
        return if (values[1] == "PM") {
            time[0].toInt() >= 6
        } else {
            time[0].toInt() < 6
        }
    }
}