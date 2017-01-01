import { createStore, applyMiddleware } from 'redux'
import { browserHistory } from 'react-router'
import createSagaMiddleware from 'redux-saga'
import { routerMiddleware } from 'react-router-redux'

import rootReducer from '../reducers'

export default function configure() {
    const sagaMiddleware = createSagaMiddleware()
    const routerMiddlewareImpl = routerMiddleware(browserHistory)
    const store = createStore(
        rootReducer,
        applyMiddleware(sagaMiddleware, routerMiddlewareImpl)
    )

    store.runSaga = sagaMiddleware.run

    return store
}