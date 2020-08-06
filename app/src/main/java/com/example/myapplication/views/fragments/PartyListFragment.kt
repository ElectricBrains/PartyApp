package com.example.myapplication.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.PartyItemLayoutBinding
import com.example.myapplication.tools.adapters.*
import com.example.myapplication.viewmodels.PartyListViewModel
import com.example.myapplication.viewmodels.PartyListViewModelFactory
import com.example.myapplication.viewmodels.PartyViewModel
import kotlinx.android.synthetic.main.fragment_party_list.*
import javax.inject.Inject

class PartyListFragment: Fragment() {
    @Inject
    lateinit var mFactory: PartyListViewModelFactory

    private lateinit var mViewModel: PartyListViewModel

    private val mAdapter = SimpleListAdapter(
        HolderCreator(this::createHolder),
        HolderBinder(this::bindHolder),
        PartyDiffCallback()
    )

    private fun bindHolder(viewModel: PartyViewModel, holder: Holder<PartyItemLayoutBinding>): Int {
        holder.binding.viewModel = viewModel

        if (viewModel.additionalVisitors.isNotEmpty() && !holder.binding.joinedFriendsStub.isInflated) {
            holder.binding.joinedFriendsStub.viewStub?.inflate()
        }
        else if (viewModel.additionalVisitors.isNotEmpty() && holder.binding.joinedFriendsStub.isInflated) {
            holder.binding.joinedFriendsStub.root?.visibility = View.VISIBLE
        }
        else if (viewModel.additionalVisitors.isEmpty() && holder.binding.joinedFriendsStub.isInflated) {
            holder.binding.joinedFriendsStub.root?.visibility = View.GONE
        }

        holder.binding.showMapButton.setOnClickListener {
            findNavController().navigate(
                PartyListFragmentDirections.actionPartyListFragmentToPartyDescriptionFragment(
                    viewModel.id
                )
            )
        }

        return 0
    }

    private fun createHolder(parent: ViewGroup?): Holder<PartyItemLayoutBinding> {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = PartyItemLayoutBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        mViewModel = ViewModelProvider(this, mFactory).get(PartyListViewModel::class.java)
        mViewModel.list.observe(this, Observer {
            mAdapter.submitList(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_party_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity, findNavController());
        party_list?.adapter = mAdapter
    }
}