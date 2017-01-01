import { connect } from 'react-redux'

import ThumbnailList from '../components/ThumbnailList'

const mapStateToProps = (state) => {
    return {
        images: state.images.items
    }
}

const mapDispatchToProps = (dispatch) => {
    return {}
}

const VisibleThumbnailList = connect(
    mapStateToProps,
    mapDispatchToProps
)(ThumbnailList)

export default VisibleThumbnailList