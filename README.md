# react-native-smart-image-loader

[![npm](https://img.shields.io/npm/v/react-native-smart-image-loader.svg)](https://www.npmjs.com/package/react-native-smart-image-loader)
[![npm](https://img.shields.io/npm/dm/react-native-smart-image-loader.svg)](https://www.npmjs.com/package/react-native-smart-image-loader)
[![npm](https://img.shields.io/npm/dt/react-native-smart-image-loader.svg)](https://www.npmjs.com/package/react-native-smart-image-loader)
[![npm](https://img.shields.io/npm/l/react-native-smart-image-loader.svg)](https://github.com/react-native-component/react-native-smart-image-loader/blob/master/LICENSE)

A smart asynchronous image downloader with cache for React Native apps.
The library uses [https://github.com/rs/SDWebImage][1] for ios, and uses [https://github.com/nostra13/Android-Universal-Image-Loader][2] for android.

## Preview

![react-native-smart-image-loader-preview-ios][3]
![react-native-smart-image-loader-preview-android][4]

## Installation

```
npm install react-native-smart-image-loader --save
```

## Notice

It can only be used greater-than-equal react-native 0.4.0 for ios, if you want to use the package less-than react-native 0.4.0, use `npm install react-native-smart-image-loader@untilRN0.40 --save`


## Installation (iOS)

* Drag RCTImageLoader.xcodeproj to your project on Xcode.

* Click on your main project file (the one that represents the .xcodeproj) select Build Phases and drag libRCTImageLoader.a from the Products folder inside the RCTImageLoader.xcodeproj.

* Look for Header Search Paths and make sure it contains $(SRCROOT)/../../../react-native/React as recursive.

* Dray ImageLoaderResources folder to your project. *if you want change image, replace goods-placeholder.png, and you also can rename the image file*

## Installation (Android)

* In `android/settings.gradle`

```
...
include ':react-native-smart-image-loader'
project(':react-native-smart-image-loader').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-smart-image-loader/android')
```

* In `android/app/build.gradle`

```
...
dependencies {
    ...
    // From node_modules
    compile project(':react-native-smart-image-loader')
}
```

* in MainApplication.java

```
...
import com.reactnativecomponent.imageloader.RCTLoaderImageViewPackage;    //import package
...
/**
 * A list of packages used by the app. If the app uses additional views
 * or modules besides the default ones, add more packages here.
 */
@Override
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new RCTLoaderImageViewPackage() //register Module
    );
}
...

```


## Full Demo

see [ReactNativeComponentDemos][0]

## Usage

Install the package from npm with `npm install react-native-smart-image-loader --save`.
Then, require it from your app's JavaScript files with `import ImageLoader from 'react-native-smart-image-loader'`.

```js

import React, {
    Component,
} from 'react'
import {
    View,
    Text,
    StyleSheet,
    Alert,
    ScrollView,
    ListView,
    Image,
    ActivityIndicator,
    ProgressBarAndroid,
    ActivityIndicatorIOS,
    Platform,
} from 'react-native'

import TimerEnhance from 'react-native-smart-timer-enhance'
import PullToRefreshListView from 'react-native-smart-pull-to-refresh-listview'

import ImageLoader from 'react-native-smart-image-loader'
import imageUrls from './imageUrls'

let pageSize = 20
let itemIndex = 0

class PullToRefreshListViewDemo extends Component {

    // 构造
    constructor(props) {
        super(props);

        this._dataSource = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2,
            //sectionHeaderHasChanged: (s1, s2) => s1 !== s2,
        });

        let dataList = []

        this.state = {
            first: true,
            dataList: dataList,
            dataSource: this._dataSource.cloneWithRows(dataList),
        }

        this._refList = []
    }

    componentDidMount () {
        this._pullToRefreshListView.beginRefresh()
    }

    //Using ListView
    render() {
        return (
            <PullToRefreshListView
                ref={ (component) => this._pullToRefreshListView = component }
                viewType={PullToRefreshListView.constants.viewType.listView}
                contentContainerStyle={{backgroundColor: 'yellow', }}
                style={{marginTop: Platform.OS == 'ios' ? 64 : 56, }}
                initialListSize={100}
                enableEmptySections={true}
                dataSource={this.state.dataSource}
                pageSize={100}
                renderRow={this._renderRow}
                //renderSeparator={(sectionID, rowID) => <View style={styles.separator} />}
                listItemProps={{overflow: 'hidden', height: 150, }}
                renderHeader={this._renderHeader}
                renderFooter={this._renderFooter}
                onRefresh={this._onRefresh}
                onLoadMore={this._onLoadMore}
                autoLoadMore={true}
                //onEndReachedThreshold={15}
            />
        )

    }

    _renderRow = (rowData, sectionID, rowID) => {
        return (
            <View style={{flexDirection: 'row',}}>
                <ImageLoader
                    style={{width: 150, height: 150}}
                    options={{
                     rowID: rowID,
                     src: `http://o2o.doorto.cn/upload/yun-o2o/${rowData}`,
                     placeholder: Platform.OS == 'ios' ? 'goods-placeholder' : 'goods_placeholder',
                     }}
                />
                <Text style={{paddingHorizontal: 30, paddingVertical: 20}} >{rowID}</Text>
            </View>
        )
    }

    _renderHeader = (viewState) => {
        let {pullState, pullDistancePercent} = viewState
        let {refresh_none, refresh_idle, will_refresh, refreshing,} = PullToRefreshListView.constants.viewState
        pullDistancePercent = Math.round(pullDistancePercent * 100)
        switch(pullState) {
            case refresh_none:
                return (
                    <View style={{height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        <Text>pull down to refresh</Text>
                    </View>
                )
            case refresh_idle:
                return (
                    <View style={{height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        <Text>pull down to refresh {pullDistancePercent}%</Text>
                    </View>
                )
            case will_refresh:
                return (
                    <View style={{height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        <Text>release to refresh {pullDistancePercent > 100 ? 100 : pullDistancePercent}%</Text>
                    </View>
                )
            case refreshing:
                return (
                    <View style={{flexDirection: 'row', height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        {this._renderActivityIndicator()}<Text>refreshing</Text>
                    </View>
                )
        }
    }

    _renderFooter = (viewState) => {
        let {pullState, pullDistancePercent} = viewState
        let {load_more_none, load_more_idle, will_load_more, loading_more, loaded_all, } = PullToRefreshListView.constants.viewState
        pullDistancePercent = Math.round(pullDistancePercent * 100)
        switch(pullState) {
            case load_more_none:
                return (
                    <View style={{height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        <Text>pull up to load more</Text>
                    </View>
                )
            case loading_more:
                return (
                    <View style={{flexDirection: 'row', height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        {this._renderActivityIndicator()}<Text>loading</Text>
                    </View>
                )
            case loaded_all:
                return (
                    <View style={{height: 35, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pink',}}>
                        <Text>no more</Text>
                    </View>
                )
        }
    }

    _onRefresh = () => {
        //console.log('outside _onRefresh start...')

        //simulate request data
        this.setTimeout( () => {
            itemIndex = 0
            let startIndex = itemIndex
            let endIndex = itemIndex + pageSize <= imageUrls.length ? itemIndex + pageSize : imageUrls.length - itemIndex
            let refreshedDataList = imageUrls.slice(startIndex, endIndex)
            itemIndex = endIndex

            this.setState({
                dataList: refreshedDataList,
                dataSource: this._dataSource.cloneWithRows(refreshedDataList),
            })
            this._pullToRefreshListView.endRefresh()

        }, 1000)
    }

    _onLoadMore = () => {
        //console.log('outside _onLoadMore start...')

        //simulate request data
        this.setTimeout( () => {

            let startIndex = itemIndex
            let endIndex = itemIndex + pageSize <= imageUrls.length ? itemIndex + pageSize : imageUrls.length
            let addedDataList = imageUrls.slice(startIndex, endIndex)
            itemIndex = endIndex

            let newDataList = this.state.dataList.concat(addedDataList)
            this.setState({
                dataList: newDataList,
                dataSource: this._dataSource.cloneWithRows(newDataList),
            })

            let loadedAll
            if(this.state.dataList.length >= imageUrls.length) {
                loadedAll = true
                this._pullToRefreshListView.endLoadMore(loadedAll)
            }
            else {
                loadedAll = false
                this._pullToRefreshListView.endLoadMore(loadedAll)
            }

        }, 1000)
    }

    _renderActivityIndicator() {
        return ActivityIndicator ? (
            <ActivityIndicator
                style={{marginRight: 10,}}
                animating={true}
                color={'#ff0000'}
                size={'small'}/>
        ) : Platform.OS == 'android' ?
            (
                <ProgressBarAndroid
                    style={{marginRight: 10,}}
                    color={'#ff0000'}
                    styleAttr={'Small'}/>

            ) :  (
            <ActivityIndicatorIOS
                style={{marginRight: 10,}}
                animating={true}
                color={'#ff0000'}
                size={'small'}/>
        )
    }

}



const styles = StyleSheet.create({
    itemHeader: {
        height: 35,
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderBottomColor: '#ccc',
        backgroundColor: 'blue',
        overflow: 'hidden',
        justifyContent: 'center',
        alignItems: 'center',
    },
    item: {
        height: 60,
        //borderBottomWidth: StyleSheet.hairlineWidth,
        //borderBottomColor: '#ccc',
        overflow: 'hidden',
        justifyContent: 'center',
        alignItems: 'center',
    },

    contentContainer: {
        paddingTop: 20 + 44,
    },

    separator: {
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderBottomColor: '#ccc',
    },

    thumbnail: {
        padding: 6,
        flexDirection: 'row',
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderBottomColor: '#ccc',
        overflow: 'hidden',
    },

    textContainer: {
        //height: 100,
        padding: 20,
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    }
})

export default TimerEnhance(PullToRefreshListViewDemo)
```

## Props

Prop                   | Type   | Optional | Default   | Description
---------------------- | ------ | -------- | --------- | -----------
options                | object | No       |           | determines the options
options.src            | number | No       |           | determines the image uri
options.placeholder    | number | Yes      |           | determines the placeholder image uri
options.threadSize     | number | Yes      | 3         | determines the number of request thread(for android)
options.fadeInDuration | number | Yes      | 0         | determines the fadeIn animation duration(for android)
options.rowID          | string | Yes      |           | determines the rowID for each listView's row, only used for test


[0]: https://github.com/cyqresig/ReactNativeComponentDemos
[1]: https://github.com/rs/SDWebImage
[2]: https://github.com/nostra13/Android-Universal-Image-Loader
[3]: http://cyqresig.github.io/img/react-native-smart-image-loader-preview-ios-v1.0.0.gif
[4]: http://cyqresig.github.io/img/react-native-smart-image-loader-preview-android-v1.0.0.gif