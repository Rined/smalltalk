<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Small-Talk</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <v-container v-if="!profile">
                Авторизуйтесь через
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <messages-list/>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import MessagesList from 'components/messages/MessagesList.vue'
    import {addHandler} from "util/ws";

    export default {
        components: {
            MessagesList
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body);
                            break;
                        case 'UPDATE':
                            this.updateMessageMutation(data.body);
                            break;
                        case 'DELETE':
                            this.removeMessageMutation(data.body);
                            break;
                        default:
                            console.error(`Event type "${data.eventType}" is unknown!`)
                    }
                } else {
                    console.error(`Object type "${data.objectType}" is unknown!`)
                }
            });
        }
    }
</script>

<style>
</style>