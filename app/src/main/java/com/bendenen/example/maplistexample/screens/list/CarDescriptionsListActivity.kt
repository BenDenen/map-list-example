package com.bendenen.example.maplistexample.screens.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bendenen.example.maplistexample.R
import com.bendenen.example.maplistexample.screens.list.viewmodel.CarDescriptionsListViewModel
import com.bendenen.example.maplistexample.utils.ResourceObserver
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_list.car_description_list
import kotlinx.android.synthetic.main.activity_map.progressbar
import javax.inject.Inject

class CarDescriptionsListActivity : AppCompatActivity() {

    private lateinit var carDescriptionsViewModel: CarDescriptionsListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        car_description_list.layoutManager = LinearLayoutManager(this)
        val adapter = CarDescriptionsAdapter(this)
        car_description_list.adapter = adapter

        carDescriptionsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CarDescriptionsListViewModel::class.java).also { it ->
                    it.carDescriptions.observe(this, ResourceObserver("CarDescriptionsListActivity",
                            hideLoading = ::hideLoading,
                            showLoading = ::showLoading,
                            onSuccess = {
                                adapter.setData(it)
                            },
                            onError = ::showErrorMessage))

                }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    companion object {

        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, CarDescriptionsListActivity::class.java)
        }
    }


}