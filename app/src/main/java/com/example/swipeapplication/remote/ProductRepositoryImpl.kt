package com.example.swipeapplication.remote

import com.example.swipeapplication.utility.createPartFromString
import com.example.swipeapplication.data.AddProductResponse
import com.example.swipeapplication.data.ProductData
import com.example.swipeapplication.data.ProductDetailsDto
import com.example.swipeapplication.service.MyApplicationService
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ProductRepositoryImpl(private val service: MyApplicationService) : ProductRepository {
    override suspend fun getAllProducts(): List<ProductDetailsDto> {
        //Code for api calling and returning the same
        return service.getProducts()
    }

    override suspend fun addProduct(product: ProductData): AddProductResponse {
        delay(1000)
        val requestMap: MutableMap<String, RequestBody> = mutableMapOf()
        var multiPartImage: MultipartBody.Part? = null
        requestMap["product_name"] = createPartFromString(product.productName)
        requestMap["product_type"] = createPartFromString(product.productType)
        requestMap["price"] = createPartFromString(product.price)
        requestMap["tax"] = createPartFromString(product.tax)
        product.image?.let { image ->

//            val request = RequestBody.create("image/*".toMediaTypeOrNull(), image.readBytes()) // read all bytes using kotlin extension
//            multiPartImage = MultipartBody.Part.createFormData(
//                "file",
//                "TestFile.jpg",
//                request
//            )

//            val fileUri = Uri.parse(image)
//            val file = File(fileUri.path)
//            val requestFile: RequestBody = RequestBody.create(
//                "image/jpg".toMediaType(),
//                file = file
//            )
//            multiPartImage =
//                MultipartBody.Part.createFormData("files", file.name, requestFile)


//            multiPartImage = MultipartBody.Part.createFormData(
//                "files[]", "filename", RequestBody.create(
//                    "image/*".toMediaTypeOrNull(),
//                    image.rea
//                )
//            )

//            val baos = ByteArrayOutputStream()
//            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val stream = baos.toByteArray()
//            val requestBody = RequestBody.create(MediaType.parse("image/*"), stream)
//            multiPartImage =
//                MultipartBody.Part.createFormData("files[]", "files[]", requestBody)

        }

        //return service.addProduct(requestMap, multiPartImage)
        return service.addAProduct(
            prodName = createPartFromString(product.productName),
            prodType = createPartFromString(product.productType),
            prodPrice = createPartFromString(product.price),
            prodTax = createPartFromString(product.tax),
            file = product.image
        )
    }

}