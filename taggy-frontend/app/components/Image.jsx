import React from 'react'

export default class Image extends React.Component {
    render() {
        return (
            <img src={this.props.imageURL} />
        )
    }
}

Image.propTypes = {
    imageURL: React.PropTypes.string
}