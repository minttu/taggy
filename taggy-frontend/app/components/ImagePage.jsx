import React from 'react'

import Image from './Image'

export default class ImagePage extends React.Component {
    componentDidMount() {
        this.props.getImage(this.props.params.id)
    }

    render() {
        return (
            <Image imageURL={this.props.imageURL}/>
        )
    }
}

ImagePage.propTypes = {
    imageURL: React.PropTypes.string,
    params: React.PropTypes.object,
    getImage: React.PropTypes.func
}

