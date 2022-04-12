package org.wit.zarn.ui.appointment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.zarn.R
import org.wit.zarn.adapters.ZarnAdapter
import org.wit.zarn.adapters.ZarnClickListener
import org.wit.zarn.databinding.FragmentAppointmentBinding
import org.wit.zarn.main.ZarnApp
import org.wit.zarn.models.ZarnModel
import org.wit.zarn.utils.SwipeToDeleteCallback
import org.wit.zarn.utils.createLoader
import org.wit.zarn.utils.hideLoader
import org.wit.zarn.utils.showLoader

class AppointmentFragment : Fragment(), ZarnClickListener {

    lateinit var app: ZarnApp
    private var _binding : FragmentAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var loader : AlertDialog

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
        loader = createLoader(requireActivity())

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        appointmentViewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        showLoader(loader,"Downloading Bokings")
        appointmentViewModel.observableZarnsList.observe(viewLifecycleOwner, Observer {
            zarns ->
            zarns?.let {
                render(zarns as ArrayList<ZarnModel>)
            hideLoader(loader)
            checkSwipeRefresh()
            }
        })
//        binding.fab.setOnClickListener{
//            val action = AppointmentFragmentDirections.actionAppointmentFragmentToScheduleFragment()
//            findNavController().navigate(action)
//        }

        setSwipeRefresh()
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Bookings")
                val adapter = binding.recyclerView.adapter as ZarnAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                appointmentViewModel.delete(viewHolder.itemView.tag as String)
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(binding.recyclerView)

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

    fun setSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Bookings")
            appointmentViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing)
            binding.swiperefresh.isRefreshing = false
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