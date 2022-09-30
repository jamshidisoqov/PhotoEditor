package uz.gita.photoeditor.navigation

import androidx.navigation.NavDirections

interface Navigator {
    suspend fun navigateTo(navDirections: NavDirections)
    suspend fun back()
}