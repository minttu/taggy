import { connect } from 'react-redux'
import { push } from 'react-router-redux'
import R from 'ramda'

import * as actions from '../actions'
import ImagePage from '../components/ImagePage'

const mapStateToProps = (state) => {
    return {
        imageURL: state.image.image.imageURL
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getImage: (id) => dispatch(actions.requestImage(id))
    }
}

const VisibleImagePage = connect(
    mapStateToProps,
    mapDispatchToProps
)(ImagePage)

export default VisibleImagePage