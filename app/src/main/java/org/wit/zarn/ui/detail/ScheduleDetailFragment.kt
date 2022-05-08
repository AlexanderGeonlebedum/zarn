package org.wit.zarn.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wit.zarn.databinding.FragmentScheduleDetailBinding
import org.wit.zarn.ui.auth.LoggedInViewModel
import org.wit.zarn.ui.appointment.AppointmentViewModel
import timber.log.Timber

class ScheduleDetailFragment : Fragment() {

    private lateinit var detailViewModel: ScheduleDetailViewModel
    private val args by navArgs<ScheduleDetailFragmentArgs>()
    private var _binding: FragmentScheduleDetailBinding? = null
    private val binding get() = _binding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val appointmentViewModel : AppointmentViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)
        val root = binding.root

        detailViewModel = ViewModelProvider(this).get(ScheduleDetailViewModel::class.java)
        detailViewModel.observableZarn.observe(viewLifecycleOwner, Observer { render() })

        binding.editZarnButton.setOnClickListener {
            detailViewModel.updateZarn(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.zarnid, binding.zarnvm?.observableZarn!!.value!!)
            //Force Reload of list to guarantee refresh
            appointmentViewModel.load()
            findNavController().navigateUp()
            //findNavController().popBackStack()

                }

        binding.deleteZarnButton.setOnClickListener {
            appointmentViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                            detailViewModel.observableZarn.value?.uid!!)
            findNavController().navigateUp()
        }
        return root
    }

    private fun render() {
        binding.zarnvm = detailViewModel
        Timber.i("Retrofit binding.zarnvm == $binding.zarnvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getZarn(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                        args.zarnid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}