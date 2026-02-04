import { useMemo, useState } from "react";
import { Product, OrderDraft } from "@/types/api";

export function useCart(initial: OrderDraft = {}) {
  const [orderDraft, setOrderDraft] = useState<OrderDraft>(initial);
  const [stockErrorIds, setStockErrorIds] = useState<number[]>([]);

  const markStockErrors = (latestProducts: Product[]) => {
    const errorIds: number[] = [];
    Object.values(orderDraft).forEach((item) => {
      const product = latestProducts.find((p) => p.id === item.productId);
      if (!product || item.quantity > product.stock) {
        errorIds.push(item.productId);
      }
    });
    setStockErrorIds(errorIds);
  };

  const addProduct = (product: Product) => {
    if (product.stock <= 0) return;

    setOrderDraft((draft) => {
      const exist = draft[product.id];
      if (exist && exist.quantity >= product.stock) return draft;

      return {
        ...draft,
        [product.id]: exist
          ? { ...exist, quantity: exist.quantity + 1 }
          : {
              productId: product.id,
              name: product.name,
              price: product.price,
              quantity: 1,
            },
      };
    });

    setStockErrorIds((ids) => ids.filter((id) => id !== product.id));
  };

  const removeProduct = (product: Product) => {
    const exist = orderDraft[product.id];
    if (!exist) return;

    setOrderDraft((draft) => {
      const copy = { ...draft };
      if (exist.quantity === 1) delete copy[product.id];
      else copy[product.id] = { ...exist, quantity: exist.quantity - 1 };
      return copy;
    });

    setStockErrorIds((ids) => ids.filter((id) => id !== product.id));
  };

  const totalQty = useMemo(
    () => Object.values(orderDraft).reduce((sum, i) => sum + i.quantity, 0),
    [orderDraft],
  );

  const totalPrice = useMemo(
    () =>
      Object.values(orderDraft).reduce(
        (sum, i) => sum + i.quantity * i.price,
        0,
      ),
    [orderDraft],
  );

  const clear = () => {
    setOrderDraft({});
    setStockErrorIds([]);
  };

  return {
    orderDraft,
    addProduct,
    removeProduct,
    stockErrorIds,
    markStockErrors,
    totalQty,
    totalPrice,
    clear,
  };
}
