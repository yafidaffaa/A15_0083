package com.yafidaffaaa.uas_pam.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yafidaffaaa.uas_pam.ui.view.HomeMonitoringView
import com.yafidaffaaa.uas_pam.ui.view.hewan.DetailScreen
import com.yafidaffaaa.uas_pam.ui.view.hewan.HomeScreen
import com.yafidaffaaa.uas_pam.ui.view.kandang.HomeScreenKandang
import com.yafidaffaaa.uas_pam.ui.view.hewan.InsertHwnScreen
import com.yafidaffaaa.uas_pam.ui.view.hewan.UpdateScreenHewan
import com.yafidaffaaa.uas_pam.ui.view.kandang.DetailScreenKandang
import com.yafidaffaaa.uas_pam.ui.view.kandang.InsertKandangScreen
import com.yafidaffaaa.uas_pam.ui.view.kandang.UpdateScreenKandang
import com.yafidaffaaa.uas_pam.ui.view.monitoring.DetailScreenMonitoring
import com.yafidaffaaa.uas_pam.ui.view.monitoring.HomeScreenMonitoring
import com.yafidaffaaa.uas_pam.ui.view.monitoring.InsertMonitoringScreen
import com.yafidaffaaa.uas_pam.ui.view.monitoring.UpdateScreenMonitoring
import com.yafidaffaaa.uas_pam.ui.view.petugas.DetailScreenPetugas
import com.yafidaffaaa.uas_pam.ui.view.petugas.HomeScreenPetugas
import com.yafidaffaaa.uas_pam.ui.view.petugas.InsertPtgScreen
import com.yafidaffaaa.uas_pam.ui.view.petugas.UpdateScreenPetugas

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiMonitoring.route,
        modifier = Modifier
    ) {
        composable(DestinasiMonitoring.route) {
            HomeMonitoringView(
                manageKandang = { navController.navigate(DestinasiHomeKandang.route) },
                manageHewan = { navController.navigate(DestinasiHome.route) },
                manageMonitoring = { navController.navigate(DestinasiHomeMonitoring.route) },
                managePetugas = { navController.navigate(DestinasiHomePetugas.route) }
            )
        }

        // PENGELOLA HALAMAN HEWAN
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToInsertHewan = { navController.navigate(DestinasiInsert.route) },
                onDetailClick = { navController.navigate("${DestinasiDetail.route}/$it") },
                navController = navController
            )
        }
        composable(DestinasiInsert.route) {
            InsertHwnScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiDetail.ID)
            id?.let { id ->
                DetailScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    onDeleteClick = { idHewan ->
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) {
                                inclusive = true
                            }
                        }
                    },
                    ID = id
                )
            }
        }
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiUpdate.ID)
            nim?.let { nim ->
                UpdateScreenHewan(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        // PENGELOLA HALAMAN PETUGAS

        composable(DestinasiHomePetugas.route) {
            HomeScreenPetugas(
                navigateToInsertPetugas = { navController.navigate(DestinasiInsertPetugas.route) },
                onDetailClick = { navController.navigate("${DestinasiDetailPetugas.route}/$it") },
                navController = navController
            )
        }
        composable(DestinasiInsertPetugas.route) {
            InsertPtgScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePetugas.route) {
                        popUpTo(DestinasiHomePetugas.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailPetugas.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPetugas.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val idPetugas = it.arguments?.getString(DestinasiDetailPetugas.ID)
            idPetugas?.let { idPetugas ->
                DetailScreenPetugas(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePetugas.route}/$it")
                    },
                    onDeleteClick = { idPetugas ->
                        navController.navigate(DestinasiHomePetugas.route) {
                            popUpTo(DestinasiHomePetugas.route) {
                                inclusive = true
                            }
                        }
                    },
                    ID = idPetugas
                )
            }
        }
        composable(
            DestinasiUpdatePetugas.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePetugas.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val idPetugas = it.arguments?.getString(DestinasiUpdatePetugas.ID)
            idPetugas?.let { idPetugas ->
                UpdateScreenPetugas(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.navigate(DestinasiHomePetugas.route) {
                            popUpTo(DestinasiHomePetugas.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        // PENGELOLA KANDANG
        composable(DestinasiHomeKandang.route) {
            HomeScreenKandang(
                navigateToInsertKandang = { navController.navigate(DestinasiInsertKandang.route) },
                onDetailClick = { navController.navigate("${DestinasiDetailKandang.route}/$it") },
                navController = navController
            )
        }
        composable(DestinasiInsertKandang.route) {
            InsertKandangScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKandang.route) {
                        popUpTo(DestinasiHomeKandang.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailKandang.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailKandang.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val idKandang = it.arguments?.getString(DestinasiDetailKandang.ID)
            idKandang?.let { idKandang ->
                DetailScreenKandang(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateKandang.route}/$it")
                    },
                    onDeleteClick = { idKandang ->
                        navController.navigate(DestinasiHomeKandang.route) {
                            popUpTo(DestinasiHomeKandang.route) {
                                inclusive = true
                            }
                        }
                    },
                    ID = idKandang
                )
            }
        }
        composable(
            DestinasiUpdateKandang.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateKandang.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val idKandang = it.arguments?.getString(DestinasiUpdateKandang.ID)
            idKandang?.let { idKandang ->
                UpdateScreenKandang(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.navigate(DestinasiHomeKandang.route) {
                            popUpTo(DestinasiHomeKandang.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        // PENGELOLA MONITORING

        composable(DestinasiHomeMonitoring.route) {
            HomeScreenMonitoring(
                navigateToInsertMonitoring = { navController.navigate(DestinasiInsertMonitoring.route) },
                onDetailClick = { navController.navigate("${DestinasiDetailMonitoring.route}/$it") },
                navController = navController
            )
        }
        composable(DestinasiInsertMonitoring.route) {
            InsertMonitoringScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeMonitoring.route) {
                        popUpTo(DestinasiHomeMonitoring.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailMonitoring.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailMonitoring.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val idMonitoring = it.arguments?.getString(DestinasiDetailMonitoring.ID)
            idMonitoring?.let { idMonitoring ->
                DetailScreenMonitoring(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMonitoring.route}/$it")
                    },
                    onDeleteClick = { idMonitoring ->
                        navController.navigate(DestinasiHomeMonitoring.route) {
                            popUpTo(DestinasiHomeMonitoring.route) {
                                inclusive = true
                            }
                        }
                    },
                    ID = idMonitoring
                )
            }
        }
        composable(
            DestinasiUpdateMonitoring.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateMonitoring.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val idMonitoring = it.arguments?.getString(DestinasiUpdateMonitoring.ID)
            idMonitoring?.let { id ->
                UpdateScreenMonitoring(
                    idMonitoring = id,
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.navigate(DestinasiHomeMonitoring.route) {
                            popUpTo(DestinasiHomeMonitoring.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}