package com.example.tara.ui.main

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tara.R
import com.example.tara.data.response.ListTouristAttractionItem
import com.example.tara.databinding.ItemLayoutBinding
import com.example.tara.ui.detail.DetailActivity
import java.util.Locale

class TouristAttractionAdapter (val userLocation: Location) :
    RecyclerView.Adapter<TouristAttractionAdapter.TouristAttractionViewHolder>() {

    private var listOfTouristAttraction = ArrayList<ListTouristAttractionItem>()

    fun addTouristAttraction(list: List<ListTouristAttractionItem>) {
        this.listOfTouristAttraction.clear()
        this.listOfTouristAttraction.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristAttractionViewHolder {
        return TouristAttractionViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfTouristAttraction.size
    }

    override fun onBindViewHolder(holder: TouristAttractionViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TouristAttractionViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val itemNow = listOfTouristAttraction[position]

            val geoCoder = Geocoder(itemView.context, Locale.getDefault())
            val address = geoCoder.getFromLocation(itemNow.lat,itemNow.lon,3)
            val location = address?.get(0)?.subAdminArea ?: "Kota Tidak Terdeteksi"
            val subLocation = address?.get(0)?.locality ?: "Kota Tidak Terdeteksi"

            val results = FloatArray(1)

            if (userLocation.latitude == 0.0 && userLocation.longitude == 0.0) {
                binding.tvLocation.text = "$subLocation, $location (-- KM)"
            } else {
                Location.distanceBetween(userLocation.latitude, userLocation.longitude , itemNow.lat, itemNow.lon, results)

                val distance = results[0]/1000
                val formattedDistance = String.format("%.2f", distance)

                binding.tvLocation.text = "$subLocation, $location ($formattedDistance KM)"
            }

            Glide
                .with(itemView.context)
                .load(itemNow.photoUrl)
                .centerCrop()
                .into(binding.ivLocationImage)

            binding.tvLocationName.text = itemNow.locationName
            binding.tvRating.text = itemView.context.getString(R.string.rating, itemNow.rating, itemNow.userRatingsTotal)

            binding.itemLayout.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_TOURIST_ATTRACTION_DATA, itemNow)
                intent.putExtra(DetailActivity.EXTRA_USER_LOCATION, userLocation)
                itemView.context.startActivity(intent)
            }
        }

    }
}