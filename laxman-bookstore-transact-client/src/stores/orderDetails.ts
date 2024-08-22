import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type {OrderDetails} from "@/types";

const ORDER_DETAIL_STORAGE_KEY = "orderDetail";

export const useOrderDetailsStore = defineStore("OrderDetailsStore", {
    state: () => {
        let orderDetails = {} as OrderDetails;
        const initOrderString: string | null = sessionStorage.getItem(
            ORDER_DETAIL_STORAGE_KEY
        );
        if (initOrderString !== null) {
            const orderFromStorage = JSON.parse(initOrderString) as OrderDetails;
            orderDetails = Object.assign({}, orderFromStorage);
        }
        return {
            orderDetails: orderDetails,
        };
    },
    actions: {
        clearOrderDetails() {
            sessionStorage.removeItem(ORDER_DETAIL_STORAGE_KEY);
            this.orderDetails = {} as OrderDetails;
        },
        setOrderDetails(orderDetails: OrderDetails) {
            this.orderDetails = orderDetails;
            sessionStorage.setItem(
                ORDER_DETAIL_STORAGE_KEY,
                JSON.stringify(orderDetails)
            );
        },
        hasOrderDetails(): boolean {
            const orderDetailsString = sessionStorage.getItem(
                ORDER_DETAIL_STORAGE_KEY,
            );
            if(!orderDetailsString)
                return true;
            else
                return false;
        },
    },
    // getters
});
