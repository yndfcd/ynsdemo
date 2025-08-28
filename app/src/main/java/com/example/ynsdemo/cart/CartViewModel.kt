import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import kotlinx.coroutines.flow.update
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class CartItem(val id: String, val name: String, val price: Double)
class CartViewModel {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    
    init {
        // Mock 20 items
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
        }
        try {
            val response: List<Product> = client.get("https://fakestoreapi.com/products").body()
            val cartItems = response.map { product -> CartItem(id = product.id.toString(), name = product.title, price = product.price) }
            _cartItems.value = cartItems
        } catch (e: Exception) {
            // Handle exceptions, maybe log or show an error message
            e.printStackTrace()
        }
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
data class Product(
    val id: Int,
    val title: String,
    val price: Double
)