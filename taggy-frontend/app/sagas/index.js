import { put, fork, call } from 'redux-saga/effects'
import * as actions from '../actions'

export function fetchImagesApi() {
    return fetch('/api/images')
        .then(response => response.json())
}

export function* fetchImages() {
    yield put(actions.requestImages())
    const images = yield call(fetchImagesApi)
    yield put(actions.receiveImages(images))
}

export function* startup() {
    yield fork(fetchImages)
}

export default function* root() {
    yield fork(startup)
}