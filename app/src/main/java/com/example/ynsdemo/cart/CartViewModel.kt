import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CartItem(val id: String, val name: String, val price: Double)

class CartViewModel {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    
    init {
        // Mock 20 items
        val mockItems = (1..20).map { i -> CartItem(id = i.toString(), name = "Item $i", price = 10.0 + i) }
        _cartItems.value = mockItems
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