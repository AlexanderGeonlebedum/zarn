package org.wit.zarn.ui.appointment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.zarn.R
import org.wit.zarn.adapters.ZarnAdapter
import org.wit.zarn.adapters.ZarnClickListener
import org.wit.zarn.databinding.FragmentAppointmentBinding
import org.wit.zarn.main.ZarnApp
import org.wit.zarn.models.ZarnModel


class AppointmentFragment : Fragment(), ZarnClickListener {

    lateinit var app: ZarnApp
    private var _binding : FragmentAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var appointmentViewModel: AppointmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        appointmentViewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        appointmentViewModel.observableZarnsList.observe(viewLifecycleOwner, Observer {
            zarns ->
            zarns?.let {render(zarns)}
        })
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appointment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(zarnsList: List<ZarnModel>){
        binding.recyclerView.adapter = ZarnAdapter(zarnsList,this)
        if (zarnsList.isEmpty()){
            binding.recyclerView.visibility = View.GONE
        }else{
            binding.recyclerView.visibility = View.VISIBLE
        }
    }


    override fun onZarnClick(zarn: ZarnModel) {
//       val action = AppointmentFragmentDirections.actionAppointmentFragmentToZarnDetailFragment(zarn.id)
//        findNavController().navigate(action)
    }



    override fun onResume() {
        super.onResume()
        appointmentViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}