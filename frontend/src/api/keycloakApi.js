import axios from "axios"
import Api from "./api"
const sweetalert = require("sweetalert");

class KeycloakApi extends Api{

    async getUser(username){
        await axios.get(`${this.baseUrl}${this.apiName}/user/${username}`).then(res => {
            this.store.dispatch(`${this.module}/storeUser`, res.data[0])
          }).catch(err => this.errorHandling(err.response))
    }

    async searchUser(username){
      await axios.get(`${this.baseUrl}${this.apiName}/user/${username}`).then(res => {
          this.store.dispatch(`${this.module}/findUser`, res.data[0])
        }).catch(err => this.errorHandling(err.response))
    }

    async getAllUser(){
      await axios.get(`${this.baseUrl}${this.apiName}/user/getAllUser`).then(res => {
          this.store.dispatch(`${this.module}/getAllUser`, res.data)
        }).catch(err => this.errorHandling(err.response))
    }

    async add(payload){
      await axios.post(`${this.baseUrl}${this.apiName}/user/add`, payload)
        .then(() => {
          sweetalert({
            text: 'Benutzer wurde erfolgreich erstellt! Bitte melden Sie sich an',
            icon: 'success'
          })
        })
    }

    async delete(id){
      await axios.delete(`${this.baseUrl}${this.apiName}/user/delete/${id}`, this.store.state.keycloak.jwt).then(() => {
        this.store.dispatch(`${this.module}/clearFindedUser`)
        sweetalert({
          text: 'Benutzer wurde erfolgreich gelöscht',
          icon: 'success'
        })
      })
    }
}

export const keycloakClient = new KeycloakApi("keycloak", "keycloak")