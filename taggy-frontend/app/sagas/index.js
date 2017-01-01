import { put, fork, call, takeLatest } from 'redux-saga/effects'
import * as actions from '../actions'

export function fetchImagesApi() {
    return fetch('/api/images')
        .then(response => response.json())
}

export function fetchImageApi(id) {
    return fetch(`/api/images/${id}`)
        .then(response => response.json())
}

export function* fetchImages(action) {
    const images = yield call(fetchImagesApi)
    yield put(actions.receiveImages(images))
}

export function* fetchImage(action) {
    const image = yield call(fetchImageApi, action.id)
    yield put(actions.receiveImage(action.id, image))
}

export default function* root() {
    yield takeLatest(actions.REQUEST_IMAGES, fetchImages)
    yield takeLatest(actions.REQUEST_IMAGE, fetchImage)
}