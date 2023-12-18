package com.example.nomad.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.nomad.R
import com.example.nomad.additional.TopScrollView
import com.example.nomad.databinding.FragmentMainBinding
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.use_case.LanguageController
import com.example.nomad.domain.use_case.ProductListManager
import com.example.nomad.presentation.adapters.PagerItemAdapter
import com.example.nomad.presentation.adapters.ProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), PagerItemAdapter.Listener, ProductAdapter.Listener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    private var currentTab = TopScrollView.MAIN
    private var pagerItemAdapter: PagerItemAdapter? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerItemAdapter = PagerItemAdapter(requireContext(), this)
        productAdapter = ProductAdapter(this)
        viewModel.fetchMainMenu(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        navigationToBucket()
        navigationToSearch()
        showOrHideBill()
        languageController()
        observers()



        with(binding) {
            mainMenu.setOnClickListener { scrollBarListener(mainMenu, TopScrollView.MAIN) }
            barMenu.setOnClickListener { scrollBarListener(barMenu, TopScrollView.BAR) }
            dessertMenu.setOnClickListener { scrollBarListener(dessertMenu, TopScrollView.DESSERT) }

            ProgressBar.visibility = View.VISIBLE
            RecView2.visibility = View.GONE
        }

        return binding.root
    }

    private fun navigationToBucket() {
        binding.Bill.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.main_to_bucket)
        }
    }

    private fun navigationToSearch() {
        binding.searchView.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.main_to_search)
        }
    }

    private fun showOrHideBill() {
        with(binding) {
            if (ProductListManager.getBill() > 0) {
                Bill.visibility = View.VISIBLE
                Bill.text = ProductListManager.getBill().toString()
            } else
                Bill.visibility = View.GONE
        }
    }

    private fun languageController() {
        with(binding) {
            language.text = getString(LanguageController.getAbbreviation())
            searchView.text = getString(LanguageController.getSearch())

            language.setOnClickListener {
                LanguageController.setLanguage()
                language.text = getString(LanguageController.getAbbreviation())
                searchView.text = getString(LanguageController.getSearch())

                viewModel.mainMenuList.value?.let { list ->
                    textView1.text = LanguageController.getMainMenuLanguage(list[0])
                    textView2.text = LanguageController.getMainMenuLanguage(list[1])
                    textView3.text = LanguageController.getMainMenuLanguage(list[2])
                }

                pagerItemAdapter!!.setData(viewModel.foodTypeList.value!!, true)
                productAdapter!!.setData(viewModel.productList.value!!, true)

            }
        }
    }

    private fun observers() {

        viewModel.mainMenuList.observe(viewLifecycleOwner) { list ->
            with(binding) {
                viewModel.fetchFoodTypeByID(list[0].documentId)
                imgView1.load(list[0].image)
                textView1.text = LanguageController.getMainMenuLanguage(list[0])
                imgView2.load(list[1].image)
                textView2.text = LanguageController.getMainMenuLanguage(list[1])
                imgView3.load(list[2].image)
                textView3.text = LanguageController.getMainMenuLanguage(list[2])
            }
        }

        viewModel.foodTypeList.observe(viewLifecycleOwner) { foodTypeList ->
            viewModel.fetchProductByID(foodTypeList[0].documentId, requireContext())
            pagerItemAdapter!!.selectItem(0)
            pagerItemAdapter!!.setData(foodTypeList)
            binding.RecView1.adapter = pagerItemAdapter
            binding.RecView1.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.productList.observe(viewLifecycleOwner) { list ->
            productAdapter!!.setData(list)
            binding.RecView2.adapter = productAdapter
            binding.RecView2.layoutManager =
                LinearLayoutManager(requireContext())

            with(binding) {
                ProgressBar.visibility = View.GONE
                RecView2.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun scrollBarListener(view: View, tab: TopScrollView) {
        if (currentTab != tab) {
            when (currentTab) {
                TopScrollView.MAIN -> {
                    binding.mainMenu.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.unselected_item_bg, null)
                }

                TopScrollView.BAR -> {
                    binding.barMenu.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.unselected_item_bg, null)
                }

                TopScrollView.DESSERT -> {
                    binding.dessertMenu.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.unselected_item_bg, null)
                }
            }

            when (tab) {
                TopScrollView.MAIN -> {
                    view.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.selected_item_bg, null)
                    viewModel.mainMenuList.value?.get(0)
                        ?.let { it1 -> viewModel.fetchFoodTypeByID(it1.documentId) }
                }

                TopScrollView.BAR -> {
                    view.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.selected_item_bg, null)
                    viewModel.mainMenuList.value?.get(1)
                        ?.let { it1 -> viewModel.fetchFoodTypeByID(it1.documentId) }
                }

                TopScrollView.DESSERT -> {
                    view.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.selected_item_bg, null)
                    viewModel.mainMenuList.value?.get(2)
                        ?.let { it1 -> viewModel.fetchFoodTypeByID(it1.documentId) }
                }
            }
            currentTab = tab
        }
    }

    override fun onClick(foodType: FoodTypeModel) {
        viewModel.fetchProductByID(foodType.documentId, requireContext())
        with(binding) {
            ProgressBar.visibility = View.VISIBLE
            RecView2.visibility = View.GONE
        }
    }

    override fun onItemPosition(position: Int) {
        pagerItemAdapter!!.selectItem(position)
    }

    override fun onClick() {
        with(binding) {
            if (ProductListManager.getBill() > 0) {
                Bill.visibility = View.VISIBLE
                Bill.text = ProductListManager.getBill().toString()
            } else {
                Bill.visibility = View.GONE
            }
        }
    }
}