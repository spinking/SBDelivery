package studio.eyesthetics.sbdelivery.data.models.menu

data class MenuItem(
    val titleId: Int,
    val iconId: Int,
    val badgeCount: Int = -1,
    val destinationId: Int
)