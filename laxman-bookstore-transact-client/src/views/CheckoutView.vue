<script setup lang="ts">
import { reactive, ref } from "vue";
import useVuelidate from "@vuelidate/core";
import { helpers, maxLength, minLength, required, email } from "@vuelidate/validators";
import { useCartStore } from "@/stores/cart";
import { isCreditCard, isMobilePhone } from "@/validators";
import { useRouter } from "vue-router";
const router = useRouter();
import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import { useCategoryStore } from "@/stores/category";
import { useOrderDetailsStore } from "@/stores/orderDetails"
import BookPrice from "@/components/BookPrice.vue";
import type { OrderDetails, ServerErrorResponse } from "@/types";
const categoryStore = useCategoryStore();
const cartStore = useCartStore();
const orderDetailsStore = useOrderDetailsStore();
const cart = cartStore.cart;

const defaultServerErrorMessage = "An unexpected error occurred, please try again."
const serverErrorMessage = ref(defaultServerErrorMessage);
const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const currentYear = new Date().getFullYear();
const years = [currentYear];
for (let i = 1; i < 15; i++) {
  years.push(currentYear + i);
}

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  //   TODO: Add more validations for these and other fields that need more validation.
  address: {
    required: helpers.withMessage("Please provide an address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 letters.",
      maxLength(45)
    ),
  },
  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    isMobilePhone: helpers.withMessage("Please provide a valid phone number.", isMobilePhone)
  },
  email: {
    required: helpers.withMessage("Please provide an email address.", required),
    email: helpers.withMessage("Please provide a valid email address.", email)
  },
  ccNumber: {
    required: helpers.withMessage("Please provide a credit card number.", required),
    isCreditCard: helpers.withMessage("Please provide a valid credit card number.", isCreditCard)
  },

};
const v$ = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    try {
      form.checkoutStatus = "PENDING";
      serverErrorMessage.value = defaultServerErrorMessage;

      const placeOrderResponse: OrderDetails | ServerErrorResponse =
        await cartStore.placeOrder({
          name: form.name,
          address: form.address,
          phone: form.phone,
          email: form.email,
          ccNumber: form.ccNumber,
          ccExpiryMonth: form.ccExpiryMonth,
          ccExpiryYear: form.ccExpiryYear,
        })

      if ("error" in placeOrderResponse) {
        form.checkoutStatus = "SERVER_ERROR";
        serverErrorMessage.value = placeOrderResponse.message
        console.log("Error placing order", placeOrderResponse);
      } else {
        form.checkoutStatus = "OK";
        orderDetailsStore.setOrderDetails(placeOrderResponse as OrderDetails);
        await router.push({ name: "confirmation-view" });
      }

    } catch (e) {
      form.checkoutStatus = "SERVER_ERROR";
      serverErrorMessage.value = defaultServerErrorMessage;
      console.log("Error placing order", e);
    }
  }
}

/* NOTE: For example yearFrom(0) == <current_year> */
function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
/* TODO: Adapt these styles to your page */
.checkout-page {
  background: #F8E7E7;
  color: #000000;
}

.checkout-page-body {
  display: flex;
  padding: 1em;
  justify-content: center;
  gap: 1em;
}

form {
  display: flex;
  flex-direction: column;
}

form>div {
  display: flex;
  justify-content: flex-end;
  margin-top: 1em;
}

form>div>input,
form>div>select {
  background-color: #fefefe;
  margin-left: 0.5em;
  border-radius: 8px;
  line-height: 1.4em;
}

input {
  border: none;
}

form>.error {
  color: red;
  /* text-align: right; */
  font-size: 14px;
}




.button-background {
  display: inline-block;
  background: var(--primary-color-dark);
  color: black;
  padding: 0.5em 1em;
  cursor: pointer;
  margin-top: 3em;
  margin-bottom: 1.6em;
  border-radius: 10px;
  font-size: 20px;
}


.button-background:hover {
  background: #ff8282;
}

.checkout-page-empty {
  text-align: center;
}

.title {
  margin-bottom: 1em;
  padding-top: 1em;
  text-align: center;
}

.empty {
  margin-bottom: 0.5em;
}

.complete-button {
  background: rgb(224, 224, 18);
  color: black;
  padding: 0.5em 1em;
  justify-content: end;
  cursor: pointer;
  border-radius: 10px;
  font-size: 20px;
  margin-top: 1em;
  align-self: flex-end;
}

.complete-button:hover {
  background: rgb(255, 255, 15);
}

.cost-summary {
  display: flex;
  flex-direction: column;
  gap: 0.5em;
  width: fit-content;
}

.bg,
.checkoutStatusBox {
  background-color: rgb(255, 215, 217);
  padding: 0.5em;
  border-radius: 10px;
  text-align: center;
}

