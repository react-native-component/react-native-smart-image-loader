/*
 * A smart image loader for react-native apps
 * https://github.com/react-native-component/react-native-smart-image-loader/
 * Released under the MIT license
 * Copyright (c) 2016 react-native-component <moonsunfall@aliyun.com>
 */


import React, {
    PropTypes,
    Component,
} from 'react'
import {
    View,
    requireNativeComponent,
    Platform
} from 'react-native'

export default class ImageLoader extends Component {

    static propTypes = {
        ...View.propTypes,
        options: PropTypes.shape({
            src: PropTypes.string.isRequired,
            placeholder: PropTypes.string,
            threadSize: PropTypes.number,       //only supports android
            fadeInDuration: PropTypes.number,     //only supports android
            rowID: PropTypes.string,    //test prop
        }).isRequired,
    }

    constructor(props) {
        super(props)
        this.state = {}
    }

    render() {
        return (
            <NativeImageLoader
                {...this.props}
            />
        )
    }
}

const NativeImageLoader = requireNativeComponent(Platform.OS == 'ios' ? 'RCTImageLazyLoader' : 'RCTLoaderImageView', ImageLoader)
