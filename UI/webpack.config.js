/**
 * Created by shusesshou on 2017/9/6.
 */
var htmlWebpackPlugin = require('html-webpack-plugin');
var webpack = require('webpack')

module.exports = {
    entry: './src/main.js',
    output: {
        path: __dirname + "/dist",
        publicPath: "/static",
        filename: "build.js"
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                use: ["vue-loader"]
            }
        ]
    }
}