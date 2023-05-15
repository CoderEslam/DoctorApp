package com.doubleclick.doctorapp.android.Home

import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.doubleclick.doctorapp.android.Adapters.AdapterNavigation
import com.doubleclick.doctorapp.android.Home.fragment.BookFragment
import com.doubleclick.doctorapp.android.Home.fragment.FavoriteFragment
import com.doubleclick.doctorapp.android.Home.fragment.HomeFragment
import com.doubleclick.doctorapp.android.Home.fragment.ProfileFragment
import com.doubleclick.doctorapp.android.ItemNavigationListener
import com.doubleclick.doctorapp.android.Model.ItemNavigation
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentMainBinding
import com.doubleclick.doctorapp.android.views.bubblenavigation.listener.BubbleNavigationChangeListener


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainFragment = view.findViewById<View>(R.id.main_fragment)


/*        requireActivity().supportFragmentManager.beginTransaction()
            .replace(mainFragment.id, HomeFragment()).commit()*/

      /*  binding.rvNavigation.adapter =
            AdapterNavigation(
                this@MainFragment,
                listOf(
                    ItemNavigation(R.drawable.home, R.string.home, 0),
                    ItemNavigation(R.drawable.favorite, R.string.favorite, 1),
                    ItemNavigation(R.drawable.book, R.string.book, 2),
                    ItemNavigation(R.drawable.person, R.string.profile, 3)
                )
            )*/

/*        binding.floatingTopBarNavigation.setNavigationChangeListener(object :
            BubbleNavigationChangeListener {
            override fun onNavigationChanged(view: View?, position: Int) {
                when (position) {
                    0 -> {
                        replaceFragment(mainFragment.id, HomeFragment())
                    }
                    1 -> {
                        replaceFragment(mainFragment.id, FavoriteFragment())
                    }
                    2 -> {
                        replaceFragment(mainFragment.id, BookFragment())
                    }
                    3 -> {
                        replaceFragment(mainFragment.id, ProfileFragment())
                    }
                }
            }
        })*/

    }



    private fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
    ) {
        requireActivity().supportFragmentManager
            .beginTransaction().apply {
                replace(containerViewId, fragment)
                commit()
            }

    }



}