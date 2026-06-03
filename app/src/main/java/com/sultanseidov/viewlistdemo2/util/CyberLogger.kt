package com.sultanseidov.viewlistdemo2.util

import android.util.Log
import com.sultanseidov.viewlistdemo2.BuildConfig

object CyberLogger {
    private const val TAG = "CyberTerminal"

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "$tag $message")
        }
    }

    fun logNeuralSeed(title: String, genres: List<Int>) {
        if (!BuildConfig.DEBUG) return
        val block = """
            ┌──────────────────────────────────────────────────────────┐
            │ [✦ NEURAL SEED] :: INITIALIZING ROOT CLUSTER             │
            ├──────────────────────────────────────────────────────────┤
            │ TITLE: $title                                            │
            │ GENRES: ${genres.joinToString(", ")}                     │
            └──────────────────────────────────────────────────────────┘
        """.trimIndent()
        Log.d(TAG, "\n$block")
    }

    fun logPipelineIngest(movieId: Long, title: String, genres: List<Int>, profile: List<String>) {
        if (!BuildConfig.DEBUG) return
        val block = """
            ┌──────────────────────────────────────────────────────────┐
            │ [🎛️ PIPELINE_INGEST] :: MOVIE ID: $movieId :: VERSION: 2  │
            ├──────────────────────────────────────────────────────────┤
            │ TITLE: $title                                            │
            │ GENRES: ${genres.joinToString(", ")}                     │
            │ SEMANTIC PROFILE: ${profile.joinToString(" | ")}         │
            └──────────────────────────────────────────────────────────┘
        """.trimIndent()
        Log.d(TAG, "\n$block")
    }

    fun logMemoryStore(movieId: Long, title: String) {
        if (!BuildConfig.DEBUG) return
        Log.d(TAG, "[💾 MEMORY_STORE] -> COMMITTED IMMUTABLE FOOTPRINT: $title (ID: $movieId)")
    }

    fun logClusterMatch(pinTitle: String, score: Double, threshold: Double) {
        if (!BuildConfig.DEBUG) return
        val match = if (score >= threshold) "MATCH: TRUE" else "MATCH: FALSE"
        Log.d(TAG, "[🛰️ CLUSTER_MATCH] -> PIN: '$pinTitle' | OVERLAP: ${"%.2f".format(score)} | THRESHOLD: $threshold -> [$match]")
    }

    fun logRebuildCache(pinId: Long, title: String, isNew: Boolean) {
        if (!BuildConfig.DEBUG) return
        val action = if (isNew) "BURST NEW CLUSTER" else "MODIFIED EXISTING PIN"
        Log.d(TAG, "[⚡ REBUILD_CACHE] -> $action :: ID: $pinId :: TITLE: $title")
    }

    suspend inline fun <T> traceNetworkExecution(operationName: String, crossinline block: suspend () -> T): T {
        if (!BuildConfig.DEBUG) return block()
        val startTime = System.currentTimeMillis()
        return try {
            val result = block()
            val endTime = System.currentTimeMillis()
            logNetFetch(operationName, "SUCCESS", endTime - startTime)
            result
        } catch (e: Exception) {
            val endTime = System.currentTimeMillis()
            logNetFetch(operationName, "FAILURE: ${e.message}", endTime - startTime)
            throw e
        }
    }

    fun logNetFetch(operation: String, status: String, latencyMs: Long) {
        if (!BuildConfig.DEBUG) return
        val block = """
            ┌──────────────────────────────────────────────────────────┐
            │ 📡 NET_FETCH :: OPERATION: $operation                  │
            ├──────────────────────────────────────────────────────────┤
            │ STATUS: $status                                          │
            │ LATENCY: $latencyMs ms                                   │
            └──────────────────────────────────────────────────────────┘
        """.trimIndent()
        Log.d(TAG, "\n$block")
    }

    fun logDiscoverLoadState(message: String) {
        if (!BuildConfig.DEBUG) return
        Log.d(TAG, "[📡 NET_FETCH] -> DISCOVER GRID STATE: $message")
    }
}
