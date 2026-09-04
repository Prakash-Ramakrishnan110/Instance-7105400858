package com.example.config

import androidx.compose.ui.graphics.Color

data class SchoolConfig(
    val schoolName: String = "Prakash International School",
    val shortName: String = "PIS",
    val tagline: String = "Learn • Grow • Succeed",
    val academicYear: String = "2026-27",
    val primaryColorHex: String = "#1E40AF",
    val secondaryColorHex: String = "#F59E0B",
    val address: String = "42, Anna Nagar Main Road, Chennai, Tamil Nadu 600040",
    val phone: String = "+91 98400 12345",
    val email: String = "contact@prakashschool.edu.in",
    val website: String = "www.prakashschool.edu.in",
    val affiliation: String = "CBSE Affiliated • Code: 1930482",
    val principalName: String = "Dr. R. Meenakshi Sundaram, Ph.D.",
    val poweredBy: String = "Powered by Fusion Engine Technology"
) {
    val primaryColor: Color
        get() = parseHexColor(primaryColorHex, Color(0xFF1E40AF))

    val secondaryColor: Color
        get() = parseHexColor(secondaryColorHex, Color(0xFFF59E0B))

    companion object {
        fun colorFromHex(hex: String): Color = parseHexColor(hex, Color(0xFF1E40AF))

        fun parseHexColor(hex: String, fallback: Color): Color {
            return try {
                val cleanHex = hex.removePrefix("#").trim()
                val colorLong = when (cleanHex.length) {
                    6 -> ("FF$cleanHex").toLong(16)
                    8 -> cleanHex.toLong(16)
                    else -> return fallback
                }
                Color(colorLong)
            } catch (e: Exception) {
                fallback
            }
        }
    }
}
