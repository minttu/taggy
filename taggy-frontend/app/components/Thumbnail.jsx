import React from 'react'

export default class Thumbnail extends React.Component {
    render() {
        return (
            <div className="thumbnail">
                <img src={this.props.thumbnailURL}/>
            </div>
        )
    }
}

Thumbnail.propTypes = {
    thumbnailURL: React.PropTypes.string
}