import { View, Text } from '@tarojs/components'
import { Product, OrderItem } from '@/types/api'
import './CartDrawer.scss'

interface Props {
  item: OrderItem
  product: Product
  hasStockError: boolean
  onAdd: (p: Product) => void
  onRemove: (p: Product) => void
}

export default function CartItem({ item, product, hasStockError, onAdd, onRemove }: Props) {
  return (
    <View
      key={item.productId}
      className={`cart-item ${hasStockError ? 'stock-error' : ''}`}
      style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', paddingTop: 20, paddingBottom: 20, borderBottomWidth: 1, borderBottomColor: '#f5f5f5', borderBottomStyle: 'solid' }}
    >
      <View className="info" style={{ display: 'flex', flexDirection: 'column' }}>
        <Text className="name">{item.name}</Text>

        <View className="meta">
          <Text className="price">${item.price.toFixed(2)}</Text>
          <Text className="stock">Stock: {product.stock}</Text>
        </View>

        {hasStockError && <Text className="error-text">Insufficient stock</Text>}
      </View>

      <View className="counter" style={{ display: 'flex', alignItems: 'center' }}>
          <View className="btn" onClick={() => onRemove(product)} style={{ width: '56rpx', height: '56rpx', borderRadius: '28rpx', background: '#f2f3f5', color: '#333', fontSize: '28rpx', textAlign: 'center', lineHeight: '56rpx', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Text className="btn-symbol">-</Text>
          </View>

          <Text className="qty" style={{ margin: '0 20rpx', fontSize: '28rpx', minWidth: '40rpx', textAlign: 'center', fontWeight: 'bold' }}>{item.quantity}</Text>

          <View
            className={`btn ${product.stock === 0 ? 'disabled' : ''}`}
            onClick={() => product.stock > 0 && onAdd(product)}
            style={{ width: '56rpx', height: '56rpx', borderRadius: '28rpx', background: product.stock === 0 ? '#ffccc7' : '#f2f3f5', color: product.stock === 0 ? '#ff4d4f' : '#333', fontSize: '28rpx', textAlign: 'center', lineHeight: '56rpx', display: 'flex', alignItems: 'center', justifyContent: 'center' }}
          >
            <Text className="btn-symbol">+</Text>
          </View>
        </View>
    </View>
  )
}
