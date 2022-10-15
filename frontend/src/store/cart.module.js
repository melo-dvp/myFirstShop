const CartModule = {
    state: {
        cartItems: [],
        totalCost: 0
    },

    mutations: {
        SET_ITEMS(state, items){
            state.cartItems = items
        },
        SET_TOTALCOST(state, item){
            state.totalCost = item
        },
        SAVE(state, item){
            state.cartItems.push(Object.assign({}, item))
        },
        DELETE(state, id){
            const index = state.cartItems.findIndex(cart => cart.id === id)
            state.cartItems.splice(index, 1)
        },
        DELETE_ALL(state){
            state.cartItems = []
        }
    },

    actions: {
        setItems({commit}, items){
            commit('SET_ITEMS', items)
        },
        setTotalCost({commit}, item){
            commit('SET_TOTALCOST', item)
        },
        save({commit}, item){
            commit('SAVE', item)
        },
        delete({commit}, id){
            commit('DELETE', id)
        },
        deleteAll({commit}){
            commit('DELETE_ALL')
        }
    },
    
    getters: {
        cartItems: (state) => {
            return state.cartItems
        },
        totalCost: (state) => {
            return state.totalCost
        }
    }
}

export default CartModule