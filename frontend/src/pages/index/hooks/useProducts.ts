import { useCallback, useEffect, useState } from "react";
import Taro from "@tarojs/taro";
import { Product } from "@/types/api";
import * as api from "../services";

export function useProducts() {
  const [products, setProducts] = useState<Product[]>([]);

  const refresh = useCallback(async () => {
    const p = await api.fetchProducts();
    setProducts(p);
    return p;
  }, []);

  useEffect(() => {
    refresh().catch(async () => {
      await Taro.showToast({
        title: "Failed to load products",
        icon: "none",
        duration: 3000,
      });
    });
  }, [refresh]);

  return { products, refresh };
}
