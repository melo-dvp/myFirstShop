import axios from "axios"
import Api from "./api"
const sweetalert = require("sweetalert");

class ProductApi extends Api{

    async add(payload){
        await axios.post(`${this.baseUrl}${this.apiName}/add`, payload, this.store.state.keycloak.jwt).then(response => {
            this.store.dispatch(`${this.module}/save`, response.data.entity)
            sweetalert({
                text: 'Produkt wurde erfolgreich erstellt',
                icon: 'success'
              })
        }).catch((err) => {
            this.errorHandling(err.response)
        })
    }

}

export const productClient = new ProductApi("product", "product")