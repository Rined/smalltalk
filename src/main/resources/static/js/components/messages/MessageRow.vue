<template>
    <v-card class="my-2">
        <v-card-text class="text--primary">
            <i>({{ message.id }})</i>
            {{ message.text }}
        </v-card-text>
        <v-card-actions>
            <v-btn @click="edit" small  rounded>
                Edit
            </v-btn>
            <v-btn @click="del" icon small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        props: ['message', 'editMessage'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message);
            },
            del() {
                this.removeMessageAction(this.message);
            }
        }
    }
</script>

<style>

</style>