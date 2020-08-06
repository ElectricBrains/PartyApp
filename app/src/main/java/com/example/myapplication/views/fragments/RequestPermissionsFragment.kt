package com.example.myapplication.views.fragments

import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_request_permissions.*


class RequestPermissionsFragment: Fragment() {
    private companion object {
        private const val REQUEST_PERMISSION_CODE = 1111
    }

    private fun isPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireActivity(), READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request_permissions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request_permissions_submit_button?.setOnClickListener {
            requestPermissions(arrayOf(READ_CONTACTS),
                REQUEST_PERMISSION_CODE
            )
        }

        open_system_settings_button?.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + requireContext().packageName)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPermissionsGranted()) {
            onPermissionGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        }
        else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(requireContext(), R.string.request_error, Toast.LENGTH_LONG).show()
            open_system_settings_button?.visibility = View.VISIBLE
        }
    }

    private fun onPermissionGranted() {
        val activity = requireActivity()
        activity.finish()
        activity.startActivity(activity.intent)
    }
}