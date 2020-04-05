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
                <messages-list :messages="messages"/>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import MessagesList from 'components/messages/MessagesList.vue'
    import {addHandler} from "util/ws";

    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    const index = this.messages.findIndex(item => item.id === data.body.id);
                    switch (data.eventType) {
                        case 'CREATE':
                        case 'UPDATE':
                            if (index > -1) {
                                this.messages.splice(index, 1, data.body);
                            } else {
                                this.messages.push(data.body);
                            }
                            break;
                        case 'DELETE':
                            this.messages.splice(index, 1);
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