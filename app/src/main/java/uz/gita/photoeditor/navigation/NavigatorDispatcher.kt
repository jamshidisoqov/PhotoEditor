package uz.gita.photoeditor.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

typealias NavigationArgs = NavController.() -> Unit

interface NavigatorDispatcher {
    val dispatcher: Flow<NavigationArgs>
}