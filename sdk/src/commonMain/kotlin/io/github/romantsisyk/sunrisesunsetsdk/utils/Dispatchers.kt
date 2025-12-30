package io.github.romantsisyk.sunrisesunsetsdk.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Platform-specific IO dispatcher.
 * On JVM/Android: Dispatchers.IO
 * On iOS/Native: Dispatchers.Default (no IO dispatcher available)
 */
internal expect val ioDispatcher: CoroutineDispatcher
