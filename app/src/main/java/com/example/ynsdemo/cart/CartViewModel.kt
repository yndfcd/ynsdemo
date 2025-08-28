import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.ResponseException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    val fontImg: String,
    val pid: String
)

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _shippingTotal = MutableStateFlow<String>("Calculatng...")
    val shippingTotal: StateFlow<String> = _shippingTotal

    init {
        viewModelScope.launch {
            fetchProducts()
        }
    }


    private suspend fun fetchProducts() {


        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
            expectSuccess = false // Allow handling non-2xx responses
        }
        try {
            val response: NewCheckoutResponse =
                client.get("https://checkout-api.iherbtest.biz/v2/ec/gasc?zipCode=92571&countryCode=US&keepSelectState=true") {
                    headers.append("AnonymousToken", "58e58f1a-7084-4db9-94f6-781bf443d9d7")
                }.body()


            _shippingTotal.value = response.cart.shippingCost
            val cartItems = response.cart.prodList.mapNotNull { product ->
                try {
                    CartItem(
                        id = product.productId.toString(),
                        name = product.displayName,
                        price = product.price,
                        fontImg = product.frontImg,
                        pid = product.pid
                    )
                } catch (e: SerializationException) {
                    println("Serialization Error for item: ${e.message}")
                    e.printStackTrace()
                    null // Skip this item if parsing fails
                }

            }
            _cartItems.value = cartItems
        } catch (e: ResponseException) {
            // Handle HTTP response errors
            println("HTTP Error: ${e.response.status.value}")
            e.printStackTrace()
            // You can access the error body here: e.response.body<String>()
        } catch (e: SerializationException) {
            // Handle serialization errors
            println("Serialization Error: ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
            e.printStackTrace()
        }
        client.close()
    }


    fun addItem(item: CartItem) {
        _cartItems.update { currentItems ->
            currentItems + item
        }
    }

    fun removeItem(item: CartItem) {
        _cartItems.update { currentItems ->
            currentItems.filterNot { it.id == item.id }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}


@Serializable
data class NewCheckoutResponse(
    val cart: Cart
)

@Serializable
data class Cart(
    val shippingCost: String,
    val prodList: List<NewProduct> = emptyList()
)

@Serializable
data class NewProduct(
    val productId: Int,
    val pid: String,
    val displayName: String,
    val frontImg: String = "",
    val prodQty: String,
    val price: Double,
    val lineItemErrors: List<String>? = emptyList()

)





