<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable.vue";

import { useCategoryStore } from "@/stores/category";
const categoryStore = useCategoryStore();

import { useOrderDetailsStore } from "@/stores/orderDetails";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();

const orderDetails = orderDetailsStore.orderDetails;

const orderDate = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toLocaleTimeString();
});

const ccExpDate = computed(function (): Date {
  return new Date(orderDetails.customer.ccExpDate);
});

const ccExpYear = computed(function (): number {
  return ccExpDate.value.getFullYear();
});

const ccExpMonth = computed(function (): string {
  let month = ccExpDate.value.getMonth() + 1;
  let result = "" + month;
  if (month < 10) {
    result = "0" + month;
  }
  return result;
});
</script>

<style scoped>
#confirmationView {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background-color: #F8E7E7;
  align-items: center;
}

ul {
  text-align: left;
}

ul>li {
  margin: 0.25em;
}

.title {
  margin-bottom: 1em;
  padding-top: 1em;
  text-align: center;
}

#header {
  text-align: center;
}

.customer_info {
  background-color: rgb(255, 215, 217);
  text-align: center;
  padding: 0.5em;
  border-radius: 10px;
}

.customer {
  font-weight: 700;
}

li {
  text-align: center;
}

.button-background {
  display: inline-block;
  background: var(--primary-color-dark);
  color: black;
  padding: 0.5em 1em;
  cursor: pointer;
  margin-top: 1em;
  margin-bottom: 1.6em;
  border-radius: 10px;
  font-size: 20px;
}
.empty {
  margin-bottom: 3em;
  text-align: center;
}

.button-background:hover {
  background: #ff8282;
}
</style>

<template>
  <div id="confirmationView">
    <h1 class="title">Confirmation</h1>
    <section v-if="orderDetailsStore.hasOrderDetails()">
      <div class="empty">Order not found.</div>
      <router-link :to="{
        name: 'category-view',
        params: { name: categoryStore.defaultCategoryName },
      }">
        <button class="button-background">Continue Shopping</button>
      </router-link>
    </section>
    <template v-else>
      <ul id="header">
        <li>Confirmation number: {{ orderDetails.order.confirmationNumber }}</li>
        <li>Time: {{ new Date(orderDetails.order.dateCreated) }}</li>
      </ul>
      <confirmation-table> </confirmation-table>
      <div class="customer_info">
        <p class="customer">Customer Information</p>
        <ul>
          <li>{{ orderDetails.customer.customerName }}</li>
          <li>{{ orderDetails.customer.address }}</li>
          <li>{{ orderDetails.customer.email }}</li>
          <li>{{ orderDetails.customer.phone }}</li>
          <li>**** **** **** {{ orderDetails.customer.ccNumber.slice(-4) }} ({{ ccExpMonth }}-{{ ccExpYear }})</li>
        </ul>
      </div>
      <div>
        <router-link :to="{
          name: 'category-view',
          params: { name: categoryStore.defaultCategoryName },
        }">
          <button class="button-background">Continue Shopping</button>
        </router-link>
      </div>
    </template>
  </div>
</template>
