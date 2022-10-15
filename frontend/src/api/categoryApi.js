import axios from "axios"
import Api from "./api"
const sweetalert = require("sweetalert");

class CategoryApi extends Api{

    async create(payload){
        await axios.post(`${this.baseUrl}${this.apiName}/create`, payload, this.store.state.keycloak.jwt).then(response => {
            this.store.dispatch(`${this.module}/save`, response.data.entity)
            sweetalert({
                text: 'Kategorie wurde erfolgreich erstellt',
                icon: 'success'
              })
        }).catch((err) => {
            this.errorHandling("err create category:", err.response)
        })
    }

    async update(payload, id){
        await axios.put(`${this.baseUrl}${this.apiName}/update/${id}`, payload, this.store.state.keycloak.jwt).then(response => {
            this.store.dispatch(`${this.module}/update`, response.data.entity)
            sweetalert({
                text: 'Erfolgreich aktualisiert!',
                icon: 'success'
              })
        }).catch((err) => {
            this.errorHandling("err update category:", err.response)
        })
    }

}

export const categoryClient = new CategoryApi("category", "category")