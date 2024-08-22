<script setup lang="ts">
import { useCartStore } from "@/stores/cart";
const cartStore = useCartStore();
import BookPrice from "./BookPrice.vue";
import { useOrderDetailsStore } from "@/stores/orderDetails";
import type { BookItem, OrderDetails } from "@/types";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails: OrderDetails = orderDetailsStore.orderDetails;

// // A helper function - optional to use
// const bookAt = function (orderDetails: OrderDetails, index: number): BookItem {
//     return orderDetails.books[index];
// };
</script>



<style scoped>
table {
    width: fit-content;
    margin-top: 1em;
    margin-bottom: 1em;
    border: 1px solid;
    background-color: #fceffd;
    border-collapse: collapse;
}

td,
th {
    display: table-cell;
    padding: 0.5em 0.5em;
    vertical-align: middle;
    border: none;
}

tr:nth-child(even)>td {}

td:nth-child(1) {
    text-align: left;
}

td:nth-child(2) {
    text-align: center;
}

td:nth-child(3) {
    text-align: right;
}


tr {
    border-bottom: 1pt solid black;
}

#header {
    text-align: center;
}

.tr_header {
    background-color: #333;
    color: white;
}
</style>


<template>
    <table>
        <tr class="tr_header">
            <td id="header">Book</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        <tr v-for="(item, index) in orderDetails.lineItems" :key="item.bookId">
            <td>
                {{ orderDetails.books[index].title }}
            </td>
            <td>{{ item.quantity }}</td>
            <td>
                <BookPrice :amount="orderDetails.books[index].price * item.quantity" />
            </td>
        </tr>
        <tr style="font-weight: 600;">
            <td colspan="2" id="header">
                Total (Subtotal + surcharge(
                <BookPrice :amount="cartStore.cart.surcharge" />))
            </td>
            <td>
                <BookPrice :amount="orderDetails.order.amount" />
            </td>
        </tr>
    </table>
</template>

