import Taro from "@tarojs/taro";
import { Product } from "@/types/api";
import { API_BASE } from "./config";

/**
 * Handle API response and error
 * @param res API response from Taro.request
 * @returns Response data if successful
 * @throws Error with detailed error message if failed
 */
function handleApiResponse<T = any>(res: any): T {
  // accept any 2xx as success
  if (
    res?.statusCode &&
    res.statusCode >= 200 &&
    res.statusCode < 300 &&
    res.data
  ) {
    return res.data;
  }

  let errorMessage = "API request failed";
  if (res?.statusCode) {
    errorMessage = `API request failed with status ${res.statusCode}`;
    if (res.data && typeof res.data === "object" && "message" in res.data) {
      errorMessage += `: ${(res.data as any).message}`;
    }
  } else if (res?.errMsg) {
    errorMessage = res.errMsg;
  }

  throw new Error(errorMessage);
}

export async function fetchProducts(): Promise<Product[]> {
  try {
    const res = await Taro.request<Product[]>({
      url: `${API_BASE}/products`,
    });
    return handleApiResponse(res);
  } catch (error) {
    console.error("Failed to fetch products:", error);
    throw error;
  }
}

export async function createOrder(
  items: { productId: number; quantity: number }[],
): Promise<Taro.request.SuccessCallbackResult<any>> {
  try {
    const res = await Taro.request({
      url: `${API_BASE}/orders`,
      method: "POST",
      data: { items },
    });
    // return full response so caller can handle 201 vs 4xx/409 semantics
    return res as Taro.request.SuccessCallbackResult<any>;
  } catch (error) {
    console.error("Failed to create order:", error);
    throw error;
  }
}
