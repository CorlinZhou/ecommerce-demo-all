import { View, Text } from '@tarojs/components'
import { Product } from '@/types/api'
import './ProductCard.scss'

interface Props {
  product: Product
  index: number
  quantity: number
  highlight?: boolean
  onAdd: (p: Product) => void
  onRemove: (p: Product) => void
}

export default function ProductCard({
                                      product,
                                      index,
                                      quantity,
                                      highlight,
                                      onAdd,
                                      onRemove
                                    }: Props) {
  return (
    <View className={`product-card ${highlight ? 'low-stock' : ''}`}>
      <Text className="index">#{index + 1}</Text>

      <View className="info">
        <Text className="name">{product.name}</Text>
        <View className="meta">
          <Text className="price">${product.price}</Text>
          <Text className="stock">Stock {product.stock}</Text>
        </View>
      </View>

      <View className="counter">
        <View
          className={`btn ${quantity === 0 ? 'disabled' : ''}`}
          onClick={() => onRemove(product)}
        >
          <Text className="btn-symbol">-</Text>
        </View>
        <Text className="qty">{quantity}</Text>
        <View
          className={`btn ${product.stock === 0 ? 'disabled' : ''}`}
          onClick={() => onAdd(product)}
        >
          <Text className="btn-symbol">+</Text>
        </View>
      </View>
    </View>
  )
}
