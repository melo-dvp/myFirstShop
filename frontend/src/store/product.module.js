const ProductModule = {
    state: {
        products: []
    },

    mutations: {
        SET_ITEMS(state, items){
            state.products = items
        },
        SAVE(state, item){
            state.products.push(Object.assign({}, item))
        },
        UPDATE(state, item){
            const existsAtIndex = state.products.findIndex(product => product.id === item.id)
            state.products[existsAtIndex] = item
            state.products = [...state.products]
        },
        DELETE(state, id){
            const index = state.products.findIndex(product => product.id === id)
            state.products.splice(index, 1)
        }
    },

    actions: {
        setItems({commit}, items){
            commit('SET_ITEMS', items)
        },
        save({commit}, item){
            commit('SAVE', item)
        },
        update({commit}, item){
            commit('UPDATE', item)
        },
        delete({commit}, id){
            commit('DELETE', id)
        }
    },
    
    getters: {
        products: (state) => {
            return state.products
        }
    }
}

export default ProductModule