.checkoutStatusBoxMessage {
  background-color: rgb(255, 215, 217);
  padding: 1em;
  border-radius: 10px;
  text-align: right;
}

.checkout-status-box {
  display: flex;
  flex-direction: column;
  justify-content: end;
  flex-basis: 11em;
  gap: 1em;
}

.error {
  color: red;
  /* text-align: right; */
  font-size: 14px;
}

.black {
  color: black;
}

.yr {
  margin-left: 0.5em;
}
</style>

<template>
  <div class="checkout-page">
    <h1 class="title">Checkout</h1>
    <!-- TODO: Add an HTML section to display when checking out with an empty cart -->
    <section class="checkout-page-empty" v-if="cart.empty">
      <div class="empty">Your shopping cart is empty.</div>
      <div>
        <router-link :to="{
          name: 'category-view',
          params: { name: categoryStore.selectedCategoryName },
        }">
          <button class="button-background">Continue Shopping</button>
        </router-link>
      </div>
    </section>
    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder" class="bg">
        <div>
          <label for="name">Name:</label>
          <input type="text" size="20" id="name" name="name" v-model.lazy="v$.name.$model" />
        </div>
        <CheckoutFieldError :field-name="v$.name" class="error" />
        <!-- TODO: Add address input and validation messages -->
        <div>
          <label for="address">Address:</label>
          <input class="textField" type="text" size="20" id="address" name="address" v-model.lazy="v$.address.$model" />
        </div>
        <!-- TODO: Add phone validation message(s) -->
        <CheckoutFieldError :field-name="v$.address" />
        <div>
          <label for="phone">Phone:</label>
          <input class="textField" type="text" size="20" id="phone" name="phone" v-model.lazy="v$.phone.$model" />
        </div>
        <!-- TODO: Add phone validation message(s) -->
        <CheckoutFieldError :field-name="v$.phone" />

        <div>
          <label for="email">Email:</label>
          <input type="text" size="20" id="email" name="email" v-model.lazy="v$.email.$model" />
        </div>
        <!-- TODO: Add email validation message(s) -->
        <CheckoutFieldError :field-name="v$.email" />

        <div>
          <label for="ccNumber">Credit card:</label>
          <input type="text" size="20" id="ccNumber" name="ccNumber" v-model.lazy="v$.ccNumber.$model" />
        </div>
        <!-- TODO: Add credit card validation message(s) -->
        <CheckoutFieldError :field-name="v$.ccNumber" />

        <div>
          <label>Exp. Month:</label>
          <select v-model.lazy="form.ccExpiryMonth">
            <option v-for="(month, index) in months" :key="index" :value="index + 1">
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <label class="yr">Year:</label>
          <select v-model.lazy="form.ccExpiryYear">
            <!-- TODO: Complete this select tag for 'ccExpiryYear'. -->
            <option v-for="(year, index) in years" :key="index" :value="year">
              {{ year }}
            </option>
          </select>
        </div>
        <!-- TODO (style): Use a single label for both month and date and put the on the same line. -->
        <!-- TODO (style): For example: Exp Date {Month} {Year}, with space between month/year selectors. -->

        <input type="submit" name="submit" class="complete-button" :disabled="form.checkoutStatus === 'PENDING'"
          :style="form.checkoutStatus === 'PENDING' ? { 'color': 'gray', 'cursor': 'not-allowed' } : {}"
          value="Place Order" />
        <!-- TODO (style): The submit button should not take up the entire width of the form. -->
        <!-- TODO (style): The submit button should be styled consistent with your own site. -->

      </form>
      <!-- TODO (style): Fix error message placement so they nearer to the correct fields. -->
      <!-- TODO (style): HINT: Use another <div> and label, input, and error, and use flexbox to style. -->

      <!-- TODO: Display the cart total, subtotal and surcharge. -->
      <div class="checkout-status-box">
        <div class="cost-summary checkoutStatusBoxMessage">
          <div v-if="cartStore.count >= 1">
            Subtotal:
            <BookPrice :amount="cart.subtotal" />
          </div>
          <div v-if="cartStore.count >= 1">
            Surcharge:
            <BookPrice :amount="cart.surcharge" />
          </div>
          <div v-if="cartStore.count >= 1">
            <h3>Total:
              <BookPrice :amount="cart.total" />
            </h3>
          </div>
        </div>
        <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
          <div v-if="form.checkoutStatus === 'ERROR'" class="error">
            Error: Please fix the problems above and try again.
          </div>

          <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>
          <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>
          <div v-else class="error">{{ serverErrorMessage }}</div>
        </section>
      </div>
    </section>
  </div>
</template>
