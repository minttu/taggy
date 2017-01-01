var HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
    entry: './app/initialize.js',
    output: {
        filename: 'app.js',
        path: './public',
        publicPath: '/'
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    module: {
        loaders: [
            {
                test: /\.scss$/,
                loaders: ['style-loader', 'css-loader', 'sass-loader']
            },
            {
                test: /\.jsx?$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015', 'react']
                }
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './app/assets/index.html',
            filename: 'index.html',
            inject: 'body'
        })
    ]
}