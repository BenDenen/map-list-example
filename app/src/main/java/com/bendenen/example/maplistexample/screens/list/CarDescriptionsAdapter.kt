package com.bendenen.example.maplistexample.screens.list

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bendenen.example.maplistexample.R
import com.bendenen.example.maplistexample.models.CarDescription
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class CarDescriptionsAdapter(context: Context) : RecyclerView.Adapter<CarDescriptionsAdapter.CarDescriptionViewHolder>() {

    private val dataList = ArrayList<CarDescription>()

    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarDescriptionViewHolder =
            CarDescriptionViewHolder(layoutInflater.inflate(R.layout.car_description_item, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: CarDescriptionViewHolder, position: Int) {
        holder.carDescription = dataList[position]
    }

    fun setData(newData: List<CarDescription>) {
        val postDiffCallback = PostDiffCallback(dataList, newData)
        val diffResult = DiffUtil.calculateDiff(postDiffCallback)

        dataList.clear()
        dataList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    internal inner class PostDiffCallback(private val oldCarDescriptionsList: List<CarDescription>, private val newCarDescriptionsList: List<CarDescription>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldCarDescriptionsList.size
        }

        override fun getNewListSize(): Int {
            return newCarDescriptionsList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCarDescriptionsList[oldItemPosition].id === newCarDescriptionsList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCarDescriptionsList[oldItemPosition] == newCarDescriptionsList[newItemPosition]
        }
    }


    class CarDescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val carImage: ImageView = itemView.findViewById(R.id.car_image)
        private val carModel: TextView = itemView.findViewById(R.id.car_model)
        private val carTransmissionType: TextView = itemView.findViewById(R.id.car_transmission_type)
        private val picasso = Picasso.with(itemView.context)

        private val imageSize = itemView.context.resources.getDimensionPixelSize(R.dimen.car_description_image_size)

        var carDescription: CarDescription? = null
            set(value) {
                field = value
                picasso
                        .load("https://content.drive-now.com/sites/default/files/cars/3x/${carDescription?.modelIdentifier}/${carDescription?.color}.png")
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .error(R.drawable.ic_small_car_svgrepo_com)
                        .resize(imageSize, imageSize)
                        .centerInside()
                        .into(carImage, object : Callback {
                            override fun onSuccess() {
                            }

                            override fun onError() {
                                picasso
                                        .load(carDescription?.carImageUrl)
                                        .resize(imageSize, imageSize)
                                        .error(R.drawable.ic_small_car_svgrepo_com)
                                        .centerInside()
                                        .into(carImage)
                            }
                        })

                carModel.text = carDescription?.modelName ?: "null"
                carTransmissionType.text = carDescription?.transmission?.serverKey ?: "undefined"
            }


    }
}