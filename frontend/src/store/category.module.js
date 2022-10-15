const CategoryModule = {
    state: {
        categories: []
    },

    mutations: {
        SET_ITEMS(state, items){
            state.categories = items
        },
        SAVE(state, item){
            state.categories.push(Object.assign({}, item))
        },
        UPDATE(state, item){
            const existsAtIndex = state.categories.findIndex(category => category.id === item.id)
            state.categories[existsAtIndex] = item
            state.categories = [...state.categories]
        },
        DELETE(state, id){
            const index = state.categories.findIndex(category => category.id === id)
            state.categories.splice(index, 1)
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
        categories: (state) => {
            return state.categories
        }
    }
}

export default CategoryModule