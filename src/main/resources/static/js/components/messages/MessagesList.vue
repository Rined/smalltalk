<template>
    <div style="position: relative; width: 300px">
        <!--
                <message-from :messages="messages" :messageAttr="message"/>
                <message-row v-for="message in messages"
                             :key="message.id"
                             :message="message"
                             :editMethod="editMethod"
                             :messages="messages"/> -->
    </div>
</template>

<script>
    export default {
        props: ['messages'],
        data() {
            return {
                message: null
            }
        },
        methods: {
            editMethod(message) {
                this.message = message
            }
        }
    }
</script>

<style>

</style>