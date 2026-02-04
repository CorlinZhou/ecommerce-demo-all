import { View, Text } from '@tarojs/components'
import { Product, OrderDraft, OrderItem } from '@/types/api'
import './CartDrawer.scss'
import CartItem from './CartItem'

interface Props {
  visible: boolean
  products: Product[]
  orderDraft: OrderDraft

  /** Product IDs with insufficient stock (for highlighting) */
  stockErrorIds: number[]

  totalQty: number
  totalPrice: number
  submitting: boolean

  onClose: () => void
  onAdd: (product: Product) => void
  onRemove: (product: Product) => void
  onSubmit: () => void
}

export default function CartDrawer(props: Props) {
  const {
    visible,
    products,
    orderDraft,
    stockErrorIds,
    totalQty,
    totalPrice,
    submitting,
    onClose,
    onAdd,
    onRemove,
    onSubmit
  } = props

  if (!visible) return null

  return (
    <View
      className="cart-mask"
      onClick={onClose}
      style={{ position: 'fixed', left: 0, right: 0, top: 0, bottom: 0, background: 'rgba(0,0,0,0.45)', zIndex: 9999 }}
    >
      <View
        className="cart-panel"
        onClick={e => e.stopPropagation()}
        style={{
          position: 'absolute',
          left: 0,
          right: 0,
          bottom: 0,
          zIndex: 10000,
          backgroundColor: '#ffffff',
          borderTopLeftRadius: 24,
          borderTopRightRadius: 24,
          overflow: 'hidden',
          // keep a gentle shadow where supported
          boxShadow: '0 -4px 20px rgba(0,0,0,0.15)'
        }}
      >
        {/* header */}
        <View className="cart-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', paddingTop: 24, paddingBottom: 24, paddingLeft: 24, paddingRight: 24, borderBottomWidth: 1, borderBottomColor: '#f0f0f0', borderBottomStyle: 'solid' }}>
          <Text className="title">🛒 Shopping Cart</Text>
          <View className="close-btn" onClick={onClose} role="button" style={{ padding: 12 }}>
            <Text className="close-icon">×</Text>
          </View>
        </View>

        {/* list */}
        <View className="cart-list" style={{ flex: 1, overflowY: 'auto', paddingTop: 16, paddingBottom: 16, paddingLeft: 24, paddingRight: 24 }}>
          {Object.values(orderDraft).map((item: OrderItem) => {
            const product = products.find(p => p.id === item.productId)
            if (!product) return null
            const hasStockError = stockErrorIds.includes(item.productId)

            return (
              <CartItem
                key={item.productId}
                item={item}
                product={product}
                hasStockError={hasStockError}
                onAdd={onAdd}
                onRemove={onRemove}
              />
            )
          })}
        </View>

        {/* footer */}
        <View className="cart-footer" style={{ paddingTop: 20, paddingBottom: 20, paddingLeft: 24, paddingRight: 24, borderTopWidth: 1, borderTopColor: '#f0f0f0', borderTopStyle: 'solid' }}>
          <View className="summary" style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 16 }}>
            <Text>Total {totalQty} items</Text>
            <Text className="price">${totalPrice.toFixed(2)}</Text>
          </View>

          <View
            className={`submit-btn ${submitting || totalQty === 0 ? 'disabled' : ''}`}
            onClick={() => {
              if (!submitting && totalQty > 0) {
                onSubmit()
              }
            }}
            style={{ width: '100%', height: '72rpx', background: (submitting || totalQty === 0) ? '#ccc' : '#1677ff' }}
          >
            {submitting ? 'Submitting...' : 'Submit Order'}
          </View>
        </View>
      </View>
    </View>
  )
}
