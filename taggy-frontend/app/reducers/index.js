import { combineReducers } from 'redux'
import { routerReducer } from 'react-router-redux'

import * as actions from '../actions'

function images(state = {
    isFetching: false,
    items: []
}, action) {
    switch(action.type) {
        case actions.REQUEST_IMAGES:
            return Object.assign({}, state, { isFetching: true })
        case actions.RECEIVE_IMAGES:
            return Object.assign({}, state,
                {
                    isFetching: false,
                    items: action.images,
                    lastUpdated: action.receivedAt
                }
            )
        default:
            return state
    }
}

function image(state = {
    isFetching: false,
    image: {}
}, action) {
    switch(action.type) {
        case actions.REQUEST_IMAGE:
            return Object.assign({}, state,
                {
                    isFetching: true,
                    image: {}
                }
            )
        case actions.RECEIVE_IMAGE:
            return Object.assign({}, state,
                {
                    isFetching: false,
                    image: action.image,
                    lastUpdated: action.receivedAt
                }
            )
        default:
            return state
    }
}

const rootReducer = combineReducers({
    images,
    image,
    routing: routerReducer
})

export default rootReducer