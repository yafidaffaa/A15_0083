package com.yafidaffaaa.uas_pam.ui.navigation

interface DestinationNavigation {
    val route: String
    val titleRes: String
}

object DestinasiMonitoring : DestinationNavigation {
    override val route = "home monitoring"
    override val titleRes = "Monitoring"
}

// DESTINASI HEWAN
object DestinasiHome : DestinationNavigation {
    override val route = "home hewan"
    override val titleRes = "Home Hewan"
}

object DestinasiInsert : DestinationNavigation {
    override val route = "insert hewan"
    override val titleRes = "Insert Data Hewan"
}

object DestinasiDetail : DestinationNavigation {
    override val route = "detail/{ID}"
    override val titleRes = "Detail Data Hewan"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiDelete : DestinationNavigation {
    override val route = "delete/{ID}"
    override val titleRes = "Delete Data Hewan"
    const val ID = "ID"
    val routeWithArgs = "$route/{ID}"
}

object DestinasiUpdate: DestinationNavigation {
    override val route = "Update/{ID}"
    override val titleRes = "Update Data Hewan"
    const val ID = "ID"
    val routesWithArg = "$route/{$ID}"
}


// DESTINASI PETUGAS
object DestinasiHomePetugas : DestinationNavigation {
    override val route = "home petugas"
    override val titleRes = "Home Petugas"
}

object DestinasiInsertPetugas : DestinationNavigation {
    override val route = "insert petugas"
    override val titleRes = "Insert Data Petugas"
}

object DestinasiDetailPetugas : DestinationNavigation {
    override val route = "detail/petugas/{ID}"
    override val titleRes = "Detail Data Petugas"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiDeletePetugas : DestinationNavigation {
    override val route = "delete/petugas/{ID}"
    override val titleRes = "Delete Data Petugas"
    const val ID = "ID"
    val routeWithArgs = "$route/{ID}"
}

object DestinasiUpdatePetugas: DestinationNavigation {
    override val route = "Update/petugas/{ID}"
    override val titleRes = "Update Data Petugas"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

// DESTINASI KANDANG

object DestinasiHomeKandang : DestinationNavigation {
    override val route = "home kandang"
    override val titleRes = "Home Kandang"
}

object DestinasiInsertKandang : DestinationNavigation {
    override val route = "insert kandang"
    override val titleRes = "Insert Data Kandang"
}

object DestinasiDetailKandang : DestinationNavigation {
    override val route = "detail_kandang/{ID}"
    override val titleRes = "Detail Data Kandang"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiDeleteKandang : DestinationNavigation {
    override val route = "delete_kandang/{ID}"
    override val titleRes = "Delete Data Kandang"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiUpdateKandang : DestinationNavigation {
    override val route = "update_kandang/{ID}"
    override val titleRes = "Update Data Kandang"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

// DESTINASI MONITORING

object DestinasiHomeMonitoring : DestinationNavigation {
    override val route = "home_monitoring"
    override val titleRes = "Home Monitoring"
}

object DestinasiInsertMonitoring : DestinationNavigation {
    override val route = "insert_monitoring"
    override val titleRes = "Insert Data Monitoring"
}

object DestinasiDetailMonitoring : DestinationNavigation {
    override val route = "detail_monitoring/{ID}"
    override val titleRes = "Detail Data Monitoring"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiDeleteMonitoring : DestinationNavigation {
    override val route = "delete_monitoring/{ID}"
    override val titleRes = "Delete Data Monitoring"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}

object DestinasiUpdateMonitoring : DestinationNavigation {
    override val route = "update_monitoring/{ID}"
    override val titleRes = "Update Data Monitoring"
    const val ID = "ID"
    val routeWithArgs = "$route/{$ID}"
}