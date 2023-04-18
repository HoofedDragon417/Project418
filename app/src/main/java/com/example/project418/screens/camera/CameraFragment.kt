package com.example.project418.screens.camera

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.common.BaseFragment
import com.example.project418.common.Screens
import com.example.project418.databinding.FragmentCameraBinding
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.github.terrakok.cicerone.Router
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CameraFragment : BaseFragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CameraVMImpl by activityViewModel()

    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionsBuilder(Manifest.permission.CAMERA).build().send { result ->
            if (result.allGranted()) {
                startCamera()
            } else router.backTo(Screens.Main())
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val previewMain = Preview.Builder().build()
                .also { it.setSurfaceProvider(binding.cameraPreview.surfaceProvider) }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val options =
                GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()

            val scanner = GmsBarcodeScanning.getClient(AppGlobal.Instance, options)

            scanner.startScan().addOnSuccessListener { barcode ->
                if (viewModel.checkContent(requireNotNull(barcode.rawValue))) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(AppGlobal.Instance.getString(R.string.qr_error_title))
                        .setMessage(AppGlobal.Instance.getString(R.string.qr_error_message))
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
                }
            }

            try {
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector,
                    previewMain
                )
            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    companion object {
        private const val TAG = "CameraXApp"
    }
}