import { View, Text, Button } from '@tarojs/components'
import { OrderConfirmation } from '@/types/api'
import './OrderModal.scss'

interface Props {
  data: OrderConfirmation | null
  onClose: () => void
}

export default function OrderModal({ data, onClose }: Props) {
  if (!data) return null

  return (
    <View className="modal-mask" onTap={onClose}>
      <View className="modal-content" onTap={e => e.stopPropagation()}>
        <View className="modal-header">
          <Text className="title">Order Success</Text>
          <View className="close-btn" onTap={onClose} role="button" style={{ padding: 12 }}>
            <Text className="close-icon">×</Text>
          </View>
        </View>

        <View className="modal-body">
          <View className="order-detail">
            <Text className="label">Order ID:</Text>
            <Text className="value">{data.orderId}</Text>
          </View>
          <View className="order-detail total">
            <Text className="label">Total:</Text>
            <Text className="value">${data.totalPrice.toFixed(2)}</Text>
          </View>
        </View>

        <Button className="confirm-btn" onTap={onClose}>
          OK
        </Button>
      </View>
    </View>
  )
}
