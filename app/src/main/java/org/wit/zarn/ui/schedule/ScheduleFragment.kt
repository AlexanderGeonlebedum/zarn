package org.wit.zarn.ui.schedule

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import org.wit.zarn.R
import org.wit.zarn.databinding.FragmentScheduleBinding
import org.wit.zarn.main.ZarnApp
import org.wit.zarn.models.ZarnModel
import org.wit.zarn.ui.appointment.AppointmentViewModel
import org.wit.zarn.ui.auth.LoggedInViewModel


class ScheduleFragment : Fragment() {

    private var _binding : FragmentScheduleBinding? =null
    private val binding get() = _binding!!
    private lateinit var scheduleViewModel: ScheduleViewModel
    private val appointmentViewModel: AppointmentViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root = binding.root
        activity?.title = getString(R.string.action_schedule)

        scheduleViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        scheduleViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        setButtonListener(binding)
        return root;
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_schedule, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {

                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.bookingError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout : FragmentScheduleBinding){
        layout.bookButton.setOnClickListener{

            val name = layout.clientAddName.text
            val contacts = layout.clientAddNumber.text.toString().toInt()
            if (name.isEmpty()){
                Toast.makeText(context,R.string.ClientNameValidation,Toast.LENGTH_LONG).show()
            }
            else if (layout.clientAddNumber.text.isEmpty()){
                Toast.makeText(context,R.string.ClientNumberValidation,Toast.LENGTH_LONG).show()
            }
            else{
                val chosenAppointments = if(layout.chosenAppointments.checkedRadioButtonId == R.id.ClassicFullSet)"Classic Full Set"
                else if(layout.chosenAppointments.checkedRadioButtonId == R.id.HybridFullSet)"Hybrid Full Set"
                else if (layout.chosenAppointments.checkedRadioButtonId == R.id.VolumeFullSet)"Volume Full Set"
                else if(layout.chosenAppointments.checkedRadioButtonId == R.id.MegaVolumeFullSet)"Mega Volume Full Set"
                else if(layout.chosenAppointments.checkedRadioButtonId == R.id.TouchUps)"Touch Ups"
                else "Removal"

                scheduleViewModel.addZarn(ZarnModel(chosenAppointments = chosenAppointments,clientAddName = name.toString(),clientAddNumber = contacts,
                    email = loggedInViewModel.liveFirebaseUser.value?.email!!))
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val appointmentViewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}