package uz.gita.photoeditor.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorDispatcherImpl @Inject constructor() : Navigator, NavigatorDispatcher {
    override val dispatcher = MutableSharedFlow<NavigationArgs>()

    private suspend fun navigator(args: NavigationArgs) {
        dispatcher.emit(args)
    }

    override suspend fun navigateTo(action: NavDirections) = navigator {
        navigate(action)
    }

    override suspend fun back() = navigator {
        navigateUp()
    }

}
