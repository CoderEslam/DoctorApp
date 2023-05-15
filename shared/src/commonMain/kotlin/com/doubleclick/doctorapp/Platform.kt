package com.doubleclick.doctorapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform