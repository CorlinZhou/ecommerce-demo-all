import { View } from '@tarojs/components'
import { Product, OrderDraft } from '@/types/api'
import ProductCard from './ProductCard'

interface Props {
  products: Product[]
  orderDraft: OrderDraft
  stockErrorIds: number[]
  onAdd: (p: Product) => void
  onRemove: (p: Product) => void
}

export default function ProductList({
                                      products,
                                      orderDraft,
                                      stockErrorIds,
                                      onAdd,
                                      onRemove
                                    }: Props) {
  return (
    <View className="product-list">
      {products.map((p, index) => (
        <ProductCard
          key={p.id}
          product={p}
          index={index}
          quantity={orderDraft[p.id]?.quantity || 0}
          highlight={stockErrorIds.includes(p.id)}
          onAdd={onAdd}
          onRemove={onRemove}
        />
      ))}
    </View>
  )
}
