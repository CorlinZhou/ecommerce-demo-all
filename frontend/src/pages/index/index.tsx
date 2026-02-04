import { useState } from 'react'
import { View } from '@tarojs/components'
import { OrderConfirmation } from '@/types/api'
import './index.scss'

// Import components directly for better WeChat Mini Program compatibility
import ProductList from './components/product/ProductList'
import BottomBar from './components/layout/BottomBar'
import CartDrawer from './components/cart/CartDrawer'
import OrderModal from './components/order/OrderModal'

import { useProducts, useCart, useOrder } from './hooks'

export default function Index() {
  const { products, refresh: refreshProducts } = useProducts()

  const {
    orderDraft,
    addProduct,
    removeProduct,
    stockErrorIds,
    markStockErrors,
    totalQty,
    totalPrice,
    clear
  } = useCart()

  const [cartVisible, setCartVisible] = useState(false)
  const [orderConfirmation, setOrderConfirmation] =
    useState<OrderConfirmation | null>(null)

  const { submitting, submitOrder } = useOrder({
    orderDraft,
    totalQty,
    refreshProducts,
    markStockErrors,
    clearCart: clear,
    setCartVisible,
    setOrderConfirmation
  })

  return (
    <View className="app-container">
      <View className="page">
        <ProductList
          products={products}
          orderDraft={orderDraft}
          stockErrorIds={stockErrorIds}
          onAdd={addProduct}
          onRemove={removeProduct}
        />

        <BottomBar
          totalQty={totalQty}
          totalPrice={totalPrice}
          onOpenCart={() => totalQty && setCartVisible(true)}
        />
      </View>

      {/* Move modals outside the page container for proper positioning */}
      <CartDrawer
        visible={cartVisible}
        products={products}
        orderDraft={orderDraft}
        stockErrorIds={stockErrorIds}
        totalQty={totalQty}
        totalPrice={totalPrice}
        submitting={submitting}
        onClose={() => setCartVisible(false)}
        onAdd={addProduct}
        onRemove={removeProduct}
        onSubmit={submitOrder}
      />

      <OrderModal
        data={orderConfirmation}
        onClose={() => setOrderConfirmation(null)}
      />
    </View>
  )
}
