import { combineReducers } from 'redux'
import { REQUEST_IMAGES, RECEIVE_IMAGES } from '../actions'

function images(state = {
    isFetching: false,
    items: []
}, action) {
    switch(action.type) {
        case REQUEST_IMAGES:
            return Object.assign({}, state, { isFetching: true })
        case RECEIVE_IMAGES:
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

const rootReducer = combineReducers({
    images
})

export default rootReducer