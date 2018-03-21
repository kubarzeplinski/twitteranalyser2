const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const config = {
    entry: './src/index',
    output: {
        path: __dirname + '/dist',
        publicPath: '/',
        filename: '[name].js'
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'src/index.html'
        })
    ],
    module: {
        rules: [
            { test: /\.s?css$/, use: ['style-loader', 'css-loader', 'sass-loader'] }
        ]
    },
    devServer: {
        port: 3000,
        proxy: {
            "/app": "http://localhost:8080"
        }
    }
};
module.exports = config;
