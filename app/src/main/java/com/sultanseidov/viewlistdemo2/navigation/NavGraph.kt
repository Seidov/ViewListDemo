package com.sultanseidov.viewlistdemo2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


/**
 * Destinations used in the ([ViewList]).
 */
object MainDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val HOME_DISCOVER_ROUTE = "homeDiscover"
    const val DETAIL_DISCOVER_ROUTE = "detailDiscover"
    const val DETAIL_DISCOVER_ID_KEY = "detailDiscoverId"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_DISCOVER_ROUTE,
    showOnboardingInitially: Boolean = false
) {
    // Onboarding could be read from shared preferences.
    val onboardingComplete = remember(showOnboardingInitially) {
        mutableStateOf(!showOnboardingInitially)
    }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        /*
        composable(MainDestinations.ONBOARDING_ROUTE) {
            // Intercept back in Onboarding: make it finish the activity
            BackHandler {
                finishActivity()
            }

            Onboarding(
                onboardingComplete = {
                    // Set the flag so that onboarding is not shown next time.
                    onboardingComplete.value = true
                    actions.onboardingComplete()
                }
            )
        }

         */
        navigation(
            route = MainDestinations.HOME_DISCOVER_ROUTE,
            startDestination = Tabs.DISCOVER.route
        ) {
            home(
                onCourseSelected = actions.openCourse,
                onBoardingComplete = onboardingComplete,
                navController = navController,
                modifier = modifier
            )
        }

        /*
        composable(
            "${MainDestinations.DETAIL_ROUTE}/{$DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(DETAIL_ID_KEY) { type = NavType.LongType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val currentCourseId = arguments.getLong(DETAIL_ID_KEY)
            CourseDetails(
                courseId = currentCourseId,
                selectCourse = { newCourseId ->
                    actions.relatedCourse(newCourseId, backStackEntry)
                },
                upPress = { actions.upPress(backStackEntry) }
            )
        }

         */
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onboardingComplete: () -> Unit = {
        navController.popBackStack()
    }

    // Used from HOME_DISCOVER_ROUTE
    val openCourse = { newDiscoverId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.DETAIL_DISCOVER_ROUTE}/$newDiscoverId")
        }
    }

    // Used from DETAIL_DISCOVER_ROUTE
    val relatedCourse = { newDiscoverId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.DETAIL_DISCOVER_ROUTE}/$newDiscoverId")
        }
    }

    // Used from DETAIL_DISCOVER_ROUTE
    val upPress: (from: NavBackStackEntry) -> Unit = { from ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
