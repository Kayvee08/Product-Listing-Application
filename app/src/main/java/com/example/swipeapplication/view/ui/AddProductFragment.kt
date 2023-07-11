package com.example.swipeapplication.view.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.swipeapplication.view.viewmodel.AddProductViewModel
import com.example.swipeapplication.data.ProductData
import com.example.swipeapplication.data.Status
import com.example.swipeapplication.databinding.FragmentAddProductBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel: AddProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        if (savedInstanceState?.containsKey("image") == true) {
            viewModel.productImage = savedInstanceState.get("image") as Bitmap?
        }
        viewModel.productImage?.let { image ->
            binding.productIv.setImageBitmap(image)
        }

        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING ->
                    binding.loaderLayout.visibility = View.VISIBLE

                Status.SUCCESS, Status.ERROR -> {
                    binding.loaderLayout.visibility = View.GONE
                    findNavController().navigate(
                        AddProductFragmentDirections.actionAddProductFragmentToResultFragment(
                            response
                        )
                    )
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.productImage?.let { uri ->
            outState.putParcelable("image", uri)
        }
    }

    private fun setListeners() {
        binding.apply {
            radioGroup.setOnCheckedChangeListener { group, _ ->
                val selectedButtons =
                    binding.root.findViewById<RadioButton>(group.checkedRadioButtonId)
                productTypeTv.text = selectedButtons.text
                toggleProductTypeExpandedLayoutVisibility()
            }

            addProductIv.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )

                } else {
                    openGallery()
                }
            }

            productTypeLayout.setOnClickListener {
                toggleProductTypeExpandedLayoutVisibility()
            }

            submit.setOnClickListener {
                //var imageMultipart: MultipartBody.Part? = null
                if (productNameEdittext.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Product Name Cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (productTypeTv.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Product Type Cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (productPriceEdittext.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Product Price Cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (taxRateEdittext.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Tax Rate Cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

//                viewModel.file?.let { imageFile ->
//                    val requestImageFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
//                    imageMultipart = MultipartBody.Part.createFormData(
//                        "files[]",
//                        "file",
//                        requestImageFile
//                    )
//                }

                viewModel.submit(
                    ProductData(
                        productName = productNameEdittext.text.toString(),
                        productType = productTypeTv.text.toString(),
                        price = productPriceEdittext.text.toString(),
                        tax = taxRateEdittext.text.toString(),
                        image = viewModel.multiPart
                    )
                )
            }
        }
    }

    private fun toggleProductTypeExpandedLayoutVisibility() {
        binding.arrow.rotation += 180f
        binding.productTypeExpandedSection.visibility =
            if (binding.productTypeExpandedSection.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FROM_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {

                    data?.data?.let { uri ->
                        val imageUri: Uri = uri
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
                        viewModel.productImage = bitmap
                        binding.productIv.apply {
                            setImageBitmap(bitmap)
                        }
                        val stream =
                            context?.contentResolver?.openInputStream(data.data!!) ?: return
                        val request = RequestBody.create(
                            "image/*".toMediaTypeOrNull(),
                            stream.readBytes()
                        )
                        viewModel.multiPart = MultipartBody.Part.createFormData(
                            "files[]",
                            "test.jpg",
                            request
                        )
                    }

                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(
                    context,
                    "Need Storage Permission for selecting an Image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun openGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        intent.apply {
            type = "image/jpeg|image/png"
            //type = "image/*"
            //putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            putExtra("crop", "true")
            putExtra("aspectX", 1)
            putExtra("aspectY", 1)
            putExtra("outputX", 396)
            putExtra("outputY", 396)
            putExtra("scale", true)
            putExtra("return-data", true)
            putExtra(
                "outputFormat",
                Bitmap.CompressFormat.JPEG.toString()
            )
        }
        startActivityForResult(intent, PICK_FROM_GALLERY)
    }

    companion object {

        const val PICK_FROM_GALLERY = 100

    }
}