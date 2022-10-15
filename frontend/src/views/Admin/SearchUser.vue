<template>
    <div class="container mt-5">
        <div class="form-group">
            <input 
                v-model="username" 
                type="text" 
                class="form-control search-username-input mr-3" 
                placeholder="Nach Benutzername suchen"
                style="float: left"
            >
        </div>
        <div>
            <button 
                class="btn btn-x-mark" 
                v-if="hasUser" 
                @click="hasUser = false; username = ''"
                data-toggle="tooltip" 
                title="leeren"
            >
                X
            </button>
        </div>
        <div>
            <button class="btn btn-primary" @click="findUser()">Suchen</button>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Benutzername</th>
                    <th>Vorname</th>
                    <th>Nachname</th>
                    <th>E-Mail</th>
                    <th>Löschen</th>
                </tr>
            </thead>
            <tbody v-if="hasUser">
                <tr>
                    <td>{{ getUser.username }}</td>
                    <td>{{ getUser.firstName }}</td>
                    <td>{{ getUser.lastName }}</td>
                    <td>
                        <font-awesome-icon
                            icon="exclamation-circle" 
                            v-if="!getUser.emailVerified" 
                            class="xmark-icon" 
                            data-toggle="tooltip" 
                            title="nicht verifiziert"
                        />
                        <font-awesome-icon 
                            icon="check-circle" 
                            v-if="getUser.emailVerified" 
                            class="check-icon"
                            data-toggle="tooltip" 
                            title="verifiziert"
                        />
                        {{ getUser.email }}
                    </td>
                    <td>
                        <button 
                            class="btn btn-danger" 
                            data-toggle="modal" 
                            data-target="#deleteDialog"
                            @click="userId = getUser.id; deletedUsername = getUser.username"
                        >
                            <font-awesome-icon icon="trash" />
                        </button>
                    </td>
                </tr>
            </tbody>
            <tbody v-if="!hasUser">
                <tr 
                    v-for="user in allUsers"
                    :key="user"
                >
                    <td>{{ user.username }}</td>
                    <td>{{ user.firstName }}</td>
                    <td>{{ user.lastName }}</td>
                    <td>
                        <font-awesome-icon 
                            icon="exclamation-circle" 
                            v-if="!user.emailVerified" 
                            class="xmark-icon"
                            data-toggle="tooltip" 
                            title="nicht verifiziert"
                        />
                        <font-awesome-icon 
                            icon="check-circle" 
                            v-if="user.emailVerified" 
                            class="check-icon"
                            data-toggle="tooltip" 
                            title="verifiziert"
                        />
                        {{ user.email }}
                    </td>
                    <td>
                        <button 
                            class="btn btn-danger" 
                            data-toggle="modal" 
                            data-target="#deleteDialog"
                            @click="userId = user.id; deletedUsername = user.username"
                        >
                            <font-awesome-icon icon="trash" />
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="modal" id="deleteDialog">
          <div class="modal-dialog">
            <div class="modal-content">

              <div class="modal-header">
                <h4 class="modal-title">Möchten Sie den Benutzer "{{ deletedUsername }}" wirklich löschen?</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>

              <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Abbrechen</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" @click="deleteUser()">Ja</button>
              </div>

            </div>
          </div>
        </div>
    </div>
</template>

<script>
import { keycloakClient } from '../../api/keycloakApi'

const sweetalert = require("sweetalert");


export default {
    name: "SearchUser",

    data(){
        return{
            username: '',
            hasUser: false,
            userId: '',
            deletedUsername: ''
        }
    },

    mounted(){
        this.getAllUser()
    },

    computed:{
        getUser(){
            return this.$store.getters["keycloak/findUser"]
        },

        allUsers(){
            return this.$store.getters["keycloak/getAllUser"]
        }
    },

    methods: {
        async getAllUser(){
            await keycloakClient.getAllUser()
        }, 

        async findUser(){
            if(this.username.length === 0){
                sweetalert({
                    text: 'Bitte füllen Sie das Feld aus',
                    icon: 'warning'
                })
            }else{
                await keycloakClient.searchUser(this.username)
                if(this.getUser === undefined){
                    sweetalert({
                        text: 'Benutzer konnte nicht gefunden werden',
                        icon: 'warning'
                    })
                    this.hasUser = false
                }else{
                    this.hasUser = true
                }
            }
        },

        async deleteUser(){
            await keycloakClient.delete(this.userId).then(() => {
                this.getAllUser()
            })
            this.hasUser = false
        }
    }
}
</script>


<style scoped>
.search-username-input{
    width: 25%;
}

.check-icon{
    color: green;
}

.xmark-icon{
    color: rgb(191, 42, 42);
}

.btn-x-mark{
    background-color: white;
    color: black;
    border: none;
}
</style>