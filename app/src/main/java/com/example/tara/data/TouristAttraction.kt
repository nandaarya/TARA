package com.example.tara.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TouristAttraction(
    val id: Int = 0,
    val locationName: String,
    val description: String,
    val photoUrl: String,
    val rating: Double,
    val userRatingsTotal: Int,
    val lat: Double,
    val lon: Double,
): Parcelable

object DataDummy {
    val touristAttractionList = listOf(
        TouristAttraction(
            1,
            "Malioboro",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            "https://asset.kompas.com/crops/MUE-eoOIx_KuiklsCUtdxaOyw5M=/0x0:749x500/750x500/data/photo/2022/05/16/62822daaa7f76.png",
            4.5,
            9999,
            -7.793033,
            110.365831
        ),
        TouristAttraction(
            2,
            "Pantai Parangtritis",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            "https://bob.kemenparekraf.go.id/wp-content/uploads/2019/10/parangtritis.jpg",
            4.8,
            19293,
            -8.024535,
            110.330018
        ),
        TouristAttraction(
            3,
            "Puncak Sosok",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            "https://www.goodnewsfromindonesia.id/uploads/post/large-puncak-sosok-wisataoke-dot-com-a87fe329452d623d3aedd439ddf002fa.jpg",
            4.9,
            283929,
            -7.871701,
            110.426220
        ),
        TouristAttraction(
            4,
            "HeHa Sky View",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            "https://ds393qgzrxwzn.cloudfront.net/cat1/img/images/0/6TYQBHAWMX.jpg",
            4.3,
            10292,
            -7.849588,
            110.478452
        ),
    )
}
