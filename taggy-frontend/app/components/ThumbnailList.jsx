import React from 'react'

import Thumbnail from './Thumbnail'

export default class ThumbnailList extends React.Component {
    componentDidMount() {
        this.props.fetchImages()
    }
    render() {
        const thumbnails = this.props.images.map((obj) => <Thumbnail key={obj.id} onClick={() => this.props.openImage(obj.id)} thumbnailURL={obj.thumbnailURL}/>)
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
            id: React.PropTypes.number,
            thumbnailURL: React.PropTypes.string
        })
    ),
    openImage: React.PropTypes.func,
    fetchImages: React.PropTypes.func
}