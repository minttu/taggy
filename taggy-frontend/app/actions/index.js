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