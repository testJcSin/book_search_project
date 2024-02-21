package rjc.bookbackend.util

import rjc.bookbackend.consts.BookConst
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private lateinit var key: SecretKey

    fun setSecretKey(secretKey: String) {
        val keyBytes = secretKey.toByteArray(charset("UTF-8"))
        val keyLength = BookConst.KEY_LENGTH
        val keyBytesFixed = keyBytes.copyOf(keyLength)
        key = SecretKeySpec(keyBytesFixed, BookConst.ALGORITHM)
    }

    fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(BookConst.ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedValue = cipher.doFinal(value.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedValue)
    }

}
