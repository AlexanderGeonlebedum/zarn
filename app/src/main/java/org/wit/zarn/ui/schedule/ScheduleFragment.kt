package org.wit.zarn.ui.schedule

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.zarn.R
import org.wit.zarn.databinding.FragmentScheduleBinding


class ScheduleFragment : Fragment() {

    private var _binding : FragmentScheduleBinding? =null
    private val binding get() = _binding!!
    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_schedule, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }


    fun setButtonListener(layout : FragmentScheduleBinding){
        layout.bookButton.setOnClickListener{

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}