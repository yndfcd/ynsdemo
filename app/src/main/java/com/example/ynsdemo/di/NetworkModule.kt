package com.example.ynsdemo.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            defaultRequest {
                header("apiSeed", "2")
                header("Accept-Language", "en-US,en;q=0.8")
                header("Platform", "android")
                header("RegionType", "GLOBAL")
                header("AnonymousToken", "58e58f1a-7084-4db9-94f6-781bf443d9d7")
                header("IH-Pref", "lc=en-US;cc=USD;ctc=US;wp=pounds")
                header(
                    "Pref",
                    "{"ctc":"US","crc":"USD","crs":"2","lac":"en-US","pc":"92571","storeid":0,"som":"pounds"}"
                )
                header("AppV", "905")
                header(
                    "User-Agent",
                    "iHerbMobile/11.9.0905.905 (Android 11; google sdk_gphone_arm64; GLOBAL)"
                )
            }
            expectSuccess = false // Allow handling non-2xx responses
        }
    }
}
