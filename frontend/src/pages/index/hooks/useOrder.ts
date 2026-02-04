import { useCallback, useState } from "react";
import { OrderDraft, OrderConfirmation, Product } from "@/types/api";
import Taro from "@tarojs/taro";
import * as api from "../services";

interface UseOrderParams {
  orderDraft: OrderDraft;
  totalQty: number;
  refreshProducts: () => Promise<Product[]>;
  markStockErrors: (p: Product[]) => void;
  clearCart: () => void;
  setCartVisible: (v: boolean) => void;
  setOrderConfirmation: (c: OrderConfirmation | null) => void;
}

export function useOrder({
  orderDraft,
  totalQty,
  refreshProducts,
  markStockErrors,
  clearCart,
  setCartVisible,
  setOrderConfirmation,
}: UseOrderParams) {
  const [submitting, setSubmitting] = useState(false);

  const submitOrder = useCallback(async () => {
    if (submitting || totalQty === 0) return;
    setSubmitting(true);

    try {
      const res = await api.createOrder(
        Object.values(orderDraft).map((i) => ({
          productId: i.productId,
          quantity: i.quantity,
        })),
      );

      if (res.statusCode === 201 && res.data) {
        await Taro.hideToast();
        setOrderConfirmation(res.data);
        clearCart();
        setCartVisible(false);
        await refreshProducts();
        await Taro.showToast({ title: "Order Success", icon: "success" });
        return;
      }

      // business failure — show server message when available (409 -> conflict)
      await Taro.hideToast();
      const serverMsg =
        res &&
        res.data &&
        typeof res.data === "object" &&
        (res.data as any).message;
      const title =
        res?.statusCode === 409
          ? serverMsg || "Insufficient stock"
          : serverMsg || "Order failed, please adjust items";
      await Taro.showToast({
        title,
        icon: "none",
        duration: 3000,
      });

      const latest = await refreshProducts();
      markStockErrors(latest);
      setCartVisible(true);
    } catch (e) {
      // network failure
      Taro.hideToast();
      await Taro.showToast({
        title: "Network error",
        icon: "none",
        duration: 3000,
      });
      try {
        const latest = await refreshProducts();
        markStockErrors(latest);
      } catch {}
      setCartVisible(true);
    } finally {
      Taro.hideToast();
      setSubmitting(false);
    }
  }, [
    submitting,
    totalQty,
    orderDraft,
    refreshProducts,
    markStockErrors,
    clearCart,
    setCartVisible,
    setOrderConfirmation,
  ]);

  return { submitting, submitOrder };
}
