import { View, Text } from '@tarojs/components'

interface Props {
  totalQty: number
  totalPrice: number
  onOpenCart: () => void
}

export default function BottomBar({
                                    totalQty,
                                    totalPrice,
                                    onOpenCart
                                  }: Props) {
  return (
    <View className="bottom-bar">
      <View className="summary">
        <Text>Total {totalQty} items</Text>
        <Text className="price">${totalPrice.toFixed(2)}</Text>
      </View>

      <View
        className={`cart-btn ${totalQty === 0 ? 'disabled' : ''}`}
        onTap={() => totalQty > 0 && onOpenCart()}
      >
        🛒 Cart
      </View>
    </View>
  )
}
