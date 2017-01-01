export const REQUEST_IMAGES = 'REQUEST_IMAGES'
export const RECEIVE_IMAGES = 'RECEIVE_IMAGES'

export function requestImages() {
    return {
        type: REQUEST_IMAGES
    }
}

export function receiveImages(images) {
    return {
        type: RECEIVE_IMAGES,
        images,
        receivedAt: Date.now()
    }
}

export const REQUEST_IMAGE = 'REQUEST_IMAGE'
export const RECEIVE_IMAGE = 'RECEIVE_IMAGE'

export function requestImage(id) {
    return {
        type: REQUEST_IMAGE,
        id
    }
}

export function receiveImage(id, image) {
    return {
        type: RECEIVE_IMAGE,
        id,
        image,
        receivedAt: Date.now()
    }
}