package com.example.project418.screens.camera

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.project418.R
import com.example.project418.common.AppGlobal
import com.example.project418.common.BaseFragment
import com.example.project418.common.Screens
import com.example.project418.databinding.FragmentCameraBinding
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CameraFragment : BaseFragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CameraVM by viewModels()

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
            } else AppGlobal.AppRouter.backTo(Screens.Main())
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val previewMain = Preview.Builder().build()
                .also { it.setSurfaceProvider(binding.cameraPreview.surfaceProvider) }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder().setTargetResolution(
                Size(
                    binding.cameraPreview.width / 6,
                    binding.cameraPreview.height / 6
                )
            ).setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST).build()

            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(requireContext()),
                QrCodeAnalyzer { result ->
                    cameraProvider.unbindAll()
                    if (!viewModel.checkContent(result)) {
                        val alertDialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle(AppGlobal.Instance.getString(R.string.qr_error_title))
                            .setMessage(AppGlobal.Instance.getString(R.string.qr_error_message))
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                                cameraProvider.bindToLifecycle(
                                    viewLifecycleOwner, cameraSelector,
                                    previewMain, imageAnalysis
                                )
                            }
                        alertDialog.create()
                        alertDialog.show()
                    }
                }
            )

            try {
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector,
                    previewMain, imageAnalysis
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