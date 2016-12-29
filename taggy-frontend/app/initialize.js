import 'babel-polyfill'
import 'whatwg-fetch'

import ReactDOM from 'react-dom';
import React from 'react';
import { Provider } from 'react-redux'

import App from 'components/App';
import configureStore from './store/configure'
import rootSaga from './sagas'

const store = configureStore()
store.runSaga(rootSaga)

document.addEventListener('DOMContentLoaded', () => {
    ReactDOM.render(
        <Provider store={store}>
            <App />
        </Provider>
        , document.querySelector('#app')
    )
})