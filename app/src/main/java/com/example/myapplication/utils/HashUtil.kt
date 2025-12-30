package com.example.myapplication.utils

import org.mindrot.jbcrypt.BCrypt

class HashUtil {
    companion object {
        fun hashPassword(pass: String): String {
            return BCrypt.hashpw(pass, BCrypt.gensalt())
        }

        fun checkPassword(pass: String, hashed: String): Boolean {
            return try {
                BCrypt.checkpw(pass, hashed)
            } catch (e: Exception) {
                false
            }
        }
    }
}