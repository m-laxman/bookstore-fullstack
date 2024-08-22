<script setup lang="ts">
import { defineProps } from "vue";
import { asDollarsAndCents } from "@/utils";


const props = defineProps<{
    amount: number;
}>();

</script>

<style></style>
<template>
    <span>{{ asDollarsAndCents(amount) }}</span>
</template>