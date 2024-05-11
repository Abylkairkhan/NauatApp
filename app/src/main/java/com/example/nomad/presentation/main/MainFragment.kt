package com.example.nomad.presentation.main

import android.os.Bundle
import android.util.Log
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
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.LanguageController
import com.example.nomad.domain.usecase.ProductListManager
import com.example.nomad.presentation.adapters.PagerItemAdapter
import com.example.nomad.presentation.adapters.ProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), PagerItemAdapter.Listener, ProductAdapter.Listener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    private var currentTab = TopScrollView.MAIN
    private var currentFoodTypePosition = 0
    private var lastClickedProduct = 0

    private var pagerItemAdapter: PagerItemAdapter? = null
    private var productAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pagerItemAdapter = PagerItemAdapter(requireContext(), this)
        productAdapter = ProductAdapter(this)

        viewModel.fetchServePercentage()
        viewModel.fetchMainMenu(requireContext())
        viewModel.fetchFoodTypeByID("main_menu")
        viewModel.fetchProductByID("xNHfOIs4zqbi0SeED9Qv", requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        with(binding) {
            mainMenu.setOnClickListener { scrollBarListener(TopScrollView.MAIN) }
            barMenu.setOnClickListener { scrollBarListener(TopScrollView.BAR) }
            dessertMenu.setOnClickListener { scrollBarListener(TopScrollView.DESSERT) }

            RecView1.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            RecView2.layoutManager =
                LinearLayoutManager(requireContext())

            ProgressBar.visibility = View.VISIBLE
            RecView2.visibility = View.GONE
        }

        productAdapter?.synchronizeCartWithProductList()
        navigationToBucket()
        navigationToSearch()
        showOrHideBill()
        languageController()

        changeSelectedTabColor()
        observers()

        return binding.root
    }

    private fun observers() {

        viewModel.mainMenuList.observe(viewLifecycleOwner) { list ->
            with(binding) {
                imgView1.load(list[0].image)
                textView1.text = LanguageController.getMainMenuLanguage(list[0])
                imgView2.load(list[1].image)
                textView2.text = LanguageController.getMainMenuLanguage(list[1])
                imgView3.load(list[2].image)
                textView3.text = LanguageController.getMainMenuLanguage(list[2])
                servePercent.text =
                    getString(LanguageController.getServePercentage()) + " " + ProductListManager.getServePercentage()
                        .toString() + "%"
            }
        }

        viewModel.foodTypeList.observe(viewLifecycleOwner) { foodTypeList ->
            pagerItemAdapter!!.setData(foodTypeList)
            pagerItemAdapter!!.selectItem(currentFoodTypePosition)

            fetchSelectedProducts()

            binding.RecView1.scrollToPosition(currentFoodTypePosition)
            binding.RecView1.adapter = pagerItemAdapter
        }

        viewModel.productList.observe(viewLifecycleOwner) { list ->
            productAdapter!!.setData(list)
            productAdapter?.synchronizeCartWithProductList()

            binding.RecView2.adapter = productAdapter
            binding.RecView2.scrollToPosition(lastClickedProduct)
            with(binding) {
                ProgressBar.visibility = View.GONE
                RecView2.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.d("MyLog", it)
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchSelectedProducts() {
        val foodType = viewModel.foodTypeList.value?.get(currentFoodTypePosition)
        if (foodType != null)
            viewModel.fetchProductByID(foodType.documentId, requireContext())
    }

    private fun fetchSelectedTabFoodType() {
        when (currentTab) {
            TopScrollView.MAIN ->
                viewModel.mainMenuList.value?.get(0)
                    ?.let { it1 -> viewModel.fetchFoodTypeByID(it1.documentId) }

            TopScrollView.BAR -> viewModel.mainMenuList.value?.get(1)
                ?.let { it1 -> viewModel.fetchFoodTypeByID(it1.documentId) }

            TopScrollView.DESSERT -> viewModel.mainMenuList.value?.get(2)
                ?.let { it1 -> viewModel.fetchFoodTypeByID(it1.documentId) }
        }
    }

    private fun changeSelectedTabColor() {
        when (currentTab) {
            TopScrollView.MAIN -> {
                binding.mainMenu.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.selected_item_bg, null)
            }

            TopScrollView.BAR -> {
                binding.barMenu.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.selected_item_bg, null)
            }

            TopScrollView.DESSERT -> {
                binding.dessertMenu.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.selected_item_bg, null)
            }
        }

        fetchSelectedTabFoodType()
    }

    private fun scrollBarListener(tab: TopScrollView) {
        currentFoodTypePosition = 0
        lastClickedProduct = 0
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

            currentTab = tab
            changeSelectedTabColor()
        }
    }

    override fun onClick(foodType: FoodTypeModel, position: Int) {
        lastClickedProduct = 0
        viewModel.fetchProductByID(foodType.documentId, requireContext())
        pagerItemAdapter!!.selectItem(position)
        with(binding) {
            ProgressBar.visibility = View.VISIBLE
            RecView2.visibility = View.GONE
        }
        currentFoodTypePosition = position
    }

    override fun onButtonClick() {
        with(binding) {
            if (ProductListManager.getBill() > 0) {
                Bill.visibility = View.VISIBLE
                Bill.text = ProductListManager.getBill().toString()
            } else {
                Bill.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(product: ProductModel, position: Int) {
        lastClickedProduct = position
        val action = MainFragmentDirections.mainToProduct(product.id)
        Navigation.findNavController(requireView()).navigate(action)
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

                servePercent.text =
                    getString(LanguageController.getServePercentage()) + " " + ProductListManager.getServePercentage()
                        .toString() + "%"

                pagerItemAdapter!!.setData(viewModel.foodTypeList.value!!, true)
                productAdapter!!.setData(viewModel.productList.value!!, true)

            }
        }
    }
}