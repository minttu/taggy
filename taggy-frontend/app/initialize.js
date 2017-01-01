import 'babel-polyfill'
import 'whatwg-fetch'

import ReactDOM from 'react-dom';
import React from 'react';
import { Provider } from 'react-redux'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'
import { syncHistoryWithStore } from 'react-router-redux'

import configureStore from './store/configure'
import rootSaga from './sagas'

import App from './components/App'
import ImagePage from './containers/ImagePage'
import ThumbnailList from './containers/ThumbnailList'

import './styles/application.scss'

const store = configureStore()
store.runSaga(rootSaga)

const history = syncHistoryWithStore(browserHistory, store)

document.addEventListener('DOMContentLoaded', () => {
    ReactDOM.render(
        <Provider store={store}>
            <Router history={history}>
                <Route path="/" component={App}>
                    <IndexRoute component={ThumbnailList} />
                    <Route path="/image/:id" component={ImagePage} />
                </Route>
            </Router>
        </Provider>
        , document.querySelector('#app')
    )
})