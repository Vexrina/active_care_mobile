package com.example.activecare.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.activecare.cache.domain.Cache
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.home.ui.HomeScreen
import com.example.activecare.screens.home.ui.HomeViewModel
import com.example.activecare.screens.networkerror.NetworkErrorScreen
import com.example.activecare.screens.onboard.ui.OnboardScreen
import com.example.activecare.screens.onboard.ui.OnboardViewModel
import com.example.activecare.screens.person.ui.PersonScreen
import com.example.activecare.screens.person.ui.PersonViewModel
import com.example.activecare.screens.signin.ui.LoginScreen
import com.example.activecare.screens.signin.ui.LoginViewModel
import com.example.activecare.screens.splash.SplashScreen
import com.example.activecare.screens.splash.SplashViewModel
import com.example.activecare.screens.workout.ui.WorkoutScreen
import com.example.activecare.screens.workout.ui.WorkoutViewModel

@Composable
fun CreateNavHost(
    navController: NavHostController,
    apiService: ApiService,
    cache: Cache,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationTree.Splash.name
    ) {
        composable(NavigationTree.Splash.name) {
            val splashViewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                viewModel = splashViewModel,
                navController = navController
            )
        }
        composable(NavigationTree.Onboard.name) {
            val onboardViewModel = hiltViewModel<OnboardViewModel>()
            OnboardScreen(
                onboardViewModel = onboardViewModel,
                navController = navController,
            )
        }
        composable(
            "${NavigationTree.Login.name}/{signIn}",
            arguments = listOf(navArgument("signIn") { type = NavType.BoolType })
        ) {
            val loginViewModel =
                hiltViewModel<LoginViewModel, LoginViewModel.LoginViewModelFactory>(
                    creationCallback = { loginViewModelFactory ->
                        loginViewModelFactory.create(
                            signIn = it.arguments?.getBoolean("signIn")!!,
                            apiService = apiService
                        )
                    }
                )
            LoginScreen(
                loginViewModel, navController
            )
        }
        composable(NavigationTree.Me.name) {
            val personViewModel = hiltViewModel<PersonViewModel>()
            PersonScreen(
                personViewModel = personViewModel,
                navController = navController,
            )
        }
        composable(NavigationTree.Home.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                homeViewModel = homeViewModel,
                navController = navController
            )
        }
        composable(NavigationTree.Workout.name){
            val workoutViewModel = hiltViewModel<WorkoutViewModel>()
            WorkoutScreen(
                workoutViewModel = workoutViewModel,
                navController = navController
            )
        }
        composable(NavigationTree.NetworkError.name){
            NetworkErrorScreen()
        }
    }
}