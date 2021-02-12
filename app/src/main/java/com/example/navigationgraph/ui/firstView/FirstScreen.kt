package com.example.navigationgraph.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.navigationgraph.R
import com.example.navigationgraph.databinding.FragmentFirstBinding
import com.example.navigationgraph.models.Country
import com.example.navigationgraph.remote.CountriesApi
import com.example.navigationgraph.remote.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class FirstScreen : Fragment() {

//    private val viewModel: FirstScreenViewModel by viewModels()
    private val viewModel:FirstScreenViewModel by inject()

    private val navController by lazy(::findNavController)  //Method referencing
    private lateinit var binding: FragmentFirstBinding

//    private val adapter = CountryAdapter()
    private val adapter: CountryAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCountry.apply {
            adapter = this@FirstScreen.adapter
        }
        bindToLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_first_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun bindToLiveData() {
        viewModel.getCountries().observe(viewLifecycleOwner, Observer { newCountryList ->
            adapter.submitList(newCountryList)
        })
    }
}

class FirstScreenViewModel(private val countriesApi: CountriesApi) : ViewModel() {

    private val countries = MutableLiveData<List<Country>>()
    fun getCountries(): LiveData<List<Country>> = countries

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val apiResponse = countriesApi.getAllCountries()
                updateCountries(apiResponse)
            } catch (e:Exception){
                Timber.e(e)
            }



        }
    }

    private fun updateCountries(newCountries: List<Country>) {
        viewModelScope.launch(Dispatchers.Main) {
            countries.value = newCountries
        }
    }

}