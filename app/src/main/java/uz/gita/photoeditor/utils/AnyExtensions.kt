package uz.gita.photoeditor.utils

import timber.log.Timber


/**
 * get value, if null return default
 * */
fun <T> T?.getOrDefault(default: T) = this ?: default
fun <T> T?.required() = this!!

/**
 * check not null
 * */
val Any?.isNotNull get() = this != null

val Any?.isNull get() = this == null

fun isNullAll(vararg args: Any?): Boolean = args.all { it != null }

fun isNullAny(vararg args: Any?): Boolean = args.any { it != null }

fun timberLog(text: String) = Timber.d(text)

fun timberLog(text: String, tag: String) = Timber.tag(tag).d(text)

