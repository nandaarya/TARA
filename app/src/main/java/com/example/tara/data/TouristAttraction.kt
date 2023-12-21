package com.example.tara.data

import android.os.Parcelable
import com.example.tara.data.response.ListTouristAttractionItem
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
        ListTouristAttractionItem(
            "1",
            "Malioboro",
            "Jalan Malioboro terkenal dengan para pedagang kaki lima yang menjajakan kerajinan khas Jogja dan warung-warung lesehan di malam hari yang menjual kuliner Jogja seperti gudeg. Jalan ini juga terkenal sebagai tempat berkumpulnya para seniman yang sering mengekspresikan kemampuan mereka seperti bermain musik, melukis, happening art, pantomim, dan lain-lain.",
            "https://asset.kompas.com/crops/MUE-eoOIx_KuiklsCUtdxaOyw5M=/0x0:749x500/750x500/data/photo/2022/05/16/62822daaa7f76.png",
            4.7,
            9323,
            -7.793033,
            110.365831
        ),
        ListTouristAttractionItem(
            "2",
            "Pantai Parangtritis",
            "Pantai Parangtritis adalah tempat wisata yang terletak di Kalurahan Parangtritis, Kapanéwon Kretek, Kabupaten Bantul, Daerah Istimewa Yogyakarta. Jaraknya kurang lebih 27 km dari pusat kota. Pantai ini menjadi salah satu destinasi wisata terkenal di Yogyakarta dan telah menjadi ikon pariwisata di Yogyakarta. Pantai Parangtritis juga memungkian wisatawan untuk menyaksikan matahari terbit dan matahari terbenam. Pantai ini menawarkan aktivitas lain bagi para wisatawan seperti mengelilingi pantai dengan menggunakan motor ATV, kuda maupun delman, melakukan olahraga seperti berlari, sepak bola dan voli di pasir pantai, bermain di pinggir ombak, bermain layangan, atau sekadar duduk-duduk sambil menikmati suasana Pantai Parangtritis.",
            "https://bob.kemenparekraf.go.id/wp-content/uploads/2019/10/parangtritis.jpg",
            4.8,
            1923,
            -8.024535,
            110.330018
        ),
        ListTouristAttractionItem(
            "3",
            "Puncak Sosok",
            "Puncak Sosok adalah destinasi wisata pegunungan yang berada di Dukuh Jambon, Desa Bawuran, Kalurahan Pleret, Kapanéwon Pleret, Kabupaten Bantul, Provinsi Daerah Istimewa Yogyakarta. Destinasi wisata tersebut menjadi populer di Kabupaten Bantul karena sering dikunjungi oleh para pencinta fotografi dan pesepeda untuk berfoto. Fasilitas penunjang wisata di tempat ini adalah warung makan. Aneka makanan dan minuman bisa dipesan di sini untuk teman menikmati indahnya panorama sore. Makanan yang dijual di sini antara lain seperti tahu walik, bakso tusuk, sempol ayam, siomay, sate gajih, cilok krispy, dan jagung bakar. Untuk minuman, tersedia teh, jeruk, dan kopi.",
            "https://www.goodnewsfromindonesia.id/uploads/post/large-puncak-sosok-wisataoke-dot-com-a87fe329452d623d3aedd439ddf002fa.jpg",
            4.7,
            2839,
            -7.871701,
            110.426220
        ),
        ListTouristAttractionItem(
            "4",
            "HeHa Sky View",
            "Heha Skyview merupakan salah satu wahana wisata buatan yang menawarkan pemandangan terbaik kota Yogyakarta dan sekitarnya dari Jogja lantai 2. Terletak di kawasan perbukitan Gunungkidul, HeHa Sky View hanya berjarak 40 menit dari pusat Kota Yogyakarta. Salah satu wahana ikonik dari Heha Skyview adalah Sky Ballon yang dibuat colorful dan masih banyak wahana lainnya.",
            "https://ds393qgzrxwzn.cloudfront.net/cat1/img/images/0/6TYQBHAWMX.jpg",
            4.7,
            1029,
            -7.849588,
            110.478452
        ),
    )
}
