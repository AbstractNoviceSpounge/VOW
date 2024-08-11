package com.app.veggiefood.network

import com.app.veggiefood.model.request.AddEditCartRequest
import com.app.veggiefood.model.request.CalculatePriceRequest
import com.app.veggiefood.model.request.CartRequest
import com.app.veggiefood.model.request.ChangePasswordRequest
import com.app.veggiefood.model.request.DeleteCartRequest
import com.app.veggiefood.model.request.DeleteUserRequest
import com.app.veggiefood.model.request.LoginRequest
import com.app.veggiefood.model.request.PlaceOrderRequest
import com.app.veggiefood.model.request.ProfileRequest
import com.app.veggiefood.model.request.SignupRequest
import com.app.veggiefood.model.response.BannerModel
import com.app.veggiefood.model.response.BannerResponse
import com.app.veggiefood.model.response.BaseResponse
import com.app.veggiefood.model.response.CMSPageResponse
import com.app.veggiefood.model.response.CalculatePriceResponse
import com.app.veggiefood.model.response.CartResponse
import com.app.veggiefood.model.response.CategoryResponse
import com.app.veggiefood.model.response.CountCartResponse
import com.app.veggiefood.model.response.DeleteCartResponse
import com.app.veggiefood.model.response.LoginResponse
import com.app.veggiefood.model.response.OrderResponse
import com.app.veggiefood.model.response.PlaceOrderResponse
import com.app.veggiefood.model.response.ProductDetailResponse
import com.app.veggiefood.model.response.ProductResponse
import com.app.veggiefood.model.response.ProfileResponse
import com.app.veggiefood.model.response.SearchResponse
import com.app.veggiefood.model.response.VariationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("my_api/login")
    suspend fun doLogin(@Body request: LoginRequest): Response<LoginResponse>

    @POST("my_api/register")
    suspend fun doRegister(@Body request: SignupRequest): Response<BaseResponse>

    @GET("my_api/profile/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<ProfileResponse>

    @PUT("my_api/profile/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body request: ProfileRequest
    ): Response<BaseResponse>

    @GET("my_api/categoriesandproducts")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("my_api/products/category/{id}")
    suspend fun getProducts(
        @Path("id") id: String
    ): Response<ProductResponse>

    @GET("my_api/product/{id}")
    suspend fun getProductDetail(
        @Path("id") id: String
    ): Response<ProductDetailResponse>

    @POST("my_api/change-password")
    suspend fun doChangePassword(
        @Body request: ChangePasswordRequest
    ): Response<BaseResponse>

    @GET("my_api/get-page-content")
    suspend fun getCMSPage(
        @Query("page_name") page_name: String
    ): Response<CMSPageResponse>

    @POST("my_api/delete-user")
    suspend fun deleteUser(@Body request: DeleteUserRequest): Response<BaseResponse>

    @GET("Api/my_api/banners")
    suspend fun myBanner(): Response<BannerResponse>

    @GET("my_api/orders/{id}")
    suspend fun getOrders(@Path("id") id: String): Response<OrderResponse>

    @GET("my_api/variation")
    suspend fun getVariation(): Response<VariationResponse>

    @POST("my_api/calculatePrice")
    suspend fun getCalculatePrice(
        @Body request: CalculatePriceRequest
    ): Response<CalculatePriceResponse>

    @POST("my_api/addEditCart")
    suspend fun addEditCartRequest(
        @Body request: AddEditCartRequest
    ): Response<DeleteCartResponse>

    @POST("my_api/cartList")
    suspend fun getMyCart(@Body request: CartRequest): Response<CartResponse>

    @POST("my_api/removeCart")
    suspend fun deleteCart(@Body request: DeleteCartRequest): Response<DeleteCartResponse>

    @POST("my_api/checkout")
    suspend fun placeOrder(
        @Body request: PlaceOrderRequest
    ): Response<PlaceOrderResponse>

    @POST("my_api/countCart")
    suspend fun getTotalCount(
        @Body request: CartRequest
    ): Response<CountCartResponse>

    @GET("my_api/products/search")
    suspend fun getSearchProduct(
        @Query("query")query:String
    ):Response<SearchResponse>
}