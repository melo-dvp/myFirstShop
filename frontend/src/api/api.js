import axios from 'axios'
import store from '../store'
const sweetalert = require("sweetalert");

export default class Api{

    baseUrl = process.env.VUE_APP_BACKEND_BASE_URL

    constructor(apiName, module){
        this.apiName = apiName
        this.module = module
        this.store = store
    }

    async getAll(){
        await axios.get(`${this.baseUrl}${this.apiName}/getAll`)
        .then(res =>{
          this.store.dispatch(`${this.module}/setItems`, res.data)
        }).catch((err) => this.errorHandling(err.response));
    }

    async update(payload){
      await axios.put(`${this.baseUrl}${this.apiName}/update/${payload.id}`, payload, this.store.state.keycloak.jwt).then((response) => {
        this.store.dispatch(`${this.module}/update`, response.data.entity)
        sweetalert({
          text: `Erfolgreich aktualisiert!`,
          icon: 'success'
        })
      })
    }

    async delete(id){
      await axios.delete(`${this.baseUrl}${this.apiName}/delete/${id}`, this.store.state.keycloak.jwt).then((response) => {
        this.store.dispatch(`${this.module}/delete`, response.data.id)
        sweetalert({
          text: `Erfolgreich gelöscht!`,
          icon: 'success'
        })
      })
    }

    errorHandling(error){
        switch(error.status){
            case 400:{
                console.error("Bad Request!", error)
                sweetalert({
                    text: `HTTP Request 400: ${error.data}`,
                    icon: 'error'
                  })
                break;
            }
            case 401:{
                console.error("not authorized!", error)
                sweetalert({
                    text: 'HTTP Request 401: Nicht authorisiert!',
                    icon: 'error'
                  })
                break;
            }
            case 403:{
                console.error("Forbidden - Not authorized!", error)
                sweetalert({
                    text: 'HTTP Request 403: Nicht authorisiert!',
                    icon: 'error'
                  })
                break;
            }
            case 404:{
                console.error("Not found!", error)
                sweetalert({
                    text: `HTTP Request 404: ${error.data.message}`,
                    icon: 'error'
                  })
                break;
            }
            case 409:{
                console.error("Duplicated Object!", error)
                sweetalert({
                    text: 'HTTP Request 409: Das Objekt existiert bereits!',
                    icon: 'error'
                  })
                break;
            }
            case 500:{
                console.error("Internal Server Error!", error)
                sweetalert({
                    text: 'HTTP Request 500: Internal Server Error!',
                    icon: 'error'
                  })
                break;
            }
            case 503:{
                console.error("Fehlerseite!", error)
                sweetalert({
                    text: 'HTTP Request 503: Fehlerseite!',
                    icon: 'error'
                  })
                break;
            }
        }
    }

}