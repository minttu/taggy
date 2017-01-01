import React from 'react'

import Thumbnail from './Thumbnail'

export default class ThumbnailList extends React.Component {
    render() {
        const thumbnails = this.props.images.map((obj) => <Thumbnail thumbnailURL={obj.thumbnailURL}/>)
        return (
            <div className="thumbnail-list">
                {thumbnails}
            </div>
        )
    }
}

ThumbnailList.propTypes = {
    images: React.PropTypes.arrayOf(
        React.PropTypes.shape({
            thumbnailURL: React.PropTypes.string
        })
    )
}