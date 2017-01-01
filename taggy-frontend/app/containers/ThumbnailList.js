import { connect } from 'react-redux'
import { push } from 'react-router-redux'

import * as actions from '../actions'
import ThumbnailList from '../components/ThumbnailList'

const mapStateToProps = (state) => {
    return {
        images: state.images.items
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        fetchImages: () => dispatch(actions.requestImages()),
        openImage: (id) => dispatch(push(`/image/${id}/`))
    }
}

const VisibleThumbnailList = connect(
    mapStateToProps,
    mapDispatchToProps
)(ThumbnailList)

export default VisibleThumbnailList