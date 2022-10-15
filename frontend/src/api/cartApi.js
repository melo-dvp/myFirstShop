import axios from "axios"
import Api from "./api"
const sweetalert = require("sweetalert");

class CartApi extends Api{

    async getAllCarts(token){
        await axios.get(`${this.baseUrl}${this.apiName}/getAll?token=${token}`).then(res => {
            this.store.dispatch(`${this.module}/setItems`, res.data.cartItems)
            this.store.dispatch(`${this.module}/setTotalCost`, res.data.totalCost)
          }).catch(err => console.log(err))
    }

    async add(payload, token){
        await axios.post(`${this.baseUrl}${this.apiName}/add?token=${token}`, payload).then(response => {
            this.store.dispatch(`${this.module}/save`, response.data.entity)
            sweetalert({
                text: 'Erfolgreich in Warenkorb gelegt',
                icon: 'success'
              })
        }).catch((err) => {
            this.errorHandling(err.response)
        })
    }

    async delete(id, token){
        await axios.delete(`${this.baseUrl}${this.apiName}/delete/${id}?token=${token}`).then(res => {
            this.store.dispatch(`${this.module}/delete`, res.data.id)
            sweetalert({
                text: 'Erfolgreich gelöscht',
                icon: 'success'
              })
        })
    }
    
    async deleteAll(token){
        await axios.delete(`${this.baseUrl}${this.apiName}/deleteAll?token=${token}`).then(() => {
            this.store.dispatch(`${this.module}/deleteAll`)
        }).catch(err => this.errorHandling(err.response))
    }

}

export const cartClient = new CartApi("newCart", "cart")